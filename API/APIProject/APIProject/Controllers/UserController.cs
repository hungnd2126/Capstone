using System.Threading.Tasks;
using System.Web.Http;
using APIProject.Core.Common;
using APIProject.Core.JWT;
using APIProject.Model.Models;
using APIProject.ViewModel;
using Microsoft.AspNet.Identity;
using System.Web;
using System;
using APIProject.Service;
using System.Linq;

namespace APIProject.Controllers
{
    [RoutePrefix("api/user")]
    [Authorize]
    public class UserController : AccountController
    {
        private readonly IUserService userService;
        private readonly ITransactionService transactionService;
        private readonly ICityService cityService;
        private readonly NLUtilities nLUtilities;
        public UserController(IUserService userService, ITransactionService transactionService, ICityService cityService)
        {
            this.userService = userService;
            this.transactionService = transactionService;
            this.cityService = cityService;
            nLUtilities = new NLUtilities();
        }


        private string[] firstname = new string[] { "An", "Anh", "Bảo", "Châu", "Điệp", "Quyên", "Quỳnh", "Hùng", "Vũ" };
        private string[] midname = new string[] { "", "Thị", "Quốc", "Hồng", "Nhật", "Duy", "Thanh", "Quang", "Khánh", "Trúc" };
        private string[] lastname = new string[] { "Nguyễn", "Trần", "Lý", "Ngô", "Phan", "Vũ", "Võ", "Đỗ", "Đoàn" };
        private string[] email = new string[] { "@gmail.com", "@outlook.com", "@yahoo.com", "@fpt.edu.vn" };
        private static Random random = new Random();

        const int expried = 24 * 60;
        /// <inheritdoc />
        /// <summary>
        /// </summary>
        public UserController()
        {
        }

        /// <summary>
        /// Login via FB
        /// </summary>
        /// <returns></returns>
        [HttpPost]
        [Route("loginfb")]
        [AllowAnonymous]
        public async Task<IHttpActionResult> LoginFb([FromBody] LoginViewModel request)
        {
            var user = ApplicationUserManager.FindByName(request.Email);

            if (user == null)
            {
                user = new User()
                {
                    UserName = request.Email,
                    Email = request.Email,
                    FirstName = request.Firstname,
                    LastName = request.Lastname,
                    FacebookId = request.Type.Equals("fb") ? request.Uid : "",
                    GoogleId = request.Type.Equals("gg") ? request.Uid : "",
                };

                IdentityResult result = await ApplicationUserManager.CreateAsync(user);
                if (!result.Succeeded)
                {
                    return BadRequest("Can't create user");
                }

                string fileName = user.Id + DateTime.Now.Millisecond.ToString() + ".jpg";
                string filePath = HttpContext.Current.Server.MapPath("~/Media/UserImage/") + fileName;
                Utilities.GetImageFormUrl(request.Avatar).Save(filePath);
                user.ImageUrl = "/Media/UserImage/" + fileName;
                result = await ApplicationUserManager.UpdateAsync(user);
            }

            request.Status_code = "200";
            request.Message = "OK";
            request.AccessToken = JwtManager.GenerateToken(user.UserName, expried);
            request.Avatar = user.ImageUrl;
            request.Email = user.Email;
            request.Uid = user.Id;
            request.Lastname = user.LastName;
            request.Amount = user.Current_Money;
            request.Phone = user.PhoneNumber;
            request.Bio = user.FirstName;
            request.Tknl = user.TKNL;
            if (user.Address != null)
            {
                string[] abc = user.Address.Split('_');
                if (abc.Length == 2)
                {
                    City city = cityService.GetCities().FirstOrDefault(c => c.Code.Equals(abc[1]));
                    if (city != null)
                    {
                        request.Address = abc[0] + ", " + city.Name + ", " + city.Country.Name;
                        request.GeoId = abc[1];
                    }
                }
            }

            return Ok(request);
        }

        /// <summary>
        /// Profile
        /// </summary>
        /// <returns></returns>
        [HttpGet]
        [Route("profile/{id}")]
        [AllowAnonymous]
        public IHttpActionResult Profile(string id)
        {
            if (Validation.Id(id) == null)
            {
                return BadRequest();
            }
            //var user = _userServices.Profile(id);
            return Ok();
        }

        /// <summary>
        /// Login
        /// </summary>
        /// <returns></returns>
        [HttpPost]
        [Route("loginform")]
        [AllowAnonymous]
        public IHttpActionResult Login([FromBody] LoginRequest loginRequest)
        {

            var user = ApplicationUserManager.FindAsync(loginRequest.Email, loginRequest.Password);
            if (user.Result == null)
            {
                return BadRequest("Access Denied.");
            }
            string token = JwtManager.GenerateToken(user.Result.UserName, expried);

            LoginViewModel response = new LoginViewModel()
            {
                AccessToken = token,
                Avatar = user.Result.ImageUrl,
                Lastname = user.Result.LastName,
                Email = user.Result.Email,
                Type = "3",
                Status_code = "200",
                Uid = user.Result.Id,
                Amount = user.Result.Current_Money,
                Bio = user.Result.FirstName,
                Phone = user.Result.PhoneNumber,
                Tknl = user.Result.TKNL
            };
            if (user.Result.Address != null)
            {
                string[] abc = user.Result.Address.Split('_');
                if (abc.Length == 2)
                {
                    City city = cityService.GetCities().FirstOrDefault(c => c.Code.Equals(abc[1]));
                    if (city != null)
                    {
                        response.Address = abc[0] + ", " + city.Name + ", " + city.Country.Name;
                        response.GeoId = abc[1];
                    }
                }
            }
            return Ok(response);
        }
        [HttpPost]
        [Route("get-user")]
        public IHttpActionResult GetUser(DialogViewModel user)
        {
            string username = Utilities.GetUserNameFromRequest(Request);
            User currentUser = userService.GetUsers().FirstOrDefault(u => u.UserName.Equals(username));
            if (user == null)
            {
                return BadRequest("Access Denied");
            }
            try
            {
                var userResult = userService.GetUsers().Where(u => u.Id == user.UserId).FirstOrDefault();
                var userVM = new DialogViewModel()
                {
                    NickName= userResult.LastName,
                    UserAvartar= userResult.ImageUrl,
                    UserId=userResult.Id,
                    Username= userResult.UserName
                };
                return Ok(userVM);

            }
            catch
            {
                return BadRequest();
            }
        }
        [HttpPost]
        [Route("findUser")]
        public IHttpActionResult FindUser(User user)
        {
            string username = Utilities.GetUserNameFromRequest(Request);
            User currentUser = userService.GetUsers().FirstOrDefault(u => u.UserName.Equals(username));
            if (user == null)
            {
                return BadRequest("Access Denied");
            }
            try
            {
                var userResult = userService.GetUsers()
                    .Where(u => u.Id != currentUser.Id
                                && u.LastName.ToUpper().Contains(user.FirstName.ToUpper()))
                                    .Select(a => new DialogViewModel
                                    {
                                        UserId = a.Id,
                                        NickName = a.LastName,
                                        UserAvartar = a.ImageUrl,
                                        Username = a.UserName
                                    }).ToList();
                return Ok(userResult);

            }
            catch
            {
                return BadRequest();
            }
        }

        [HttpPost]
        [Route("update-info")]
        [Authorize]
        public IHttpActionResult UpdateInfo([FromBody] UpdateInfoViewModel request)
        {
            string username = Utilities.GetUserNameFromRequest(Request);
            if (username == null)
            {
                return Unauthorized();
            }
            User user = ApplicationUserManager.FindByName(username);
            if (user == null)
            {
                return BadRequest("Get user Error");
            }

            try
            {
                if (request.Image != null && request.Image.Trim().Length != 0)
                {
                    string fileName = user.Id + DateTime.Now.Millisecond.ToString() + ".jpg";
                    string filePath = HttpContext.Current.Server.MapPath("~/Media/UserImage/") + fileName;
                    Utilities.ConvertFromBitmap(request.Image).Save(filePath);
                    user.ImageUrl = "/Media/UserImage/" + fileName;
                }
                user.LastName = request.Lastname;
                user.FirstName = request.Bio;
                user.PhoneNumber = request.Phone;
                user.TKNL = request.Tknl;
                user.Address = request.Address + "_" + request.GeoId;

                var result = ApplicationUserManager.Update(user);
                userService.SaveUser();
                if (!result.Succeeded)
                {
                    return BadRequest("Update error");
                }
            }
            catch (Exception)
            {
                return BadRequest("Error");
            }

            request.Status_code = "200";
            request.Message = "OK";
            request.Image = user.ImageUrl;
            City city = cityService.GetCities().FirstOrDefault(c => c.Code.Equals(request.GeoId));
            if (city != null)
            {
                request.Address = request.Address + ", " + city.Name + ", " + city.Country.Name;
            }

            return Ok(request);
        }


        [HttpPost]
        [Route("nap-tien")]
        [Authorize]
        public IHttpActionResult NapTien([FromBody] WalletViewModel request)
        {
            string username = Utilities.GetUserNameFromRequest(Request);
            if (username == null)
            {
                return Unauthorized();
            }
            User user = ApplicationUserManager.FindByName(username);
            if (user == null)
            {
                return BadRequest("Get user Error");
            }

            try
            {
                transactionService.CreateTransaction(new Transaction()
                {
                    IsSuccess = true,
                    DateCreated = DateTime.Now,
                    Description = "Nạp tiền vào ví",
                    IsActive = true,
                    IsDelete = false,
                    UserId = user.Id,
                    Value = int.Parse(request.Value.ToString()),
                    Type = Transaction.TypeTrans.CashIn
                });
                transactionService.SaveTransaction();

                user.Current_Money += request.Value;
                var result = ApplicationUserManager.Update(user);

                if (!result.Succeeded)
                {
                    return BadRequest("Update error");
                }
            }
            catch (Exception)
            {
                return BadRequest("Error");
            }

            request.Value = user.Current_Money;
            return Ok(request);
        }

        [HttpPost]
        [Route("rut-tien")]
        [Authorize]
        public IHttpActionResult RutTien([FromBody] WalletViewModel request)
        {
            string username = Utilities.GetUserNameFromRequest(Request);
            if (username == null)
            {
                return Unauthorized();
            }
            User user = userService.GetUsers().FirstOrDefault(u => u.UserName.Equals(username));
            if (user == null)
            {
                return BadRequest("Get user Error");
            }

            try
            {
                if (user.Current_Money < request.Value)
                {
                    return BadRequest("Invalid Value");
                }
                var result = nLUtilities.DoTranfer("hungnd2126@gmail.com", request.Value.ToString(), System.Guid.NewGuid().ToString());
                if (result == null)
                {
                    return BadRequest("Tranfers fail");
                }
                if (result.Response_code.Equals("E00"))
                {
                    transactionService.CreateTransaction(new Transaction()
                    {
                        IsSuccess = true,
                        DateCreated = DateTime.UtcNow.AddHours(7),
                        Description = "Rút tiền từ ví",
                        IsActive = true,
                        IsDelete = false,
                        UserId = user.Id,
                        Value = int.Parse(request.Value.ToString()),
                        Type = Transaction.TypeTrans.CashOutToUser
                    });
                    user.Current_Money -= request.Value;
                    userService.UpdateUser(user);
                    transactionService.SaveTransaction();
                }
                else
                {
                    return BadRequest("Error");
                }
            }
            catch (Exception)
            {
                return BadRequest("Update Error");
            }

            request.Value = user.Current_Money;
            return Ok(request);
        }

        [HttpGet]
        [Route("get-money")]
        [Authorize]
        public IHttpActionResult GetMoney()
        {
            string username = Utilities.GetUserNameFromRequest(Request);
            if (username == null)
            {
                return Unauthorized();
            }
            User user = userService.GetUsers().FirstOrDefault(u => u.UserName.Equals(username));
            if (user == null)
            {
                return BadRequest("Get user Error");
            }

            return Ok(user.Current_Money);
        }

        /// <summary>
        /// 
        /// </summary>
        /// <param name="loginRequest"></param>
        /// <returns></returns>
        [HttpPost]
        [Route("change-password")]
        [AllowAnonymous]
        public IHttpActionResult ChangePassword([FromBody] ChangePasswordRequest loginRequest)
        {
            if (!ModelState.IsValid) return BadRequest(ModelState);
            //var user = _userServices.Login(loginRequest.Username, loginRequest.OldPassword);
            //if (user == null)
            //{
            //    return BadRequest("Access Denied.");
            //}
            //user.Password = loginRequest.NewPassword;
            //_userServices.ChangePassword(user);

            const int expried = 24 * 60;
            return Ok(JwtManager.GenerateToken(loginRequest.Username, expried));
        }

        [AllowAnonymous]
        public void TestSingleSignalR()
        {

            var con = ApplicationUserManager.FindByEmail("nguyenquocbao357@gmail.com").SignalConnect;
            HubUtilities.GetHub().Clients.Client(con).helloFromServer("riêng mình mày đó");
        }

        private string GetEmail(string Name)
        {
            return StringConvert.RemoveVietnameseString(Name) + email[random.Next(0, email.Length)];
        }
        private string GetPhone()
        {
            string phone = "0";
            for (int i = 0; i < 9; i++)
            {
                phone += random.Next(0, 9).ToString();
            }
            return phone;
        }
        private User NewRandomUser()
        {
            string fname = firstname[random.Next(0, firstname.Length)];
            string mName1 = midname[random.Next(0, midname.Length)];
            string mName2 = midname[random.Next(0, midname.Length)];
            string lname = lastname[random.Next(0, lastname.Length)];
            string email = GetEmail(fname + lname.First() + mName1.FirstOrDefault() + mName2.FirstOrDefault());
            User user = new User()
            {
                FirstName = fname,
                LastName = lname + " " + mName1 + " " + mName2,
                Email = email,
                UserName = email,
                DateCreated = DateTime.Now,
                DateOfBirth = DateTime.Now,
                Gender = random.Next(0, 1) == 0 ? Gender.Male : Gender.Female,
                PhoneNumber = GetPhone(),
                Deleted = false,
                RoleId = SystemRoles.Role02,
                Activated = true,
                ImageUrl = "/Media/UserImage/avatar.png"
            };

            return user;
        }
        [AllowAnonymous]
        public async Task<IHttpActionResult> GenerateUserAsync()
        {
            int count = 0;
            for (int i = 0; i < 999; i++)
            {
                User newUser = NewRandomUser();
                var user = ApplicationUserManager.FindByName(newUser.UserName);

                if (user == null)
                {
                    IdentityResult result = await ApplicationUserManager.CreateAsync(newUser, "Zaq@123");
                    if (result.Succeeded)
                    {
                        count++;
                    }
                }
            }

            return Ok(count);
        }
    }
}
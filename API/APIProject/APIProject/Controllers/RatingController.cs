using APIProject.Model.Models;
using APIProject.Service;
using APIProject.ViewModel;
using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using static APIProject.Model.Models.Rating;

namespace APIProject.Controllers
{
    [RoutePrefix("api/rating")]
    public class RatingController : ApiBaseController
    {
        private readonly IUserService userService;
        private readonly IRatingService ratingService;
        private readonly INotificationService notificationService;
        private readonly IOrderService orderService;
        public RatingController(IUserService userService, IRatingService ratingService, INotificationService notificationService,
            IOrderService orderService)
        {
            this.userService = userService;
            this.ratingService = ratingService;
            this.notificationService = notificationService;
            this.orderService = orderService;
        }


        /// <summary>
        /// Create Rating
        /// </summary>
        /// <param name="request"></param>
        /// <returns></returns>
        [HttpPost]
        [Authorize]
        [Route("create-rating")]
        public IHttpActionResult CreateRating([FromBody]Rating request)
        {
            string username = Utilities.GetUserNameFromRequest(Request);
            if (username == null)
            {
                return BadRequest("Access Denied");
            }

            try
            {
                User user = userService.GetUsers().FirstOrDefault(u => u.UserName.Equals(username));
                if (user == null)
                {
                    return Ok();
                }
                request.UserRateId = user.Id;
                request.DateCreated = DateTime.Now;
                request.IsDelete = false;
                ratingService.CreateRating(request);
                ratingService.SaveRating();

                IEnumerable<Rating> list = null;
                if (request.Type == (int)RateRole.Buyer)
                {
                    list = ratingService.GetRatings().Where(r => r.UserRatedId.Equals(user.Id) && r.Type == (int)RateRole.Traveller);
                    user.BuyerScore = list.Average(r => r.Point);
                }
                else
                {
                    list = ratingService.GetRatings().Where(r => r.UserRatedId.Equals(user.Id) && r.Type == (int)RateRole.Buyer);
                    user.TravelerScore = list.Average(r => r.Point);
                }

                userService.UpdateUser(user);
                userService.SaveUser();
                string con = userService.GetUsers().FirstOrDefault(u => u.Id.Equals(request.UserRatedId))?.SignalConnect;
                if (con != null)
                {
                    Order order = orderService.GetOrder(request.OrderId);
                    notificationService.CreateNotification(new Notification
                    {
                        DateCreated = DateTime.UtcNow.AddHours(7),
                        IsRead = false,
                        Message = user.LastName + " đã cho bạn " + request.Point + " sao",
                        Title = "Đơn hàng  " + order.Offer.Post.ProductName,
                        UserId = request.UserRatedId
                    });
                    notificationService.SaveNotification();
                    HubUtilities.GetHub().Clients.Client(con).completeOrder(user.LastName + " đã cho bạn " + request.Point + " sao", order.Id);
                }
            }
            catch (Exception ex)
            {
                return BadRequest(ex.ToString());
            }

            return Ok();
        }

        /// <summary>
        /// Create Rating
        /// </summary>
        /// <param name="request"></param>
        /// <returns></returns>
        [HttpGet]
        [Authorize]
        [Route("get-user-profile")]
        public IHttpActionResult GetUserProfile(string userId)
        {
            string username = Utilities.GetUserNameFromRequest(Request);
            if (username == null)
            {
                return Unauthorized();
            }
            UserProfileViewModel response = new UserProfileViewModel();
            try
            {
                User requestUser = userService.GetUsers().FirstOrDefault(u => u.UserName.Equals(username));
                if (requestUser == null)
                {
                    return Unauthorized();
                }
                User user = userService.GetUsers().FirstOrDefault(u => u.Id.Equals(userId));
                if (user != null)
                {
                    response.BuyerCount = "0";
                    response.TravelerCount = "0";
                    var collection = ratingService.GetRatings().Where(r => r.UserRatedId.Equals(user.Id));
                    if (collection != null)
                    {
                        response.BuyerCount = collection.Where(b => b.Type == (int)RateRole.Traveller).Count().ToString();
                        response.TravelerCount = collection.Where(b => b.Type == (int)RateRole.Buyer).Count().ToString();
                    }
                    response.Id = user.Id;
                    response.Name = user.LastName;
                    response.ImageUrl = user.ImageUrl;
                    response.TravelerScore = user.TravelerScore.ToString();
                    response.BuyerScore = user.BuyerScore.ToString();
                    response.Bio = user.FirstName;
                    response.DateCreated = user.DateCreated.ToString(TIMEFORMAT);
                }
            }
            catch (Exception ex)
            {
                return BadRequest(ex.ToString());
            }

            return Ok(response);
        }

        [HttpGet]
        [Authorize]
        [Route("get-rating")]
        public IHttpActionResult GetRating(string userId)
        {
            string username = Utilities.GetUserNameFromRequest(Request);
            if (username == null)
            {
                return Unauthorized();
            }
            List<RatingViewModel> response = new List<RatingViewModel>();
            try
            {
                User requestUser = userService.GetUsers().FirstOrDefault(u => u.UserName.Equals(username));
                if (requestUser == null)
                {
                    return Unauthorized();
                }
                User user = userService.GetUsers().FirstOrDefault(u => u.Id.Equals(userId));
                if (user != null)
                {
                    var collection = ratingService.GetRatings().Where(r => r.UserRatedId.Equals(userId)).OrderByDescending(r => r.DateCreated);
                    foreach (var item in collection)
                    {
                        response.Add(new RatingViewModel()
                        {
                            Name = item.UserRate.LastName,
                            DateCreated = item.DateCreated.GetValueOrDefault().ToString(TIMEFORMAT),
                            ImageUrl = item.UserRate.ImageUrl,
                            Message = item.Comment,
                            ProductName = item.Order.Offer.Post.ProductName,
                            Score = item.Point
                        });
                    }
                }
            }
            catch (Exception ex)
            {
                return BadRequest(ex.ToString());
            }

            return Ok(response);
        }

        [HttpGet]
        [Authorize]
        [Route("check-rating")]
        public IHttpActionResult CheckRating(int orderId)
        {
            string username = Utilities.GetUserNameFromRequest(Request);
            if (username == null)
            {
                return Unauthorized();
            }
            try
            {
                User requestUser = userService.GetUsers().FirstOrDefault(u => u.UserName.Equals(username));
                if (requestUser == null)
                {
                    return Unauthorized();
                }

                var collection = ratingService.GetRatings().FirstOrDefault(r => r.UserRateId.Equals(requestUser.Id) && r.OrderId == orderId);
                if (collection == null)
                {
                    return Ok(false);
                }
            }
            catch (Exception ex)
            {
                return BadRequest(ex.ToString());
            }

            return Ok(true);
        }

    }
}

using APIProject.Model.Models;
using APIProject.Service;
using APIProject.ViewModel;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web.Http;

namespace APIProject.Controllers
{
    [RoutePrefix("api/transaction")]
    [Authorize]
    public class TransactionController : ApiBaseController
    {

        #region service
        private readonly IPostService postService;
        private readonly IUserService userService;
        private readonly IOfferService offerService;
        private readonly ICityService cityService;
        private readonly IOrderService orderService;
        private readonly ICountryService countryService;
        private readonly IImageService imageService;
        private readonly ITripService tripService;
        private readonly ITrackingOrderService trackingOrderService;
        private readonly ITransactionService transactionService;
        private readonly IEcommerceWebsiteService ecommerceWebsiteService;

        #endregion

        #region contructor
        public TransactionController(IPostService postService, IUserService userService,
            IOfferService offerService, ICityService cityService,
            ICountryService countryService, IImageService imageService, IOrderService orderService,
            ITripService tripService, ITrackingOrderService trackingOrderService, ITransactionService transactionService
            , IEcommerceWebsiteService ecommerceWebsiteService)
        {
            this.postService = postService;
            this.userService = userService;
            this.offerService = offerService;
            this.cityService = cityService;
            this.countryService = countryService;
            this.imageService = imageService;
            this.orderService = orderService;
            this.tripService = tripService;
            this.trackingOrderService = trackingOrderService;
            this.transactionService = transactionService;
            this.ecommerceWebsiteService = ecommerceWebsiteService;
        }
        #endregion

        [HttpGet]
        [Route("get-history")]
        public IHttpActionResult GetHistoryByUser()
        {
            string username = Utilities.GetUserNameFromRequest(Request);
            //string username = "nguyenquocbao357@gmail.com";
            if (username == null)
            {
                return Unauthorized();
            }
            List<TransactionViewModel> response = new List<TransactionViewModel>();
            try
            {
                User user = userService.GetUsers().FirstOrDefault(u => u.UserName.Equals(username));
                if (user == null)
                {
                    return BadRequest();
                }
                var collection = transactionService.GetTransactions().Where(t => t.UserId.Equals(user.Id) && t.IsActive && !t.IsDelete).OrderByDescending(t => t.DateCreated);
                foreach (var item in collection)
                {
                    response.Add(new TransactionViewModel()
                    {
                        CreateDate = item.DateCreated.GetValueOrDefault().ToString(TIMEFORMAT),
                        IsSuccess = item.IsSuccess ? "Thành công" : "Không thành công",
                        Name = item.Description,
                        Dau = (item.Type == Transaction.TypeTrans.CashIn
                            || item.Type == Transaction.TypeTrans.PostOut
                            || item.Type == Transaction.TypeTrans.CashOutToTraveler)
                            ? "+" : "-",
                        Value = item.Value.ToString()
                    });
                }

            }
            catch (Exception)
            {
                return BadRequest();
            }
            return Ok(response);
        }


    }
}
using APIProject.Model.Models;
using APIProject.Service;
using APIProject.ViewModel;
using StackExchange.Redis;
using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace APIProject.Controllers
{
    [RoutePrefix("api/trip")]
    public class TripController : ApiBaseController
    {
        private readonly ITripService _tripService;
        private readonly IUserService _userService;
        private readonly IOfferService _offerService;
        private readonly ICityService _cityService;
        private readonly IPostService postService;
        private readonly IOrderService _orderService;
        private readonly IImageService imageService;

        public TripController(ITripService tripService, IUserService userService, IOfferService offerService,
            ICityService cityService, IOrderService orderService, IPostService postService, IImageService imageService)
        {
            _tripService = tripService;
            _userService = userService;
            _offerService = offerService;
            _cityService = cityService;
            _orderService = orderService;
            this.postService = postService;
            this.imageService = imageService;
        }
        [HttpPost]
        [Authorize]
        [Route("get-suggest-trip")]
        public IHttpActionResult GetSuggestTrip()
        {
            string username = Utilities.GetUserNameFromRequest(Request);
            //string username = "nguyenquocbao357@gmail.com";
            User user = _userService.GetUsers().FirstOrDefault(u => u.UserName.Equals(username));
            if (user == null)
            {
                return BadRequest("Access Denied");
            }
            var db = Utilities.GetDatabase();

            List<TripViewModel> listResult = new List<TripViewModel>();
            var listPost = postService.GetPosts().Where(p => p.UserId == user.Id
                                                 && p.Type == (int)Post.PostType.Requested
                                                 && p.DeliveryDate > DateTime.UtcNow.AddHours(7)
                                                 && p.IsActive == true
                                                 && p.IsDelete == false);
            if (listPost != null)
            {
                var listOffer = _offerService.GetOffers().Where(p => p.BuyerId == user.Id
                                                    && p.Type == (int)Offer.OfferType.BuyerOffer
                                                    && p.IsActive == true
                                                    && p.IsDelete == false);

                #region get trip with buyfromid
                var listRequested = listPost.Where(p => p.BuyFromId != null
                                                    && p.CountryId == null).GroupBy(x => new { x.BuyFromId, x.DeliveryToId })
                                                     .Select(y => new
                                                     {
                                                         DeliveryToId = y.Key.DeliveryToId,
                                                         BuyFromId = y.Key.BuyFromId,
                                                     }).ToList();

                foreach (var item in listRequested)
                {
                    var listTrip = _tripService.GetTrips().Where(t =>
                                                               ((t.FromCityId == item.DeliveryToId
                                                                && t.ToCityId == item.BuyFromId)
                                                                || (t.FromCityId == item.BuyFromId
                                                                && t.ToCityId == item.DeliveryToId))
                                                                && !listOffer.Any(o => o.TripId == t.Id)
                                                                && t.UserId != user.Id).Select(s => new TripViewModel()
                                                                {
                                                                    DateCreated = s.DateCreated.GetValueOrDefault().ToString("yyyyMMdd HHmmss"),
                                                                    DateUpdated = s.DateUpdated.GetValueOrDefault().ToString("yyyyMMdd HHmmss"),
                                                                    FromCity = s.FromCity.Name + ", " + s.FromCity.Country.Name,
                                                                    ToCity = s.ToCity.Name + ", " + s.ToCity.Country.Name,
                                                                    FromCityId = s.FromCityId,
                                                                    ToCityId = s.ToCityId,
                                                                    FromDate = s.FromDate.GetValueOrDefault().ToString("dd/MM/yyyy"),
                                                                    Name = s.Name,
                                                                    ToDate = s.ToDate.GetValueOrDefault().ToString("dd/MM/yyyy"),
                                                                    TripId = s.Id,
                                                                //sửa lại sau
                                                                TravelerAvartar = s.User.ImageUrl,
                                                                    TravelerName = s.User.LastName,
                                                                    TravelerId = s.User.Id,
                                                                    creditUser = ((String)db.HashGet("CreditTraveler", s.UserId) == null ? 0 : (double)db.HashGet("CreditTraveler", s.UserId))
                                                                }).ToList();
                    listResult.AddRange(listTrip);

                }
                #endregion
                #region get trip with CountryId
                var listRequested_onWeb = listPost.Where(p => p.BuyFromId == null
                                                            && p.CountryId != null)
                                                    .GroupBy(x => new { x.CountryId, x.DeliveryToId })
                                                   .Select(y => new
                                                   {
                                                       DeliveryToId = y.Key.DeliveryToId,
                                                       CountryId = y.Key.CountryId,
                                                   }).ToList();

                foreach (var item in listRequested_onWeb)
                {
                    var listTrip = _tripService.GetTrips().Where(t =>
                                                                    ((t.FromCityId == item.DeliveryToId
                                                                     && t.ToCity.Country.Id == item.CountryId)
                                                                     || (t.FromCity.CountryID == item.CountryId
                                                                     && t.ToCityId == item.DeliveryToId)
                                                                )
                                                                 && !listOffer.Any(o => o.TripId == t.Id)
                                                                 && !listResult.Any(o => o.TripId == t.Id)
                                                                 && t.UserId != user.Id).Select(s => new TripViewModel()
                                                                 {
                                                                     DateCreated = s.DateCreated.GetValueOrDefault().ToString("yyyyMMdd HHmmss"),
                                                                     DateUpdated = s.DateUpdated.GetValueOrDefault().ToString("yyyyMMdd HHmmss"),
                                                                     FromCity = s.FromCity.Name + ", " + s.FromCity.Country.Name,
                                                                     ToCity = s.ToCity.Name + ", " + s.ToCity.Country.Name,
                                                                     FromCityId = s.FromCityId,
                                                                     ToCityId = s.ToCityId,
                                                                     FromDate = s.FromDate.GetValueOrDefault().ToString("dd/MM/yyyy"),
                                                                     Name = s.Name,
                                                                     ToDate = s.ToDate.GetValueOrDefault().ToString("dd/MM/yyyy"),
                                                                     TripId = s.Id,
                                                                 //sửa lại sau
                                                                 TravelerAvartar = s.User.ImageUrl,
                                                                     TravelerName = s.User.LastName,
                                                                     TravelerId = s.User.Id,
                                                                     creditUser = ((String)db.HashGet("CreditTraveler", s.UserId) == null ? 0 : (double)db.HashGet("CreditTraveler", s.UserId))
                                                                 }).ToList();
                    listResult.AddRange(listTrip);

                }
                #endregion
                return Ok(listResult.OrderByDescending(o => o.creditUser).ToList());
            }
            return Ok("");
        }

        [HttpPost]
        [Route("get-trip")]
        public IHttpActionResult GetTrip(OfferViewModel offer)
        {
            var tripVM = new TripViewModel();
            var trip = _tripService.GetTrip(offer.TripId);
            try
            {
                tripVM = new TripViewModel()
                {
                    DateCreated = trip.DateCreated.GetValueOrDefault().ToString("dd/MM/yyyy HH:mm:ss"),
                    DateUpdated = trip.DateUpdated.GetValueOrDefault().ToString("dd/MM/yyyy HH:mm:ss"),
                    FromCity = trip.FromCity.Name + ", " + trip.FromCity.Country.Name,
                    FromDate = trip.FromDate.GetValueOrDefault().ToString("dd/MM/yyyy"),
                    Name = trip.Name,
                    ToCity = trip.ToCity.Name + ", " + trip.ToCity.Country.Name,
                    ToDate = trip.ToDate.GetValueOrDefault().ToString("dd/MM/yyyy"),
                };
            }
            catch (Exception)
            {
                return BadRequest("Getdata error"); ;
            }
            return Ok(tripVM);

        }

        /// <summary>
        /// Create Trip
        /// </summary>
        /// <param name="request"></param>
        /// <returns></returns>
        [HttpPost]
        [Authorize]
        [Route("create-trip")]
        public IHttpActionResult CreateTrip([FromBody]CreateTripViewModel request)
        {
            string username = Utilities.GetUserNameFromRequest(Request);
            if (username == null)
            {
                return BadRequest("Access Denied");
            }

            CreateTripViewModelResponse response = new CreateTripViewModelResponse()
            {
                Status_code = "200",
                Message = "OK",
                Earning = 0,
                NumberOrder = 0,
            };

            try
            {
                request.FromDate.Replace("%2F", "/");
                request.ToDate.Replace("%2F", "/");
                string userId = _userService.GetUsers().Where(u => u.UserName.Equals(username)).FirstOrDefault()?.Id;
                Trip trip = new Trip()
                {
                    UserId = userId,
                    FromCityId = _cityService.GetCities().FirstOrDefault(c => c.Code.Equals(request.FromCityGeonameId)).Id,
                    ToCityId = _cityService.GetCities().FirstOrDefault(c => c.Code.Equals(request.ToCityGeonameId)).Id,
                    FromDate = DateTime.ParseExact(request.FromDate, "dd/MM/yyyy", CultureInfo.InvariantCulture),
                    ToDate = DateTime.ParseExact(request.ToDate, "dd/MM/yyyy", CultureInfo.InvariantCulture),
                    Name = ""
                };
                _tripService.CreateTrip(trip);
                _tripService.SaveTrip();
                response.TripId = trip.Id;
                response.DateCreated = trip.DateCreated.GetValueOrDefault().ToString("dd/MM/yyyy");
            }
            catch (Exception ex)
            {
                return BadRequest(ex.ToString());
            }

            return Ok(response);
        }

        /// <summary>
        /// Delete Trip
        /// </summary>
        /// <param name="id"></param>
        /// <returns></returns>
        [HttpPost]
        [Route("delete-trip")]
        public IHttpActionResult DeleteTrip([FromBody]int id)
        {
            //string username = Utilities.GetUserNameFromRequest(Request);
            string username = "admin";
            if (username == null)
            {
                return BadRequest("Access Denied");
            }

            CreateTripViewModelResponse response = new CreateTripViewModelResponse()
            {
                Status_code = "200",
                Message = "OK",
            };

            try
            {
                string userId = _userService.GetUsers().Where(u => u.UserName.Equals(username)).FirstOrDefault()?.Id;
                Trip trip = _tripService.GetTrip(id);
                if (trip == null)
                {
                    return BadRequest("Invalid data");
                }

                if (!trip.UserId.Equals(userId))
                {
                    return BadRequest("Invalid User");
                }

                trip.IsActive = false;
                trip.DateUpdated = DateTime.Now;
                _tripService.UpdateTrip(trip);
                _tripService.SaveTrip();

            }
            catch (Exception)
            {
                return BadRequest("Can't update trip");
            }

            return Ok(response);
        }

        /// <summary>
        /// Get All Trip By User
        /// </summary>
        /// <returns></returns>
        [HttpPost]
        [Authorize]
        [Route("get-all-trip-by-user")]
        public IHttpActionResult GetAllTripByUser(Post postId)
        {
            string username = Utilities.GetUserNameFromRequest(Request);
            if (username == null)
            {
                return BadRequest("Access Denied");
            }

            GetAllTripByUserResponse response = new GetAllTripByUserResponse()
            {
                Status_code = "200",
                Message = "OK"
            };
            List<GetAllTripByUserItem> listTripResponse = new List<GetAllTripByUserItem>();
            try
            {
                string userId = _userService.GetUsers().Where(u => u.UserName.Equals(username)).FirstOrDefault()?.Id;
                if (userId == null)
                {
                    return BadRequest("user not found");
                }
                IEnumerable<Trip> listTrip = null;
                if (postId.Id == 0)
                {
                    listTrip = _tripService.GetTrips().Where(t => t.UserId.Equals(userId) && t.IsActive );
                }
                else
                {
                    var post = postService.GetPost(postId.Id);
                    if (post.BuyFromId != null)
                    {
                        listTrip = _tripService.GetTrips().Where(t => t.UserId.Equals(userId) 
                                                                && t.IsActive
                                                                //&& t.FromDate > DateTime.UtcNow.AddHours(7)
                                                                && ((post.DeliveryToId == t.ToCityId && post.BuyFromId == t.FromCityId) 
                                                                || (post.DeliveryToId == t.FromCityId && post.BuyFromId == t.ToCityId)));
                    }
                    else
                    {
                        listTrip = _tripService.GetTrips().Where(t => t.UserId.Equals(userId)
                                                                && t.IsActive 
                                                                //&& t.FromDate > DateTime.UtcNow.AddHours(7)
                                                                 && ((post.CountryId == t.ToCity.Country.Id && post.DeliveryToId == t.FromCityId)
                                                                || ( post.CountryId == t.FromCity.Country.Id && post.DeliveryToId == t.ToCityId))
                                                           );
                    }
                }
                foreach (var item in listTrip)
                {
                    var listOrder = _orderService.GetOrders().Where(o => o.Offer.TripId == item.Id);
                    GetAllTripByUserItem responseItem = new GetAllTripByUserItem()
                    {
                        TripId = item.Id,
                        Earning = listOrder.Sum(o => o.Total),
                        NumberOrder = listOrder.Count(),
                        Name = item.FromCity.Name + " - " + item.ToCity.Name,
                        TravelDate = item.FromDate?.ToString("dd/MM/yyyy") + " - " + item.ToDate?.ToString("dd/MM/yyyy"),
                        CreatedDate = item.DateCreated.GetValueOrDefault().ToString(TIMEFORMAT)
                    };

                    listTripResponse.Add(responseItem);
                }
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }

            response.listItem = listTripResponse;
            return Ok(response);
        }
        public IHttpActionResult TestOffer()
        {
            string username = "admin";
            string userId = _userService.GetUsers().Where(u => u.UserName.Equals(username)).FirstOrDefault()?.Id;

            Offer offer = new Offer()
            {
                BuyerId = userId,
                TravellerId = userId,
                DateCreated = DateTime.Now,
                DeliveryDate = DateTime.Now,
                IsActive = true,
                IsDelete = false,
                PostId = 1,
                TripId = 2,
                DateUpdated = DateTime.Now,
                ShippingFee = 100
            };

            try
            {
                _offerService.CreateOffer(offer);
                _offerService.SaveOffer();
            }
            catch (Exception ex)
            {
                return BadRequest(ex.ToString());
                throw;
            }
            return Ok(offer);
        }
    }
}

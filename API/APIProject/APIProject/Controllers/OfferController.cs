using APIProject.Model.Models;
using APIProject.Service;
using APIProject.ViewModel;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web.Http;
using static APIProject.Model.Models.Offer;
using static APIProject.Model.Models.Order;

namespace APIProject.Controllers
{
    [RoutePrefix("api/offer")]
    [Authorize]
    public class OfferController : ApiBaseController
    {
        private readonly IOfferService offerService;
        private readonly IUserService userService;
        private readonly ICityService cityService;
        private readonly ITripService tripService;
        private readonly IOrderService orderService;
        private readonly IImageService imageService;
        private readonly IPostService postService;
        private readonly INotificationService notificationService;
        public OfferController(IOfferService offerService, IUserService userService, IPostService postService, INotificationService notificationService,
            ICityService cityService, IImageService imageService, IOrderService orderService, ITripService tripService)
        {
            this.offerService = offerService;
            this.cityService = cityService;
            this.imageService = imageService;
            this.userService = userService;
            this.postService = postService;
            this.orderService = orderService;
            this.tripService = tripService;
            this.notificationService = notificationService;
        }

        [HttpPost]
        [Route("makeoffer")]
        public IHttpActionResult MakeOffer(OfferViewModel offerVM)
        {
            string username = Utilities.GetUserNameFromRequest(Request);
            if (username == null)
            {
                return Unauthorized();
            }
            User user = userService.GetUsers().FirstOrDefault(u => u.UserName.Equals(username));
            if (user == null)
            {
                return BadRequest("Access Denied");
            }
            try
            {
                Offer offer = new Offer()
                {
                    PostId = offerVM.PostId,
                    DateCreated = DateTime.UtcNow.AddHours(7),
                    IsActive = true,
                    IsDelete = false,
                    ShippingFee = int.Parse(offerVM.ShippingFee),
                    DeliveryDate = DateTime.ParseExact(offerVM.DeliveryDate, "dd/MM/yyyy",
                                   System.Globalization.CultureInfo.InvariantCulture),
                    TripId = offerVM.TripId,
                    TravellerId = user.Id,
                    BuyerId = postService.GetPost(offerVM.PostId)?.UserId,
                    DateUpdated = DateTime.UtcNow.AddHours(7),
                    Message= offerVM.Message,
                    Type = (int)OfferType.TravelerOffer
                };
                offerService.CreateOffer(offer);
                offerService.SaveOffer();
                offerVM.Id = offer.Id;

                var con = userService.GetUsers().FirstOrDefault(u => u.Id.Equals(offer.BuyerId))?.SignalConnect;
                if (con != null)
                {
                    notificationService.CreateNotification(new Notification
                    {
                        DateCreated = DateTime.UtcNow.AddHours(7),
                        IsRead = false,
                        Message = "Nhận được 1 đề nghị mới",
                        Title = "Đơn hàng " + offer.Post.ProductName,
                        UserId = offer.BuyerId
                    });
                    notificationService.SaveNotification();
                    HubUtilities.GetHub().Clients.Client(con).receivedOffer("Nhận được 1 đề nghị mới", offerVM.PostId);
                }
                //update post on redis
                var post = postService.GetPost(offerVM.PostId);
                Utilities.updatePostRedis(post, true);
            }
            catch (Exception)
            {
                return BadRequest("Can't make offer");
            }

            return Ok(offerVM);

        }

        [HttpPost]
        [Route("buyer-make-offer")]
        public IHttpActionResult BuyerMakeOffer(int tripId, int postId)
        {
            string username = Utilities.GetUserNameFromRequest(Request);
            if (username == null)
            {
                return Unauthorized();
            }
            try
            {
                User user = userService.GetUsers().FirstOrDefault(u => u.UserName.Equals(username));
                Post post = postService.GetPost(postId);
                Trip trip = tripService.GetTrip(tripId);

                if (user == null || post == null || trip == null)
                {
                    return BadRequest("Access Denied");
                }

                Offer offer = new Offer()
                {
                    PostId = postId,
                    DateCreated = DateTime.UtcNow.AddHours(7),
                    IsActive = true,
                    IsDelete = false,
                    ShippingFee = post.ShippingFee,
                    DeliveryDate = post.DeliveryDate,
                    TripId = tripId,
                    TravellerId = trip.UserId,
                    BuyerId = post.UserId,
                    DateUpdated = DateTime.UtcNow.AddHours(7),
                    Type = (int)OfferType.BuyerOffer
                };
                offerService.CreateOffer(offer);
                offerService.SaveOffer();

                var con = userService.GetUsers().FirstOrDefault(u => u.Id.Equals(offer.TravellerId)).SignalConnect;
                if (con != null)
                {
                    notificationService.CreateNotification(new Notification
                    {
                        DateCreated = DateTime.UtcNow.AddHours(7),
                        IsRead = false,
                        Message = "Nhận được 1 đề nghị mới",
                        Title = "Chuyến đi " + offer.Trip.FromCity.Name + ", " + offer.Trip.FromCity.Country.Name 
                            + offer.Trip.ToCity.Name + ", " + offer.Trip.ToCity.Country.Name,
                        UserId = offer.BuyerId
                    });
                    notificationService.SaveNotification();
                    HubUtilities.GetHub().Clients.Client(con).receivedOffer("Có 1 đề nghị mới cho chuyến đi ", tripId);
                }
            }
            catch (Exception)
            {
                return BadRequest("Can't make offer");
            }
            return Ok();
        }

        [HttpGet]
        [Route("get-offer-of-buyer")]
        public IHttpActionResult GetOfferOfBuyer(int tripId)
        {
            List<PostViewModel> posts = new List<PostViewModel>();
            string username = Utilities.GetUserNameFromRequest(Request);
            if (username == null)
            {
                return Unauthorized();
            }
            User traveler = userService.GetUsers().Where(u => u.UserName.Equals(username)).FirstOrDefault();
            if (traveler == null)
            {
                return BadRequest("Access Denied");
            }
            try
            {
                var offers = offerService.GetOffers().Where(u => u.TravellerId == traveler.Id && u.TripId == tripId);
                foreach (var offer in offers)
                {
                    var item = postService.GetPost(offer.Id);
                    var city_BuyFrom = cityService.GetCities().Where(c => c.Id == item.BuyFromId && c.Id == item.DeliveryToId).FirstOrDefault();
                    var city_DeliveryTo = cityService.GetCities().Where(c => c.Id == item.DeliveryToId).FirstOrDefault();
                    var user = userService.GetUsers().Where(u => u.Id == item.UserId).FirstOrDefault();
                    var buy_address = item.BuyFrom;

                    posts.Add(new PostViewModel()
                    {
                        Id = item.Id,
                        Buy_Address = new Address()
                        {
                            Name = item.BuyFromAddress,
                            City_name = buy_address == null ? "" : buy_address.Name,
                            Country_name = buy_address == null ? "" : buy_address.Country.Name,
                            Country_code = buy_address == null ? "" : buy_address.Country.Code,
                        },
                        Delivery_Address = new Address()
                        {
                            Name = item.DeliveryToAddress,
                            City_name = item.DeliveryTo.Name,
                            Country_name = item.DeliveryTo.Country.Name,
                            Country_code = item.DeliveryTo.Country.Code,
                        },
                        DateCreated = item.DateCreated.GetValueOrDefault().ToString("yyyyMMdd HHmmss"),
                        DateUpdated = item.DateUpdated.GetValueOrDefault().ToString("yyyyMMdd HHmmss"),
                        Description = item.Description,
                        Price = item.Price,
                        ProductName = item.ProductName,
                        Quantity = item.Quantity,
                        ShippingFee = item.ShippingFee,
                        ImageUrl = item.ListImage.Where(p => p.PostId == item.Id && p.IsActive == true && p.IsDelete == false).FirstOrDefault().Path,
                        NumberOffer = item.ListOffer.Count(),
                        UserAvartar = user.ImageUrl,
                        Username = user.UserName,
                        Nickname = user.LastName,
                        Deposit = item.Deposit,
                        DeliveryDate = item.DeliveryDate.GetValueOrDefault().ToString("dd/MM/yyyy"),
                    });
                }

            }
            catch (Exception ex)
            {
                return BadRequest(ex.ToString());
            }

            return Ok(posts);
        }

        /// <summary>
        /// Use for stm screen. Traveler call
        /// </summary>
        /// <param name="type"> Requested : 0, Transit: 1, Completed: 2</param>
        /// <returns></returns>
        [HttpGet]
        [Route("get-offer-by-trip")]
        public IHttpActionResult GetOfferByTrip(int tripId, int type)
        {
            List<PostViewModel> posts = new List<PostViewModel>();
            string username = Utilities.GetUserNameFromRequest(Request);
            if (username == null)
            {
                return Unauthorized();
            }
            User traveler = userService.GetUsers().Where(u => u.UserName.Equals(username)).FirstOrDefault();
            if (traveler == null)
            {
                return BadRequest("Access Denied");
            }
            try
            {
                switch (type)
                {
                    case 0: // traveler lấy các đề nghị liên quan
                     
                        var offers = offerService.GetOffers()
                            .Where(o => o.IsActive && !o.IsDelete && o.TripId == tripId 
                            && o.Type == (int)OfferType.TravelerOffer)
                            .OrderByDescending(o => o.DateCreated).ToList();
                        foreach (var offer in offers)
                        {
                            var item = offer.Post;
                            if (item.Type == Post.PostType.Requested)
                            {
                                var city_BuyFrom = item.BuyFrom;
                                var city_DeliveryTo = item.DeliveryTo;
                                var user = item.User;
                                var buy_address = item.BuyFrom;
                                posts.Add(new PostViewModel()
                                {
                                    Id = offer.Id,
                                    DeliveryDate = item.DeliveryDate.GetValueOrDefault().ToString(TIMEFORMAT),
                                    BuyFrom = city_BuyFrom == null ? item.Country.Name : item.BuyFromAddress + ", " + city_BuyFrom.Name + ", " + city_BuyFrom.Country.Name,
                                    DeliveryTo = item.DeliveryToAddress + ", " + city_DeliveryTo.Name + ", " + city_DeliveryTo.Country.Name,
                                    url = city_BuyFrom == null ? item.LinkWebsite : "",
                                    domain = city_BuyFrom == null ? item.BuyFromAddress : "",
                                    Price = item.Price,
                                    Description = item.Description,
                                    Username = user.LastName,
                                    ImageUrl = item.ListImage.Where(i => i.IsActive).FirstOrDefault()?.Path,
                                    UserAvartar = user.ImageUrl,
                                    ProductName = item.ProductName,
                                    ShippingFee = offer.ShippingFee,
                                    Quantity = item.Quantity,
                                    Nickname= user.LastName,
                                    DateCreated= item.DateCreated.GetValueOrDefault().ToString(TIMEFORMAT),
                                    Message=offer.Message,
                                    UserId = user.Id
                                });
                            }
                        }
                        break;
                    case 1: // traveler lấy các đề nghị của buyer
                        offers = offerService.GetOffers()
                            .Where(o => o.IsActive && !o.IsDelete && o.TripId == tripId && o.Type == (int)OfferType.BuyerOffer)
                           .OrderByDescending(o => o.DateCreated).ToList();
                        foreach (var offer in offers)
                        {
                            var item = offer.Post;
                            var city_BuyFrom = item.BuyFrom;
                            var city_DeliveryTo = item.DeliveryTo;
                            var user = item.User;
                            var buy_address = item.BuyFrom;
                            posts.Add(new PostViewModel(item.ListOffer.ToList(), item.Price)
                            {
                                Id = item.Id,
                                BuyFrom = city_BuyFrom == null ? item.Country.Name : item.BuyFromAddress + ", " + city_BuyFrom.Name + ", " + city_BuyFrom.Country.Name,
                                DeliveryTo = item.DeliveryToAddress + ", " + city_DeliveryTo.Name + ", " + city_DeliveryTo.Country.Name,
                                url = city_BuyFrom == null ? item.LinkWebsite : "",
                                domain = city_BuyFrom == null ? item.BuyFromAddress : "",
                                DateCreated = item.DateCreated.GetValueOrDefault().ToString("yyyyMMdd HHmmss"),
                                DateUpdated = item.DateUpdated.GetValueOrDefault().ToString("yyyyMMdd HHmmss"),
                                Description = item.Description,
                                Price = item.Price,
                                ProductName = item.ProductName,
                                Quantity = item.Quantity,
                                ShippingFee = item.ShippingFee,
                                ImageUrl = item.ListImage.Where(p => p.PostId == item.Id && p.IsActive == true && p.IsDelete == false).FirstOrDefault().Path,
                                UserAvartar = user.ImageUrl,
                                Username = user.UserName,
                                Nickname = user.LastName,
                                Deposit = item.Deposit,
                                DeliveryDate = item.DeliveryDate.GetValueOrDefault().ToString("dd/MM/yyyy"),
                                Message = offer.Message,
                                UserId = user.Id
                            });
                        }
                        break;
                    case 2: // traveler lấy order
                        var orders = orderService.GetOrders()
                            .Where(o => o.IsActive && !o.IsDelete && o.TravellerId == traveler.Id && o.Status == (int)OrderType.Transit)
                            .OrderByDescending(o => o.DateCreated).ToList();
                        foreach (var order in orders)
                        {
                            var offer = offerService.GetOffers().Where(o => o.Id == order.OfferId && o.TripId == tripId).FirstOrDefault();

                            if (offer != null)
                            {
                                Post post = offer.Post;
                                User user = order.Buyer;
                                var buy_address = post.BuyFrom;
                                posts.Add(new PostViewModel()
                                {
                                    ProductName = post.ProductName,
                                    DateCreated = order.DateCreated.GetValueOrDefault().ToString("dd/MM/yyyy"),
                                    Id = post.Id,
                                    ImageUrl = post.ListImage.Where(i => i.IsActive).FirstOrDefault()?.Path,
                                    Buy_Address = new Address()
                                    {
                                        Name = post.BuyFromAddress,
                                        City_name = buy_address == null ? "" : buy_address.Name,
                                        Country_name = buy_address == null ? "" : buy_address.Country.Name,
                                        Country_code = buy_address == null ? "" : buy_address.Country.Code,
                                    },
                                    Delivery_Address = new Address()
                                    {
                                        Name = post.DeliveryToAddress,
                                        City_name = post.DeliveryTo.Name,
                                        Country_name = post.DeliveryTo.Country.Name,
                                        Country_code = post.DeliveryTo.Country.Code,
                                    },
                                    UserAvartar = user.ImageUrl,
                                    UserId = user.Id,
                                    Nickname = user.LastName,
                                    Username = user.UserName,
                                    DeliveryDate = order.DeliveryDate.GetValueOrDefault().ToString("dd/MM/yyyy"),
                                    BestPrice = order.Total,
                                    Deposit = post.Deposit,
                                    ShippingFee = order.ShippingFee,
                                    OrderId = order.Id,
                                    Quantity = post.Quantity,

                                });
                            }
                        }
                        break;
                    case 3: // traveler lấy các order hoàn tất
                        var ordersCompleted = orderService.GetOrders()
                            .Where(o => o.IsActive && !o.IsDelete && o.TravellerId == traveler.Id 
                            && (o.Status == (int)OrderType.Completed || o.Status == (int)OrderType.WaitAdmin))
                            .OrderByDescending(o => o.DateCreated).ToList();
                        foreach (var order in ordersCompleted)
                        {
                            var offer = offerService.GetOffers().Where(o => o.Id == order.OfferId && o.TripId == tripId).FirstOrDefault();
                            if (offer != null)
                            {
                                Post post = offer.Post;
                                User user = order.Buyer;
                                var buy_address = post.BuyFrom;
                                posts.Add(new PostViewModel()
                                {
                                    ProductName = post.ProductName,
                                    DateCreated = order.DateCreated.GetValueOrDefault().ToString("dd/MM/yyyy"),
                                    Id = post.Id,
                                    ImageUrl = post.ListImage.Where(i => i.IsActive).FirstOrDefault().Path,
                                    Buy_Address = new Address()
                                    {
                                        Name = post.BuyFromAddress,
                                        City_name = buy_address == null ? "" : buy_address.Name,
                                        Country_name = buy_address == null ? "" : buy_address.Country.Name,
                                        Country_code = buy_address == null ? "" : buy_address.Country.Code,
                                    },
                                    Delivery_Address = new Address()
                                    {
                                        Name = post.DeliveryToAddress,
                                        City_name = post.DeliveryTo.Name,
                                        Country_name = post.DeliveryTo.Country.Name,
                                        Country_code = post.DeliveryTo.Country.Code,
                                    },
                                    UserAvartar = user.ImageUrl,
                                    UserId = user.Id,
                                    Nickname = user.LastName,
                                    Username = user.UserName,
                                    DeliveryDate = order.DateUpdated.GetValueOrDefault().ToString("dd/MM/yyyy"),
                                    BestPrice = order.Total,
                                    Deposit = post.Deposit,
                                    ShippingFee = order.ShippingFee,
                                    OrderId = order.Id,
                                    Quantity=post.Quantity

                                });
                            }
                        }
                        break;
                    default:
                        break;
                }
            }
            catch (Exception ex)
            {
                return BadRequest(ex.ToString());
            }

            return Ok(posts);
        }
        /// <summary>
        /// Get Offer By Post Id
        /// </summary>
        /// <param name="id"></param>
        /// <returns></returns>
        [HttpGet]
        [Authorize]
        [Route("get-list-offer-by-post")]
        public IHttpActionResult GetOfferByPost(int id)
        {
            string username = Utilities.GetUserNameFromRequest(Request);
            if (username == null)
            {
                return Unauthorized();
            }

            GetOfferByPostResponse response = new GetOfferByPostResponse();

            try
            {
                string userId = userService.GetUsers().Where(u => u.UserName.Equals(username)).FirstOrDefault()?.Id;
                Post p = postService.GetPost(id);
                if (p == null)
                {
                    return BadRequest("Post wasn't found " + id);
                }
                foreach (var item in p.ListOffer.Where(u => u.IsActive && !u.IsDelete).OrderBy(s => s.ShippingFee).ToList())
                {
                    var trip = item.Trip;
                    GetOfferByPostItem responseItem = new GetOfferByPostItem()
                    {
                        OfferId = item.Id,
                        CreatedDate = item.DateCreated?.ToString(TIMEFORMAT),
                        DeliveryDate = item.DeliveryDate?.ToString("dd/MM/yyyy"),
                        ShippingFee = item.ShippingFee,
                        ProductPrice = p.Price,
                        TravelerId = item.TravellerId,
                        TravelerImage = (item.Traveller.ImageUrl != null && item.Traveller.ImageUrl.Length > 0) ? item.Traveller.ImageUrl : "/Media/UserImage/avatar.png",
                        TravelerName = item.Traveller.LastName,
                        TravelTo = trip.FromCity.Name+" - " + trip.ToCity.Name,
                        Rate = item.Traveller.TravelerScore,
                        Quantity = item.Post.Quantity,
                        Type = item.Type,
                        Message= item.Message
                        
                    };
                    response.ListItem.Add(responseItem);
                }
            }
            catch (Exception e)
            {
                return BadRequest(e.ToString());
            }
            return Ok(response);
        }
        [HttpPost]
        [Route("delete-offer")]
        public IHttpActionResult DeleteOffer(int postId)
        {
            string username = Utilities.GetUserNameFromRequest(Request);
            if (username == null)
            {
                return Unauthorized();
            }

            try
            {
                User user = userService.GetUsers().FirstOrDefault(u => u.UserName.Equals(username));
                if (user == null)
                {
                    return Unauthorized();
                }
                Offer offer = offerService.GetOffers()
                    .FirstOrDefault(o => o.PostId == postId && o.TravellerId.Equals(user.Id)
                    && o.Type == (int)OfferType.TravelerOffer && o.IsActive && !o.IsDelete);
                offer.IsActive = false;
                offerService.UpdateOffer(offer);
                offerService.SaveOffer();
            }
            catch (Exception)
            {
                return BadRequest();
            }
            return Ok();
        }

        [HttpPost]
        [Route("update-offer")]
        public IHttpActionResult UpdateOffer(OfferViewModel offerVM)
        {
            string username = Utilities.GetUserNameFromRequest(Request);
            if (username == null)
            {
                return Unauthorized();
            }
            User user = userService.GetUsers().FirstOrDefault(u => u.UserName.Equals(username));
            if (user == null)
            {
                return BadRequest("Access Denied");
            }
            try
            {
                Offer offer = offerService.GetOffer(offerVM.OfferId);
                if (offer ==  null)
                {
                    return BadRequest("Not found offer");
                }
                offer.ShippingFee = int.Parse(offerVM.ShippingFee);
                offer.DeliveryDate = DateTime.ParseExact(offerVM.DeliveryDate, "dd/MM/yyyy",
                                   System.Globalization.CultureInfo.InvariantCulture);
                offer.Message = offerVM.Message;
                offer.DateUpdated = DateTime.UtcNow.AddHours(7);
                offerService.UpdateOffer(offer);
                offerService.SaveOffer();
                offerVM.Id = offer.Id;

                var con = userService.GetUsers().FirstOrDefault(u => u.Id.Equals(offer.BuyerId)).SignalConnect;
                HubUtilities.GetHub().Clients.Client(con).receivedOffer("Nhận được 1 đề nghị mới " + offer.Post.ProductName, offerVM.PostId);
               
            }
            catch (Exception)
            {
                return BadRequest("Can't make offer");
            }

            return Ok(offerVM);

        }
    }
}

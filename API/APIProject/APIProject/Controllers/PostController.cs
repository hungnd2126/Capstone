using APIProject.Model.Models;
using APIProject.Redis;
using APIProject.Service;
using APIProject.ViewModel;
using StackExchange.Redis;
using System;
using System.Collections.Generic;
using System.Data.Common;
using System.IO;
using System.Linq;
using System.Web;
using Newtonsoft.Json;
using System.Web.Http;
using static APIProject.Model.Models.Post;
using static APIProject.Model.Models.Order;
using System.Net.Http;
using HtmlAgilityPack;
using System.Threading.Tasks;
using static APIProject.Model.Models.Offer;

namespace APIProject.Controllers
{
    [RoutePrefix("api/post")]
    public class PostController : ApiBaseController
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
        public PostController(IPostService postService, IUserService userService,
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

        /// <summary>
        /// Use for order screen, Buyer gọi
        /// </summary>
        /// <param name="type"> Requested : 0, Transit: 1, Completed: 2</param>
        /// <returns></returns>
        [HttpPost]
        [Authorize]
        [Route("get-post-by-user")]
        public IHttpActionResult GetPostByUser([FromBody]RequestedViewModelrq request)
        {
            List<PostViewModel> postsVM = new List<PostViewModel>();
            string username = Utilities.GetUserNameFromRequest(Request);
            //string username = "nguyenquocbao357@gmail.com";
            if (username == null)
            {
                return Unauthorized();
            }
            User buyer = userService.GetUsers().Where(u => u.UserName.Equals(username)).FirstOrDefault();
            if (buyer == null)
            {
                return BadRequest("Access Denied");
            }
            try
            {
                switch (request.Type)
                {
                    case 0:
                        var posts = postService.GetPosts().Where(p => p.UserId.Equals(buyer.Id)
                        && (p.Type == PostType.Requested || p.Type == PostType.Expired) && p.IsActive)
                        .OrderByDescending(p => p.DateCreated).ToList();
                        foreach (var item in posts)
                        {
                            var buy_address = item.BuyFrom;
                            postsVM.Add(new PostViewModel(item.ListOffer.ToList(), item.Price)
                            {
                                ProductName = item.ProductName,
                                DateCreated = item.DateCreated.GetValueOrDefault().ToString(TIMEFORMAT),
                                Id = item.Id,
                                ImageUrl = item.ListImage.Where(i => i.IsActive).FirstOrDefault()?.Path,
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
                                Username = buyer.DisplayName,
                                UserAvartar = buyer.ImageUrl,
                                UserId = buyer.Id,
                                Nickname= buyer.LastName,
                                DeliveryDate = item.DeliveryDate.GetValueOrDefault().ToString("dd/MM/yyyy"),
                                ShippingFee = item.ShippingFee,
                                Deposit = item.Deposit,
                                Quantity = item.Quantity,
                                Price = item.Price,
                                Description= item.Description,
                                Type= (int)item.Type
                                
                            });
                        }
                        break;
                    case 1:
                        var orders = orderService.GetOrders()
                            .Where(o => o.BuyerId == buyer.Id && o.IsActive && o.Status == (int)OrderType.Transit).OrderByDescending(s => s.DateCreated).ToList();
                        foreach (var order in orders)
                        {
                            Post post = order.Offer.Post;
                            User user = order.Traveller;
                            var buy_address = post.BuyFrom;
                            var delivery_to = post.DeliveryTo;
                            var tracking = trackingOrderService.GetTrackingOrders().Where(t => t.OrderId == order.Id).OrderByDescending(o => o.DateCreated).Take(1).FirstOrDefault();
                            postsVM.Add(new PostViewModel()
                            {
                                ProductName = post.ProductName,
                                DateCreated = order.DateCreated.GetValueOrDefault().ToString(TIMEFORMAT),
                                Id = order.Offer.Post.Id,
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
                                    City_name = delivery_to.Name,
                                    Country_name = delivery_to.Country.Name,
                                    Country_code = delivery_to.Country.Code,
                                },
                                UserAvartar =user.ImageUrl,
                                UserId = user.Id,
                                Username = user.UserName,
                                Nickname = user.LastName,
                                DeliveryDate = order.DeliveryDate.GetValueOrDefault().ToString(TIMEFORMAT),
                                BestPrice = order.Total,
                                Deposit = post.Deposit,
                                ShippingFee = order.ShippingFee,
                                OrderId = order.Id,
                                Timeline = (tracking == null ? null : new TrackingOrderViewModel()
                                {
                                    DateCreated = tracking.DateCreated.GetValueOrDefault().ToString("mm:HH dd/MM/yyyy"),
                                    Description = tracking.Description,
                                    Status = tracking.Status
                                }),
                                Quantity= post.Quantity
                            });
                        }
                        break;

                    case 2:
                        var ordersCompleted = orderService.GetOrders().Where(o => o.BuyerId == buyer.Id && o.IsActive
                        && (o.Status == (int)OrderType.Completed || o.Status == (int)OrderType.WaitAdmin)).OrderByDescending(s => s.DateCreated).ToList();
                        if (ordersCompleted != null && ordersCompleted.Count() > 0)
                        {
                            foreach (var item in ordersCompleted)
                            {
                                User user = item.Traveller;
                                Post post = item.Offer.Post;
                                var buy_address = post.BuyFrom;
                                var delivery_to = post.DeliveryTo;
                                var tracking = trackingOrderService.GetTrackingOrders().Where(t => t.OrderId == item.Id).OrderByDescending(o => o.DateCreated).Take(1).FirstOrDefault();
                                postsVM.Add(new PostViewModel()
                                {
                                    ProductName = post.ProductName,
                                    DateCreated = item.DateCreated.GetValueOrDefault().ToString(TIMEFORMAT),
                                    Id = item.Offer.Post.Id,
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
                                        City_name = delivery_to.Name,
                                        Country_name = delivery_to.Country.Name,
                                        Country_code = delivery_to.Country.Code,
                                    },
                                    UserAvartar = user.ImageUrl,
                                    UserId = user.Id,
                                    Username = user.UserName,
                                    Nickname = user.LastName,
                                    DeliveryDate = item.DeliveryDate.GetValueOrDefault().ToString(TIMEFORMAT),
                                    BestPrice = item.Total,
                                    Deposit = post.Deposit,
                                    ShippingFee = item.ShippingFee,
                                    
                                    OrderId = item.Id,
                                    Timeline = (tracking == null ? null : new TrackingOrderViewModel()
                                    {
                                        DateCreated = tracking.DateCreated.GetValueOrDefault().ToString("mm:HH dd/MM/yyyy"),
                                        Description = tracking.Description,
                                        Status = tracking.Status
                                    }),
                                    Quantity = item.Offer.Post.Quantity
                                });
                            }
                        }
                        break;
                }
            }
            catch (Exception ex)
            {
                return BadRequest(ex.ToString());
            }

            RequestedVMReturn response = new RequestedVMReturn()
            {
                Status_code = "200",
                Message = "OK",
                ListRequested = postsVM
            };
            return Ok(response);


        }
        public List<PostViewModel> GetPostInRedis(string value)
        {

            var db = Utilities.GetDatabase();
            var listPost = db.HashGetAll("Post_" + value);
            var listResult = new List<PostViewModel>();
            foreach (var item in listPost)
            {
                var model = PostViewModel.FromJson(item.Value.ToString());
                listResult.Add(model);
            }
            return listResult.ToList();

        }
        [HttpPost]
        [Route("get_post_offed_trip")]
        public List<PostViewModel> GetPostOffed(TripViewModel trip)
        {
            var offers = offerService.GetOffers().Where(o => o.TripId == trip.TripId).ToList();
            List<PostViewModel> posts = new List<PostViewModel>();
            foreach (var offer in offers)
            {
                var tmp = postService.GetPosts().Where(p => p.Id == offer.PostId).ToList();
                foreach (var p in tmp)
                {
                    User user = userService.GetUsers().Where(u => u.Id == p.UserId).FirstOrDefault();
                    var post = new PostViewModel()
                    {
                        Id = p.Id,
                        DeliveryDate = p.DeliveryDate.GetValueOrDefault().ToString(TIMEFORMAT),
                        Price = p.Price,
                        Username = user.DisplayName,
                        ImageUrl = p.ListImage.Where(i => i.IsActive).FirstOrDefault().Path,
                        UserAvartar = user.ImageUrl,
                        ProductName = p.ProductName,
                        ShippingFee = p.ShippingFee,

                    };
                    posts.Add(post);
                }
            }
            return posts;

        }
        [HttpPost]
        [Route("reloadRedis")]
        public List<PostViewModel> reloadRedis()
        {
            return Utilities.reloadPostRedis(cityService, postService, Utilities.GetDatabase());
        }

        [HttpPost]
        [Authorize]
        [Route("get-post-by-trip")]
        public IHttpActionResult GetPostByTrip(TripViewModel trip)
        {
            try
            {
                string username = Utilities.GetUserNameFromRequest(Request);
                User user = userService.GetUsers().FirstOrDefault(u => u.UserName.Equals(username));
                if (user == null)
                {
                    return BadRequest("Access Denied");
                }
                var countryTo = cityService.GetCity(trip.ToCityId).Country;
                var countryFrom = cityService.GetCity(trip.FromCityId).Country;
                var listResult = new List<PostViewModel>();
                var listRequested = postService.GetPosts().Where(p => p.UserId == user.Id
                                                     && p.DeliveryDate > DateTime.UtcNow.AddHours(7)
                                                     && p.Type == Post.PostType.Requested
                                                     && p.IsActive == true
                                                     && p.IsDelete == false
                                                     && (p.BuyFromId != null ?
                                                         ((p.DeliveryToId == trip.FromCityId && p.BuyFromId == trip.ToCityId)
                                                         ||(p.DeliveryToId == trip.ToCityId  && p.BuyFromId == trip.FromCityId))

                                                        : ((p.CountryId == countryTo.Id && p.DeliveryToId == trip.FromCityId)
                                                        || (p.CountryId == countryFrom.Id && p.DeliveryToId == trip.ToCityId))

                                                    )).Select(a => new PostViewModel()
                                                    {
                                                        Id = a.Id,
                                                        Buy_Address = new Address()
                                                        {
                                                            Name = a.BuyFromAddress,
                                                            City_name = a.BuyFromId == null ? "" : a.BuyFrom.Name,
                                                            Country_name = a.BuyFromId == null ? "" : a.BuyFrom.Country.Name,
                                                            Country_code = a.BuyFromId == null ? "" : a.BuyFrom.Country.Code,
                                                        },
                                                        Delivery_Address = new Address()
                                                        {
                                                            Name = a.DeliveryToAddress,
                                                            City_name = a.DeliveryTo.Name,
                                                            Country_name = a.DeliveryTo.Country.Name,
                                                            Country_code = a.DeliveryTo.Country.Code,
                                                        },
                                                        DateCreated = a.DateCreated.GetValueOrDefault().ToString("yyyyMMdd HHmmss"),
                                                        DateUpdated = a.DateUpdated.GetValueOrDefault().ToString("yyyyMMdd HHmmss"),
                                                        Description = a.Description,
                                                        Price = a.Price,
                                                        ProductName = a.ProductName,
                                                        Quantity = a.Quantity,
                                                        ShippingFee = a.ShippingFee,
                                                        ImageUrl = a.ListImage.Where(p => p.PostId == a.Id).FirstOrDefault().Path,
                                                        NumberOffer = a.ListOffer.Count(),
                                                        DeliveryDate = a.DeliveryDate.GetValueOrDefault().ToString("dd/MM/yyyy"),
                                                    }).ToList();


                return Ok(listRequested);
            }
            catch (Exception e)
            {
                BadRequest(e.ToString());
            }
            return Ok("");
        }

        [HttpPost]
        [Authorize]
        [Route("getallpost")]
        public IHttpActionResult GetAllPost()
        {
            try
            {
                var db = Utilities.GetDatabase();
                string username = Utilities.GetUserNameFromRequest(Request);
                #region future trip
                var user = userService.GetUsers().Where(u => u.UserName.Equals(username)).FirstOrDefault();
                var trips = tripService.GetTrips().Where(t => t.UserId == user.Id && t.FromDate > DateTime.UtcNow.AddHours(7)
                            && t.IsActive == true).GroupBy(
                    x => new { x.FromCityId, x.ToCityId }).ToList();
                var listPostVM = new List<PostViewModel>();
                List<Offer> listOffer = null;
                if (trips.Count > 0)
                {
                     listOffer = offerService.GetOffers().Where(o => o.TravellerId == user.Id && o.Type == (int)OfferType.TravelerOffer
                                      && o.IsActive == true && o.IsDelete == false).ToList();
                    foreach (var p in trips)
                    {
                        var countryTo = cityService.GetCity(p.Key.ToCityId).Country;
                        var countryFrom = cityService.GetCity(p.Key.FromCityId).Country;

                        #region get post
                        #region match buy from  buy from  delivery to
                        var listPost = db.HashGetAll("Post_" + p.Key.ToCityId.ToString() + "_" + p.Key.FromCityId.ToString());
                        foreach (var item in listPost)
                        {
                            var model = PostViewModel.FromJson(item.Value.ToString());
                            if (model.Username != username)
                            {
                                var checkExist = listOffer.FirstOrDefault(o => o.PostId == model.Id);
                                if (checkExist == null)
                                {
                                    listPostVM.Add(model);
                                }
                             
                            }
                        }
                        #endregion
                        #region match delivery to and buy from 
                        listPost = db.HashGetAll("Post_" + p.Key.FromCityId.ToString() + "_" + p.Key.ToCityId.ToString());
                        foreach (var item in listPost)
                        {
                            var model = PostViewModel.FromJson(item.Value.ToString());
                            if (model.Username != username)
                            {
                                var checkExist = listOffer.FirstOrDefault(o => o.PostId == model.Id);
                                if (checkExist == null)
                                {
                                    listPostVM.Add(model);
                                }
                            }
                        }
                        #endregion
                        #endregion
                        #region get post_onWeb
                        #region match buy from and delivery to
                        var listPostOnWeb = db.HashGetAll("Post_onWeb_" + countryTo.Id.ToString() + "_" + p.Key.FromCityId.ToString());
                        foreach (var item in listPostOnWeb)
                        {
                            var model = PostViewModel.FromJson(item.Value.ToString());
                            if (model.Username != username)
                            {
                                var checkExist = listOffer.FirstOrDefault(o => o.PostId == model.Id);
                                if (checkExist==null)
                                {
                                    listPostVM.Add(model);
                                }
                            }
                        }
                        #endregion
                        #region match buy from and delivery to
                        listPostOnWeb = db.HashGetAll("Post_onWeb_" + countryFrom.Id.ToString() + "_" + p.Key.ToCityId.ToString());
                        foreach (var item in listPostOnWeb)
                        {
                            var model = PostViewModel.FromJson(item.Value.ToString());
                            if (model.Username != username)
                            {
                                var checkExist = listOffer.FirstOrDefault(o => o.PostId == model.Id);
                                if (checkExist == null)
                                {
                                    listPostVM.Add(model);
                                }
                            }
                        }
                        #endregion
                        #endregion
                    }
                  

                }
                #endregion
                #region current trip
                var currentTrips = tripService.GetTrips().Where(t => t.UserId == user.Id 
                                                                && t.FromDate <= DateTime.UtcNow.AddHours(7)
                                                                && t.ToDate >= DateTime.UtcNow.AddHours(7)
                                                                && t.IsActive == true)
                                                                .GroupBy(x => new { x.FromCityId, x.ToCityId }).ToList();
                if (currentTrips.Count > 0)
                {
                    if (listOffer == null)
                    {
                        listOffer = offerService.GetOffers().Where(o => o.TravellerId == user.Id && o.Type == (int)OfferType.TravelerOffer
                                      && o.IsActive == true && o.IsDelete == false).ToList();
                    }
                    foreach (var p in currentTrips)
                    {
                        var countryTo = cityService.GetCity(p.Key.ToCityId).Country;
                        var countryFrom = cityService.GetCity(p.Key.FromCityId).Country;

                        #region get post
                        #region match buy from  buy from  delivery to
                        var listPost = db.HashGetAll("Post_" + p.Key.ToCityId.ToString() + "_" + p.Key.FromCityId.ToString());
                        foreach (var item in listPost)
                        {
                            var model = PostViewModel.FromJson(item.Value.ToString());
                            if (model.Username != username)
                            {
                                var checkExist = listOffer.FirstOrDefault(o => o.PostId == model.Id);
                                if (checkExist == null)
                                {
                                    listPostVM.Add(model);
                                }

                            }
                        }
                        #endregion
                        #region match delivery to and buy from 
                        listPost = db.HashGetAll("Post_" + p.Key.FromCityId.ToString() + "_" + p.Key.ToCityId.ToString());
                        foreach (var item in listPost)
                        {
                            var model = PostViewModel.FromJson(item.Value.ToString());
                            if (model.Username != username)
                            {
                                var checkExist = listOffer.FirstOrDefault(o => o.PostId == model.Id);
                                if (checkExist == null)
                                {
                                    listPostVM.Add(model);
                                }
                            }
                        }
                        #endregion
                        #endregion
                        #region get post_onWeb
                        #region match buy from and delivery to
                        var listPostOnWeb = db.HashGetAll("Post_onWeb_" + countryTo.Id.ToString() + "_" + p.Key.FromCityId.ToString());
                        foreach (var item in listPostOnWeb)
                        {
                            var model = PostViewModel.FromJson(item.Value.ToString());
                            if (model.Username != username)
                            {
                                var checkExist = listOffer.FirstOrDefault(o => o.PostId == model.Id);
                                if (checkExist == null)
                                {
                                    listPostVM.Add(model);
                                }
                            }
                        }
                        #endregion
                        #region match buy from and delivery to
                        listPostOnWeb = db.HashGetAll("Post_onWeb_" + countryFrom.Id.ToString() + "_" + p.Key.ToCityId.ToString());
                        foreach (var item in listPostOnWeb)
                        {
                            var model = PostViewModel.FromJson(item.Value.ToString());
                            if (model.Username != username)
                            {
                                var checkExist = listOffer.FirstOrDefault(o => o.PostId == model.Id);
                                if (checkExist == null)
                                {
                                    listPostVM.Add(model);
                                }
                            }
                        }
                        #endregion
                        #endregion
                    }
                    #endregion


                }

                listPostVM = listPostVM.GroupBy(
                  x => new {
                      Id = x.Id,
                      DateCreated = x.DateCreated,
                      DateUpdated = x.DateUpdated,
                      x.BuyFrom,
                      x.DeliveryTo,
                      x.url,
                      x.domain,
                      x.UserId,
                      Description = x.Description,
                      Price = x.Price,
                      ProductName = x.ProductName,
                      Quantity = x.Quantity,
                      ShippingFee = x.ShippingFee,
                      ImageUrl = x.ImageUrl,
                      NumberOffer = x.NumberOffer,
                      UserAvartar = x.UserAvartar,
                      Username = x.Username,
                      Nickname = x.Nickname,
                      Deposit = x.Deposit,
                      DeliveryDate = x.DeliveryDate,
                      Mark = x.Mark
                  }).Select(x => new PostViewModel()
                  {
                      Id = x.Key.Id,
                      DateCreated = x.Key.DateCreated,
                      DateUpdated = x.Key.DateUpdated,
                      BuyFrom = x.Key.BuyFrom,
                      DeliveryTo = x.Key.DeliveryTo,
                      url = x.Key.url,
                      domain = x.Key.domain,
                      Description = x.Key.Description,
                      Price = x.Key.Price,
                      ProductName = x.Key.ProductName,
                      Quantity = x.Key.Quantity,
                      ShippingFee = x.Key.ShippingFee,
                      ImageUrl = x.Key.ImageUrl,
                      NumberOffer = x.Key.NumberOffer,
                      UserAvartar = x.Key.UserAvartar,
                      Username = x.Key.Username,
                      Nickname = x.Key.Nickname,
                      Deposit = x.Key.Deposit,
                      DeliveryDate = x.Key.DeliveryDate,
                      Mark = x.Key.Mark,
                      UserId = x.Key.UserId

                  }).OrderByDescending(o => o.Mark).ToList();
                return Ok(listPostVM);
            }
            catch (Exception e)
            {
                return BadRequest(e.ToString());
            }

        }
        /// <summary>
        /// Use for post detail screen
        /// </summary>
        /// <param name="postId"></param>
        /// <returns>post detail + list offer</returns>
        [HttpGet]
        [Authorize]
        [Route("get-post-detail")]
        public IHttpActionResult GetPostDetail(int postId)
        {
            string username = Utilities.GetUserNameFromRequest(Request);
            if (username == null)
            {
                return Unauthorized();
            }

            Post p = postService.GetPost(postId);
            User user = userService.GetUsers().FirstOrDefault(u => u.UserName.Equals(username));
            if (user == null || user.Id != p.UserId)
            {
                return BadRequest("Access Denied");
            }
            var buy_address = p.BuyFrom;
            var delivery_address = p.DeliveryTo;
            var buyer = p.User;
            PostViewModel vm = new PostViewModel()
            {
                Id = p.Id,
         
                BuyFrom = buy_address == null ? p.Country.Name : p.BuyFromAddress + ", " + buy_address.Name + ", " + buy_address.Country.Name,
                DeliveryTo = p.DeliveryToAddress + ", " + delivery_address.Name + ", " + delivery_address.Country.Name,
                url = buy_address == null ? p.LinkWebsite : "",
                domain = buy_address == null ? p.BuyFromAddress : "",
                DateCreated = p.DateCreated.GetValueOrDefault().ToString(TIMEFORMAT),
                Username = user.LastName,
                UserId= user.Id,
                ImageUrl = p.ListImage.Where(i => i.IsActive).FirstOrDefault()?.Path,
                Description = p.Description,
                ProductName = p.ProductName,
                Quantity = p.Quantity,
                Price = p.Price,
                Nickname= user.LastName,
                UserAvartar = user.ImageUrl,
                BestPrice = (p.ListOffer != null && p.ListOffer.Count > 0) ? p.ListOffer.ToList().OrderBy(o => o.ShippingFee).ToList()[0].ShippingFee : -1,
                ShippingFee = p.ShippingFee,
                NumberOffer = p.ListOffer.Count,
                Deposit = p.Deposit,
                DeliveryDate = p.DeliveryDate.GetValueOrDefault().ToString(TIMEFORMAT)
            };
            if (p.BuyFromId != null)
            {
                vm.Buy_Address = new Address()
                {
                    Name = p.BuyFromAddress,
                    City_name = p.BuyFrom.Name,
                    Country_name = p.BuyFrom.Country.Name,
                    Country_code = p.BuyFrom.Country.Code,
                };
            }
            else if (p.CountryId != null)
            {
                vm.Buy_Address = new Address()
                {
                    Name = p.BuyFromAddress
                };
            }

            return Ok(vm);
        }
        [HttpPost]
        [Route("get-info-by-link")]
#pragma warning disable CS1998 // This async method lacks 'await' operators and will run synchronously. Consider using the 'await' operator to await non-blocking API calls, or 'await Task.Run(...)' to do CPU-bound work on a background thread.
        public async Task<IHttpActionResult> GetInfoByLink(CrawlViewModel model)
#pragma warning restore CS1998 // This async method lacks 'await' operators and will run synchronously. Consider using the 'await' operator to await non-blocking API calls, or 'await Task.Run(...)' to do CPU-bound work on a background thread.
        {
            double productPrice = 0;
            string imageURL = "";
            string productName = "";
            string country = "";
            string unit = "";
            Dictionary<string, double> hash = new Dictionary<string, double>();
            hash.Add("$", 24000);
            hash.Add("£", 31000);
            hash.Add("EUR", 28000);

            var website = ecommerceWebsiteService.GetEcommerceWebsites().Where(e => model.url.Contains(e.Url)).FirstOrDefault();

            if (website == null)
            {
                return Ok("");
            }
            else
            {
                country = website.Country.Name;
                var httpClient = new HtmlWeb();
                httpClient.UserAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36";
                var htmlDocument = httpClient.Load(model.url);
                //var htmlDocument = new HtmlDocument();
                //htmlDocument.LoadHtml(html);
                #region crawl of amazon
                if (model.url.Contains("amazon"))
                {
                    try
                    {
                        productName = htmlDocument.DocumentNode.Descendants("span")
                            .Where(n => n.GetAttributeValue("id", "").Equals("productTitle"))
                            .FirstOrDefault().InnerText.Trim();
                       imageURL = htmlDocument.DocumentNode.Descendants("div").Where(n => n.GetAttributeValue("id", "").Equals("imgTagWrapperId")).FirstOrDefault().ChildNodes["img"].GetAttributeValue("data-old-hires", "");
                       var node = htmlDocument.DocumentNode.Descendants("span")
                            .Where(n => n.GetAttributeValue("id", "").Equals("priceblock_ourprice"))
                            .FirstOrDefault().InnerText;
                        foreach (var u in hash)
                        {
                            if (node.Contains(u.Key))
                            {
                                if (node.Contains(".") && node.Contains(","))
                                {
                                    productPrice = Double.Parse(node.Replace(u.Key, "")) * hash[u.Key];
                                }
                                else
                                {
                                    productPrice = Double.Parse(node.Replace(u.Key, "").Replace(",", ".").Replace(u.Key,"")) * hash[u.Key];
                                }
                                unit = u.Key;
                                break;
                            }
                        }

                    }
                    catch
                    {
                    }
                }
                #endregion
                #region crawl of ebay
                else if (model.url.Contains("ebay"))
                {
                    try
                    {
                        var node = htmlDocument.DocumentNode.Descendants("span")
                            .Where(n => n.GetAttributeValue("id", "").Equals("prcIsum")
                            || n.GetAttributeValue("id", "").Equals("mm-saleDscPrc"))
                            .FirstOrDefault().InnerText;

                        string[] tmp = node.Split(' ');
                        if (tmp.Count() > 1)
                        {

                            foreach (var u in hash)
                            {
                                if (node.Contains(u.Key))
                                {
                                    if (tmp[1].Contains(".") && tmp[1].Contains(","))
                                    {
                                        productPrice = Double.Parse(tmp[1].Replace(u.Key, "")) * hash[u.Key];
                                    }
                                    else
                                    {
                                        productPrice = Double.Parse(tmp[1].Replace(u.Key, "").Replace(",", ".")) * hash[u.Key];
                                    }
                                    unit = u.Key;
                                    break;
                                }
                            }
                        }
                        else
                        {
                            foreach (var u in hash)
                            {
                                if (node.Contains(u.Key))
                                {
                                    //  productPrice = Double.Parse(node.Replace(u.Key, "")) * hash[u.Key];
                                    if (node.Contains(".") && node.Contains(","))
                                    {
                                        productPrice = Double.Parse(node.Replace(u.Key, "")) * hash[u.Key];
                                    }
                                    else
                                    {
                                        productPrice = Double.Parse(node.Replace(u.Key, "").Replace(",", ".")) * hash[u.Key];
                                    }
                                    unit = u.Key;
                                    break;
                                }
                            }
                            //productPrice = Double.Parse(node.Replace("$", "").Replace("£", "").Replace("EUR", ""));
                        }
                    }
                    catch
                    {
                        try
                        {
                            var node = htmlDocument.DocumentNode.Descendants("h2").Where(n => n.GetAttributeValue("class", "").Equals("display-price")).FirstOrDefault().InnerText;
                            string[] tmp = node.Split(' ');
                            if (tmp.Count() > 1)
                            {
                                foreach (var u in hash)
                                {
                                    if (node.Contains(u.Key))
                                    {
                                        if (tmp[1].Contains(".") && tmp[1].Contains(","))
                                        {
                                            productPrice = Double.Parse(tmp[1].Replace(u.Key, "")) * hash[u.Key];
                                        }
                                        else
                                        {
                                            productPrice = Double.Parse(tmp[1].Replace(u.Key, "").Replace(",", ".")) * hash[u.Key];
                                        }
                                        //productPrice = Double.Parse(tmp[1].Replace(u.Key, "")) * hash[u.Key];
                                        unit = u.Key;
                                        break;
                                    }
                                }
                            }
                            else
                            {
                                foreach (var u in hash)
                                {
                                    if (node.Contains(u.Key))
                                    {
                                        if (node.Contains(".") && node.Contains(","))
                                        {
                                            productPrice = Double.Parse(node.Replace(u.Key, "")) * hash[u.Key];
                                        }
                                        else
                                        {
                                            productPrice = Double.Parse(node.Replace(u.Key, "").Replace(",", ".")) * hash[u.Key];
                                        }
                                        unit = u.Key;
                                        break;
                                    }
                                }

                                //productPrice = Double.Parse(node.Replace("$", "").Replace("£", "").Replace("EUR", ""));
                            }
                        }
                        catch { }

                    }
                    try
                    {
                        productName = htmlDocument.DocumentNode.Descendants("h1")
                        .Where(n => n.GetAttributeValue("class", "")
                        .Equals("product-title") || n.GetAttributeValue("id", "")
                        .Equals("itemTitle")).FirstOrDefault().InnerText;
                        if (productName != null)
                        {
                            productName = productName.Replace("Details about  &nbsp;", "").Replace("&nbsp;", "");
                        }
                    }
                    catch { }
                    try
                    {
                        imageURL = htmlDocument.DocumentNode.Descendants("li")
                       .Where(n => n.GetAttributeValue("class", "")
                       .Equals("vi-image-gallery__image-container vi-image-gallery__image-container--square")).FirstOrDefault()
                       .FirstChild.ChildNodes["img"].GetAttributeValue("src", "");
                    }
                    catch
                    {
                        imageURL = htmlDocument.DocumentNode.Descendants("img")
                      .Where(n => n.GetAttributeValue("id", "")
                      .Equals("")).FirstOrDefault()
                      .GetAttributeValue("src", "");
                    }
                }
                #endregion
                #region crawl of walmart
                else if (model.url.Contains("walmart"))
                {

                    productPrice = Double.Parse(htmlDocument.DocumentNode.Descendants("span")
                        .Where(n => n.GetAttributeValue("class", "")
                        .Equals("price display-inline-block arrange-fit price price--stylized"))
                        .FirstOrDefault().FirstChild.GetAttributeValue("aria-label", "").Replace("$", "")) * hash["$"];

                    productName = htmlDocument.DocumentNode.Descendants("h1")
                        .Where(n => n.GetAttributeValue("class", "")
                        .Equals("prod-ProductTitle no-margin font-normal heading-a")).FirstOrDefault().FirstChild.InnerHtml;


                    imageURL = htmlDocument.DocumentNode.Descendants("div")
                       .Where(n => n.GetAttributeValue("class", "")
                       .Equals("prod-hero-image-container")).FirstOrDefault()
                       .ChildNodes["img"].GetAttributeValue("src", "");
                }
                #endregion


            }
            CrawlViewModel crawlViewModel = new CrawlViewModel()
            {

                url = model.url,
                domain = website.Url,
                country = country,
                imageURL = imageURL,
                productName = productName,
                productPrice = productPrice,
            };
            return Ok(crawlViewModel);

        }
        /// <summary>
        /// Use for create post screen
        /// </summary>
        /// <param name="post"></param>
        /// <returns></returns>
        [HttpPost]
        [Authorize]
        [Route("create-post")]
        public IHttpActionResult CreatePost([FromBody]PostViewModel postVM)
        {
            string username = Utilities.GetUserNameFromRequest(Request);
            if (username == null)
            {
                return BadRequest("Access Denied");
            }
            CreatePostResponse response = new CreatePostResponse();


            try
            {
                User user = userService.GetUsers().Where(u => u.UserName.Equals(username)).FirstOrDefault();
                if (user == null)
                {
                    return BadRequest("Not Found User");
                }
                Post post = null;
                //Country_code== geo id 
                var cityDeliveryTo = cityService.GetCities().Where(c => c.Code == postVM.Delivery_Address.Country_code).FirstOrDefault();

                //Country_code== geo id 
                if (postVM.Buy_Address.Country_code == null || postVM.Buy_Address.Country_code == "")
                {
                    var country = countryService.GetCountries().FirstOrDefault(c => c.Name.ToUpper() == postVM.Buy_Address.Country_name.ToUpper());
                    #region tạo post không có city
                    post = new Post()
                    {
                        UserId = user.Id,
                        BuyFromAddress = postVM.Buy_Address.City_name,
                        DeliveryToAddress = postVM.Delivery_Address.Name,
                        DeliveryToId = cityDeliveryTo.Id,
                        Description = postVM.Description,
                        ProductName = postVM.ProductName,
                        Quantity = postVM.Quantity,
                        ShippingFee = postVM.ShippingFee,
                        Price = postVM.Price,
                        CountryId = country.Id,
                        LinkWebsite = postVM.Buy_Address.Name,
                        Type = PostType.Requested,
                        DateUpdated = DateTime.UtcNow.AddHours(7),
                        DeliveryDate = DateTime.ParseExact(postVM.DeliveryDate, "dd/MM/yyyy",
                                      System.Globalization.CultureInfo.InvariantCulture),
                    };
                    #endregion

                }
                else
                {
                    var cityBuyFrom = cityService.GetCities().Where(c => c.Code == postVM.Buy_Address.Country_code).FirstOrDefault();
                    #region tạo post có city
                    post = new Post()
                    {
                        UserId = user.Id,
                        BuyFromAddress = postVM.Buy_Address.Name,
                        BuyFromId = cityBuyFrom.Id,
                        DeliveryToAddress = postVM.Delivery_Address.Name,
                        DeliveryToId = cityDeliveryTo.Id,
                        Description = postVM.Description,
                        ProductName = postVM.ProductName,
                        Quantity = postVM.Quantity,
                        ShippingFee = postVM.ShippingFee,
                        Price = postVM.Price,
                        Type = PostType.Requested,
                        DateUpdated = DateTime.UtcNow.AddHours(7),
                        DeliveryDate = DateTime.ParseExact(postVM.DeliveryDate, "dd/MM/yyyy",
                                      System.Globalization.CultureInfo.InvariantCulture),
                    };
                    #endregion

                }

                postService.CreatePost(post);
                postService.SavePost();
                #region thêm hình
                string fileName = Guid.NewGuid().ToString() + ".jpg";
                string filePath = HttpContext.Current.Server.MapPath("~/Media/ProductImage/") + fileName;
                System.Drawing.Image bitmap = Utilities.ConvertFromBitmap(postVM.ImageUrl);
                bitmap.Save(filePath);

                if (bitmap != null)
                {
                    Image image = new Image()
                    {
                        IsDelete = false,
                        PostId = post.Id,
                        Path = "/Media/ProductImage/" + fileName
                    };
                    imageService.CreateImage(image);
                    imageService.SaveImage();
                };
                #endregion
                #region xử lý deposit
                // nếu có thanh toán trước
                if (postVM.Deposit > 0)
                {
                    // kiểm tra ví. nếu hết tiền
                    if (postVM.Deposit > user.Current_Money)
                    {
                        response.Status_code = "E01";
                    }
                    else
                    {
                        transactionService.CreateTransaction(new Transaction()
                        {
                            DateCreated = DateTime.Now,
                            Description = "Nạp tiền vào đơn hàng " + post.ProductName,
                            IsSuccess = true,
                            IsActive = true,
                            IsDelete = false,
                            PostId = post.Id,
                            Type = Transaction.TypeTrans.PostIn,
                            Value = postVM.Deposit,
                            UserId = user.Id
                        });
                        user.Current_Money -= postVM.Deposit;
                        post.Deposit = postVM.Deposit;
                        postService.UpdatePost(post);
                        userService.UpdateUser(user);
                        transactionService.SaveTransaction();
                    }
                }
                #endregion
                response.Current_Money = user.Current_Money;
            }
            catch (DbException)
            {
                return BadRequest("Create post fail");
            }

            return Ok(response);

        }
        /// <summary>
        /// User for update post screen
        /// </summary>
        /// <param name="post"></param>
        /// <returns></returns>
        [HttpPost]
        [Route("update-post")]
        public IHttpActionResult UpdatePost([FromBody]PostViewModel postVM)
        {

            string username = Utilities.GetUserNameFromRequest(Request);
            if (username == null)
            {
                return BadRequest("Access Denied");
            }
            CreatePostResponse response = new CreatePostResponse();

            Post post = postService.GetPost(postVM.Id);
            if (post == null)
            {
                return BadRequest("Post wasn't found");
            }


            try
            {
                User user = userService.GetUsers().Where(u => u.UserName.Equals(username)).FirstOrDefault();
                if (user == null)
                {
                    return BadRequest("Not Found User");
                }
                // create transaction & update deposit
                if (postVM.Deposit > 0)
                {

                    if (postVM.Deposit > user.Current_Money)
                    {
                        response.Status_code = "E01";
                    }
                    else
                    {
                        transactionService.CreateTransaction(new Transaction()
                        {
                            DateCreated = DateTime.Now,
                            Description = "Nạp tiền vào đơn hàng " + post.ProductName,
                            IsSuccess = true,
                            PostId = post.Id,
                            Type = Transaction.TypeTrans.PostIn,
                            IsDelete = false,
                            Value = postVM.Deposit,
                            UserId = user.Id,
                        });
                        user.Current_Money -= postVM.Deposit;
                        post.Deposit += postVM.Deposit;
                        postService.UpdatePost(post);
                        userService.UpdateUser(user);
                        transactionService.SaveTransaction();
                    }
                }

                //Country_code== geo id 

                if (postVM.DeliveryDate != null && postVM.DeliveryDate.Length > 0)
                {
                    post.DeliveryDate = DateTime.ParseExact(postVM.DeliveryDate, "dd/MM/yyyy",
                                   System.Globalization.CultureInfo.InvariantCulture);
                }

                post.DateUpdated = DateTime.Now;
                post.Description = postVM.Description;
                post.ProductName = postVM.ProductName;
                post.Quantity = postVM.Quantity;
                post.ShippingFee = postVM.ShippingFee;
                post.Price = postVM.Price;

                postService.UpdatePost(post);
                postService.SavePost();

                // update hình
                if (postVM.ImageUrl != null && postVM.ImageUrl != post.ListImage.FirstOrDefault(im => im.IsActive == true)?.Path)
                {
                    string fileName = Guid.NewGuid().ToString() + ".jpg";
                    string filePath = HttpContext.Current.Server.MapPath("~/Media/ProductImage/") + fileName;
                    System.Drawing.Image bitmap = Utilities.ConvertFromBitmap(postVM.ImageUrl);
                    if (bitmap != null)
                    {
                        bitmap.Save(filePath);
                        Image image = new Image()
                        {
                            IsDelete = false,
                            PostId = post.Id,
                            Path = "/Media/ProductImage/" + fileName
                        };

                        imageService.CreateImage(image);
                        imageService.SaveImage();

                        //De-active hình cũ
                        Image oldImage = post.ListImage.Where(i => i.IsActive).LastOrDefault();
                        if (oldImage != null)
                        {
                            oldImage.IsActive = false;
                            imageService.UpdateImage(oldImage);
                            imageService.SaveImage();
                        }
                    }
                }
                Utilities.updatePostRedis(post, false);
                response.Current_Money = user.Current_Money;
            }
            catch (DbException)
            {
                return BadRequest("Can't update post");
            }

            return Ok(response);
        }
        /// <summary>
        /// User for update post screen
        /// </summary>
        /// <param name="id"></param>
        /// <returns></returns>
        [HttpGet]
        [Route("delete-post")]
        public IHttpActionResult DeletePost(int id)
        {
            string username = Utilities.GetUserNameFromRequest(Request);
            if (username == null)
            {
                return BadRequest("Access Denied");
            }
            bool complete = false;
            try
            {
                User user = userService.GetUsers().Where(u => u.UserName.Equals(username)).FirstOrDefault();
                if (user != null)
                {
                    Post p = postService.GetPost(id);
                    if (p == null)
                    {
                        return BadRequest("Post wasn't found");
                    }

                    if (!p.UserId.Equals(user.Id))
                    {
                        return BadRequest("Invalid User");
                    }
                    if (p.Deposit > 0)
                    {
                        user.Current_Money += p.Deposit;
                        transactionService.CreateTransaction(new Transaction()
                        {
                            IsActive = true,
                            IsDelete = false,
                            IsSuccess = true,
                            DateCreated = DateTime.UtcNow.AddHours(7),
                            PostId = p.Id,
                            Value = p.Deposit,
                            Description = "Đơn hàng bị hủy",
                            Type = Transaction.TypeTrans.PostOut,
                            UserId = user.Id
                        });
                        p.Deposit = 0;
                        userService.UpdateUser(user);
                    }
                    p.IsActive = false;
                    postService.UpdatePost(p);
                    postService.SavePost();
                    //update redis
                    Utilities.DeleteonRedis(p, id.ToString());
                }
            }
            catch (DbException)
            {
                return BadRequest("Can't delete post");
            }
            return Ok(complete);
        }
        public IHttpActionResult ReadCityData()
        {
            string raw = File.ReadAllText(HttpContext.Current.Server.MapPath("~/File/data.txt"));
            List<CityCountryCrawl> jsonData = JsonConvert.DeserializeObject<List<CityCountryCrawl>>(raw);
            List<String> country = new List<string>();
            List<String> city = new List<string>();
            if (jsonData != null && jsonData.Count > 0)
            {
                int currentID = 1;
                foreach (var item in jsonData)
                {
                    Country countryitem = new Country();
                    if (country.FirstOrDefault(c => c.Equals(item.country)) == null)
                    {
                        country.Add(item.country);
                        countryitem.Name = item.country;
                        countryService.CreateCountry(countryitem);
                        countryService.SaveCountry();
                        currentID = countryitem.Id;
                    }

                    cityService.CreateCity(new City()
                    {
                        Name = item.name,
                        Code = item.geonameid.ToString(),
                        CountryID = currentID
                    });
                }
            }

            // File.WriteAllLines(HttpContext.Current.Server.MapPath("~/File/country.txt"), country.ToArray());+
            /*    var db = GetDatabase();
                var list = db.HashGetAll("test");
                List<CityCountryCrawl> jsonData = new List<CityCountryCrawl>();
                foreach (var item in list)
                {
                    var model = JsonConvert.DeserializeObject<CityCountryCrawl>(item.Value.ToString());
                    jsonData.Add(model);
                    //   listResult.Add(model);

                }
                var tmp= jsonData.OrderByDescending(o => o.geonameid);*/
            return Ok(jsonData);
        }


        [HttpGet]
        [Route("get-offer")]
        public IHttpActionResult GetOffer(int id)
        {
            //string username = Utilities.GetUserNameFromRequest(Request);
            string username = "admin";
            if (username == null)
            {
                return BadRequest("Access Denied");
            }

            GetOfferByPostResponse response = new GetOfferByPostResponse();

            try
            {
                string userId = userService.GetUsers().Where(u => u.UserName.Equals(username)).FirstOrDefault()?.Id;
                Offer offer = offerService.GetOffer(id);
                GetOfferByPostItem responseItem = new GetOfferByPostItem()
                {
                    OfferId = offer.Id,
                    CreatedDate = offer.DateCreated?.ToString(TIMEFORMAT),
                    DeliveryDate = offer.DeliveryDate?.ToString("dd/MM/yyyy"),
                    ShippingFee = offer.ShippingFee,
                    TravelerId = offer.TravellerId,
                    TravelerImage = (offer.Traveller.ImageUrl != null && offer.Traveller.ImageUrl.Length > 0) ? offer.Traveller.ImageUrl : "/Media/UserImage/avatar.png",
                    TravelerName = offer.Traveller.DisplayName,
                    TravelTo = offer.Trip.ToCity.Name,
                    Rate = offer.Traveller.TravelerScore,
                    Quantity = offer.Post.Quantity,
                };

            }
            catch (Exception e)
            {
                return BadRequest(e.ToString());
            }
            return Ok(response);
        }
        [AllowAnonymous]
        public IHttpActionResult UpdateRedis()
        {
            Utilities.UpdateRedis(postService, cityService, userService, imageService);
            return Ok();
        }
    }
}
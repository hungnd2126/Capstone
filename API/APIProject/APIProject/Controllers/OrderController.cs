using APIProject.Model.Models;
using APIProject.Service;
using APIProject.ViewModel;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Web.Http;
using static APIProject.Model.Models.Order;

namespace APIProject.Controllers
{
    [Authorize]
    [RoutePrefix("api/order")]
    public class OrderController : ApiController
    {
        private readonly IOrderService orderService;
        private readonly IPostService postService;
        private readonly IUserService userService;
        private readonly ITransactionService transactionService;
        private readonly IOfferService offerService;
        private readonly INotificationService notificationService;
        private readonly ITrackingOrderService trackingOrderService;

        public OrderController(IOrderService _orderService, IPostService postService, IUserService userService, ITrackingOrderService trackingOrderService,
            ITransactionService transactionService, IOfferService offerService, INotificationService notificationService)
        {
            this.orderService = _orderService;
            this.postService = postService;
            this.userService = userService;
            this.transactionService = transactionService;
            this.offerService = offerService;
            this.notificationService = notificationService;
            this.trackingOrderService = trackingOrderService;
        }
        [Route("checkouts")]
        [HttpPost]
        public IHttpActionResult Create(OfferViewModel offerViewModel)
        {
            var checkoutVM = new CompleteOrderResponse();
            try
            {
                string username = Utilities.GetUserNameFromRequest(Request);
                var user = userService.GetUsers().Where(u => u.UserName == username).FirstOrDefault();
                Post post = postService.GetPost(offerViewModel.PostId);
                Offer offer = offerService.GetOffer(offerViewModel.OfferId);
                if (post == null || offer == null)
                {
                    return BadRequest("Not found data");
                }
                //update create
                Order order = new Order()
                {
                    IsActive = true,
                    IsDelete = false,
                    BuyerId = user.Id,
                    TravellerId = offerViewModel.TravelerId,
                    Total = post.Price * post.Quantity + offer.ShippingFee,
                    ShippingFee = offer.ShippingFee,
                    DateCreated = DateTime.UtcNow.AddHours(7),
                    DeliveryDate = DateTime.UtcNow.AddHours(7),
                    DateUpdated = DateTime.UtcNow.AddHours(7),
                    OfferId = offerViewModel.OfferId,
                    Status = (int)OrderType.Transit
                };

                orderService.CreateOrder(order);
                orderService.SaveOrder();

                //update post
                post.Type = Post.PostType.Transit;
                var deposit = post.Deposit;
                post.Deposit = (deposit == 0) ? offerViewModel.Deposit : (offerViewModel.Deposit + post.Deposit);
                postService.UpdatePost(post);
                postService.SavePost();

                //create transaction
                Transaction transaction = new Transaction()
                {
                    DateCreated = DateTime.UtcNow.AddHours(7),
                    Description = offerViewModel.Description,
                    IsActive = true,
                    IsDelete = false,
                    IsSuccess = true,
                    Value = offerViewModel.Deposit,
                    OrderId = order.Id,
                    UserId = user.Id
                };
                transactionService.CreateTransaction(transaction);
                transactionService.SaveTransaction();
                checkoutVM.Deposit = post.Deposit;
                checkoutVM.OrderId = order.Id;
                checkoutVM.TransactionId = transaction.Id;
                //delete post on redis
                Utilities.deletePostRedis("Post_" + post.BuyFrom.Name.ToString() + "_" + post.DeliveryTo.Name.ToString(), post.Id.ToString());
            }
            catch
            {
                return BadRequest("Error");
            }
            return Ok(checkoutVM);
        }

        [Route("complete-order")]
        [HttpPost]
        public IHttpActionResult CompleteOrder(PostViewModel model)
        {
            string username = Utilities.GetUserNameFromRequest(Request);
            if (String.IsNullOrEmpty(username))
            {
                return Unauthorized();
            }
            CompleteOrderResponse response = new CompleteOrderResponse();
            try
            {
                var user = userService.GetUsers().Where(u => u.UserName.Equals(username)).FirstOrDefault();
                if (user == null)
                {
                    return Unauthorized();
                }
                var order = orderService.GetOrders().Where(o => o.Offer.PostId == model.Id).FirstOrDefault();
                var post = order.Offer.Post;
                double amount = order.Total - post.Deposit;
                bool IsHasMoney = true;
                if (amount > 0) // nếu chưa thanh toán hết total => kiểm tra ví => tạo transaction => cập nhật post
                {
                    // 1. kiểm tra tiền trong ví
                    if (amount > user.Current_Money) // không đủ tiền
                    {
                        response.Status_code = "E01";
                        IsHasMoney = false;
                    }
                    else // đủ tiền
                    {
                        // 2. tạo transaction
                        transactionService.CreateTransaction(new Transaction()
                        {
                            DateCreated = DateTime.Now,
                            IsActive = true,
                            IsDelete = false,
                            IsSuccess = true,
                            Value = order.Total - post.Deposit,
                            OrderId = order.Id,
                            UserId = user.Id,
                            PostId = post.Id,
                            Type = Transaction.TypeTrans.PostIn,
                            Description = "Hoàn tất đơn hàng " + order.Offer.Post.ProductName
                        });
                        user.Current_Money -= amount;
                        userService.UpdateUser(user);
                    }
                }
                // Chưa thanh toán hết:  đủ tiền => IsHasMoney = true / không đủ tiền => IsHasMoney = false
                // Đã thanh toán hết: IsHasMoney = true
                if (IsHasMoney)
                {
                    // cập nhật lại phần đã thanh toán. Complete => thanh toán hết
                    post.Deposit = order.Total;
                    post.Type = Post.PostType.Completed;
                    postService.UpdatePost(post);

                    // cập nhật lại trạng thái order -> completed
                    order.Status = (int)OrderType.WaitAdmin;
                    order.DateUpdated = DateTime.UtcNow.AddHours(7);
                    orderService.UpdateOrder(order);

                    //save to db
                    orderService.SaveOrder();

                    response.Status_code = "E00";
                    response.Deposit = post.Deposit;
                    response.OrderId = order.Id;
                    response.Current_Money = user.Current_Money;

                    trackingOrderService.CreateTrackingOrder(new TrackingOrder()
                    {
                        Status = (int)TrackingOrder.TrackingOrderType.Finished,
                        OrderId = order.Id,
                        IsDelete = false,
                    });

                    trackingOrderService.SaveTrackingOrder();
                    // notify cho thằng traveler: buyer đã xác nhận
                    string con = order.Offer.Traveller.SignalConnect;
                    if (con != null)
                    {
                        notificationService.CreateNotification(new Notification
                        {
                            DateCreated = DateTime.UtcNow.AddHours(7),
                            IsRead = false,
                            Message = user.LastName + " đã xác nhận hoàn thành đơn hàng " + order.Offer.Post.ProductName,
                            Title = "Chuyến đi " + order.TripId,
                            UserId = order.Offer.TravellerId
                        });
                        notificationService.SaveNotification();
                        HubUtilities.GetHub().Clients.Client(con).completeOrder(user.LastName + " đã xác nhận hoàn thành đơn hàng " +  order.Offer.Post.ProductName, order.Id);
                    }
                }
            }
            catch
            {
                return BadRequest();
            }
            return Ok(response);
        }

        [Route("CreateOrder")]
        [HttpPost]
        public IHttpActionResult CreateOrder(OfferViewModel offerViewModel)
        {
            try
            {
                string username = Utilities.GetUserNameFromRequest(Request);
                var user = userService.GetUsers().Where(u => u.UserName == username).FirstOrDefault();
                Post post = postService.GetPost(offerViewModel.PostId);
                Offer offer = offerService.GetOffer(offerViewModel.OfferId);
                if (post == null || offer == null)
                {
                    return BadRequest("Not found data");
                }

                CreatePostResponse response = new CreatePostResponse();
                if (offerViewModel.Deposit > 0)
                {
                    if (offerViewModel.Deposit > user.Current_Money)
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
                            Value = offerViewModel.Deposit,
                            UserId = user.Id,
                        });
                        user.Current_Money -= offerViewModel.Deposit;
                        post.Deposit += offerViewModel.Deposit;
                        postService.UpdatePost(post);
                        userService.UpdateUser(user);
                        transactionService.SaveTransaction();
                    }
                }
                //update create
                Order order = new Order()
                {
                    IsActive = true,
                    IsDelete = false,
                    BuyerId = user.Id,
                    TravellerId = offerViewModel.TravelerId,
                    Total = post.Price * post.Quantity + offer.ShippingFee,
                    ShippingFee = offer.ShippingFee,
                    DateCreated = DateTime.UtcNow.AddHours(7),
                    DeliveryDate = DateTime.UtcNow.AddHours(7),
                    DateUpdated = DateTime.UtcNow.AddHours(7),
                    Status = (int)OrderType.Transit,
                    OfferId = offer.Id,
                };

                orderService.CreateOrder(order);
                orderService.SaveOrder();

                //update post
                post.Type = Post.PostType.Transit;
                //  post.Deposit = post.Deposit == null ? offerViewModel.Deposit : (offerViewModel.Deposit + post.Deposit);
                postService.UpdatePost(post);
                postService.SavePost();
                var traveler = userService.GetUsers().FirstOrDefault(t => t.Id.Equals(offerViewModel.TravelerId)).SignalConnect;
                if (traveler != null)
                {
                    notificationService.CreateNotification(new Notification
                    {
                        DateCreated = DateTime.UtcNow.AddHours(7),
                        IsRead = false,
                        Message = "Đề nghị cho đơn hàng " + post.ProductName + " đã được đồng ý",
                        Title = "Chuyến đi " + order.TripId,
                        UserId = offerViewModel.TravelerId
                    });
                    notificationService.SaveNotification();
                    HubUtilities.GetHub().Clients.Client(traveler).acceptOffer("Để nghị đã được đồng ý ", offerViewModel.OfferId);
                }
                //delete post on redis
                Utilities.DeleteonRedis(post, post.Id.ToString());
                response.Current_Money = user.Current_Money;
                return Ok(response);
            }
            catch
            {
                return BadRequest("Error");
            }
        }
        [AllowAnonymous]
        // GET api/values
        public IEnumerable<Order> Get()
        {
            return orderService.GetOrders().ToList();
        }

        [Route("admin-complete-order")]
        [HttpGet]
        [AllowAnonymous]
        public IHttpActionResult AdminCompleteOrder(int id)
        {
            try
            {
                Order order = orderService.GetOrder(id);
                User traveler = order.Traveller;

                if (order != null)
                {
                    notificationService.CreateNotification(new Notification()
                    {
                        DateCreated = DateTime.UtcNow.AddHours(7),
                        IsActive = true,
                        IsDelete = false,
                        IsRead = false,
                        Message = "Bạn nhận được " + order.Total + " VNĐ từ đơn hàng hoàn tất",
                        Title = "Hệ thống xác nhận đơn hàng hoàn tất",
                        UserId = traveler.Id

                    });
                    notificationService.SaveNotification();
                    if (traveler.SignalConnect != null)
                    {
                        HubUtilities.GetHub().Clients.Client(traveler.SignalConnect).admincompleteorder(order.Total + " VNĐ", "1");
                    }
                }
            }
            catch (Exception)
            {
                return Ok();
            }


            return Ok();
        }


    }
}
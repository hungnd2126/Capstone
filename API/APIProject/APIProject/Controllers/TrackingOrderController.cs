using APIProject.Model.Models;
using APIProject.Service;
using APIProject.ViewModel;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web.Http;
using static APIProject.Model.Models.TrackingOrder;

namespace APIProject.Controllers
{
    [RoutePrefix("api/tracking-order")]
    //  [Authorize]
    public class TrackingOrderController : ApiController
    {

        private readonly IOrderService orderService;
        private readonly ITrackingOrderService trackingOrderService;
        private readonly IUserService userService;
        private readonly IPostService postService;
        private readonly ITransactionService transactionService;
        private readonly INotificationService notificationService;

        public TrackingOrderController(ITrackingOrderService trackingOrderService, IOrderService orderService, IPostService postService,
            IUserService userService, ITransactionService transactionService, INotificationService notificationService)
        {
            this.orderService = orderService;
            this.userService = userService;
            this.postService = postService;
            this.transactionService = transactionService;
            this.trackingOrderService = trackingOrderService;
            this.notificationService = notificationService;
        }

        [HttpPost]
        [Route("get-time-line")]
        public IHttpActionResult GetTimeLine(PostViewModel model)
        {
            try
            {
                var trackingOrders = trackingOrderService.GetTrackingOrders().Where(t => t.OrderId == model.OrderId)?.ToList();
                var listResult = new List<TrackingOrderViewModel>();
                if (trackingOrders != null)
                {
                    foreach (var item in trackingOrders)
                    {
                        var tmp = new TrackingOrderViewModel();
                        tmp.DateCreated = item.DateCreated.GetValueOrDefault().ToString("HH:mm dd/MM/yyyy");
                        tmp.OrderId = item.OrderId;
                        tmp.Status = item.Status;
                        tmp.Description = item.Description;
                        tmp.Longitude = item.Longitude;
                        tmp.Latitude = item.Latitude;

                        /*  switch (item.Status)
                          {
                              case (int)TrackingOrderType.Started:
                                  tmp.Status = "Đã khởi hành";
                                  break;
                              case (int)TrackingOrderType.Arrived:
                                  tmp.Status = "Đã đến nơi";
                                  break;
                              case (int)TrackingOrderType.Buyed:
                                  tmp.Status = "Đã mua được hàng";
                                  break;
                              case (int)TrackingOrderType.CameBack:
                                  tmp.Status = "Đã về";
                                  break;
                              case (int)TrackingOrderType.CanShipping:
                                  tmp.Status = "Có thể giao hàng";
                                  break;
                              case (int)TrackingOrderType.Finished:
                                  tmp.Status = "Giao hàng thành công";
                                  break;
                              case (int)TrackingOrderType.CanNotBuy:
                                  tmp.Status = "Không thể mua hàng";
                                  break;
                              case (int)TrackingOrderType.Cancel:
                                  tmp.Status = "Đơn hàng đã bị hủy";
                                  break;
                          }*/
                        listResult.Add(tmp);
                    }
                }
                return Ok(listResult);

            }
            catch (Exception ex)
            {
                return BadRequest(ex.ToString());
            }
        }
        [HttpPost]
        [Route("update-time-line")]
        public IHttpActionResult UpdateTimeline(TrackingOrder model)
        {
            try
            {
                model.IsActive = true;
                model.IsDelete = false;
                model.DateCreated = DateTime.UtcNow.AddHours(7);
                trackingOrderService.CreateTrackingOrder(model);
                Order order = orderService.GetOrder(model.OrderId);
                if (order != null)
                {
                    User buyer = order.Buyer;
                    if (buyer != null)
                    {
                        if (model.Status == (int)TrackingOrderType.Cancel)
                        {

                            buyer.Current_Money += order.Offer.Post.Deposit;
                            Post post = order.Offer.Post;
                            transactionService.CreateTransaction(new Transaction()
                            {
                                PostId = post.Id,
                                IsActive = true,
                                IsDelete = false,
                                IsSuccess = true,
                                UserId = buyer.Id,
                                DateCreated = DateTime.UtcNow.AddHours(7),
                                Value = post.Deposit,
                                Type = Transaction.TypeTrans.PostOut,
                            });
                            post.Deposit = 0;
                            userService.UpdateUser(buyer);
                            postService.UpdatePost(post);


                        }
                    }
                    if (buyer.SignalConnect != null)
                    {
                        string status = "Đang ở ";
                        switch (model.Status)
                        {
                            case (int)TrackingOrderType.Started:
                                status = "Đang ở " + model.Description;
                                break;
                            case (int)TrackingOrderType.Arrived:
                                status = "Đã mua được hàng";
                                break;
                            case (int)TrackingOrderType.Buyed:
                                status = "Có thể giao hàng";
                                break;
                            case (int)TrackingOrderType.CameBack:
                                status = "Không thể mua hàng";
                                break;
                            case (int)TrackingOrderType.CanShipping:
                                status = "Đơn hàng đã bị hủy";
                                break;
                            case (int)TrackingOrderType.Finished:
                                status = "Giao hàng thành công";
                                break;
                        }


                        notificationService.CreateNotification(new Notification()
                        {
                            DateCreated = DateTime.UtcNow.AddHours(7),
                            IsActive = true,
                            IsDelete = false,
                            IsRead = true,
                            Message = status,
                            Title = "Đơn hàng " + order.Id,
                            UserId = buyer.Id
                        });
                        notificationService.SaveNotification();
                        HubUtilities.GetHub().Clients.Client(buyer.SignalConnect).newTimeline(status, order.Id);
                    }
                }

                trackingOrderService.SaveTrackingOrder();
                TrackingOrderViewModel tracking = new TrackingOrderViewModel()
                {
                    DateCreated = model.DateCreated.GetValueOrDefault().ToString("HH:mm dd/MM/yyyy"),
                    Status = model.Status,
                    Longitude = model.Longitude,
                    Latitude = model.Latitude,
                    OrderId = model.OrderId,
                    Description = model.Description
                };
                return Ok(tracking);

            }
            catch (Exception ex)
            {
                return BadRequest(ex.ToString());
            }
        }
    }
}

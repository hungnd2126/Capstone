using APIProject.Model.Models;
using APIProject.Service;
using Braintree;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;
using System.Web;
using System.Web.Mvc;

namespace APIProject.Controllers
{
    public class CheckoutsController : Controller
    {
        private readonly IOrderService orderService;

        public CheckoutsController(IOrderService _orderService)
        {
            this.orderService = _orderService;
        }
        public IBraintreeConfiguration config = new BraintreeConfiguration();

        public static readonly TransactionStatus[] transactionSuccessStatuses = {
                                                                                    TransactionStatus.AUTHORIZED,
                                                                                    TransactionStatus.AUTHORIZING,
                                                                                    TransactionStatus.SETTLED,
                                                                                    TransactionStatus.SETTLING,
                                                                                    TransactionStatus.SETTLEMENT_CONFIRMED,
                                                                                    TransactionStatus.SETTLEMENT_PENDING,
                                                                                    TransactionStatus.SUBMITTED_FOR_SETTLEMENT
                                                                                };
        private static readonly HttpClient client = new HttpClient();
        //  [HttpPost]
        //public async void New(OfferViewModel offerView)

        public async Task<ActionResult> New()
        {


            /*  var gateway = config.GetGateway();
              var clientToken = gateway.ClientToken.Generate();

              ViewBag.amount = long.Parse(offerView.Total.Replace(",","").Replace("VND","").Trim())/ unit;
              ViewBag.total = offerView.Total;
              ViewBag.TravelerId = offerView.TravelerId;
              ViewBag.BuyerId = "fffd7bfd-4221-4096-a33b-07fefb605ac5";
              ViewBag.shippingFee = offerView.ShippingFee;
              ViewBag.OfferId = offerView.OfferId;

              ViewBag.ClientToken = clientToken;

              return View();*/
           /* var values = new Dictionary<string, string>
                {
                   { "merchant_id", "46149" },
                   { "merchant_password", "02fc66bab505b6e0ca93b6be64e98d82" },
                   { "user_email", "hungnd2126@gmail.com" },
                   { "receive_email", "hung.nguyen21296@gmail.com" },
                   { "amount", "20000" },
                   { "reference_code", "zaQ@123" },
                   { "reason", "world" },
                };

            var content = new FormUrlEncodedContent(values);

            var response = await client.PostAsync("https://sandbox.nganluong.vn:8088/nl35/payoutTranfer.php/tranfer", content);

            var responseString = await response.Content.ReadAsStringAsync();*/

            string myJson = "{'merchant_id': '46149'," +
                              "'merchant_password':'26515499b28d1188f87b7f54bab8ecc7'" +
                                "'user_email':'hungnd2126@gmail.com'" +
                                  "'receive_email':'hung.nguyen21296@gmail.com'" +
                                    "'amount':'20000'" +
                                      "'reference_code':'zaQ@123'" +
                                      "'reason':'world'" +

                "}";

            var client = new HttpClient();
           
                var response = await client.PostAsync(
                    "https://sandbox.nganluong.vn:8088/nl35/payoutTranfer.php/tranfer",
                     new StringContent(myJson, Encoding.UTF8, "application/json"));
            
           
            return View();
           // return async Json(new { data = responseString });
        }

        public ActionResult Create()
        {
            var gateway = config.GetGateway();
            Decimal amount = 0;
            int total = 0;
            int shippingFee = 0;
            int offerId = 0;
            string buyerId = "";
            string travlerId = "";
            try
            {
                amount = Convert.ToDecimal(Request["amount"]);

            }
            catch (FormatException)
            {
                TempData["Flash"] = "Error: 81503: Amount is an invalid format.";
                //   return RedirectToAction("New");
            }
            total = int.Parse(Request["total"].Replace(",", "").Replace("VND", ""));
            shippingFee = int.Parse(Request["shippingFee"]);
            offerId = Convert.ToUInt16(Request["OfferId"]);
            buyerId = Request["Id"].ToString();
            travlerId = Request["TravelerId"].ToString();
            var nonce = Request["payment_method_nonce"];
            var request = new TransactionRequest
            {
                Amount = amount,
                PaymentMethodNonce = nonce,
                Options = new TransactionOptionsRequest
                {
                    SubmitForSettlement = true
                }
            };

            Result<Braintree.Transaction> result = gateway.Transaction.Sale(request);
            if (result.IsSuccess())
            {
                Braintree.Transaction transaction = result.Target;
                //   return RedirectToAction("Show", new { id = transaction.Id });
                string respone = Json(new { success = true, transactionAmount = transaction.Amount, transactionId = transaction.Id }).Data.ToString();
                ViewBag.respone = respone;
                Order order = new Order()
                {
                    IsActive = true,
                    IsDelete = false,
                    BuyerId = buyerId,
                    TravellerId = travlerId,
                    Total = total,
                    ShippingFee = shippingFee,
                    DateCreated = DateTime.UtcNow.AddHours(7),
                    DeliveryDate = DateTime.UtcNow.AddHours(7),
                    DateUpdated = DateTime.UtcNow.AddHours(7),
                    OfferId = 1,
                };

                orderService.CreateOrder(order);
                orderService.SaveOrder();
                /*     Model.Models.Transaction tra = new Model.Models.Transaction()
                     {
                         DateCreated = DateTime.UtcNow.AddHours(7),
                         IsActive = true,
                         IsDelete = false,
                         IsSuccess = 1,
                         OrderId = order.Id,

                     }*/
                return View();

            }
            return View("ResponeTransaction.cshtml");


        }

    }
}

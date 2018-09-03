using APIProject.Model.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace APIProject.ViewModel
{
    public class RequestedViewModel
    {
        public RequestedViewModel() { }
        public RequestedViewModel(List<Offer> list, double price)
        {
            if (list == null || list.Count == 0)
            {
                NumberOffer = 0;
                LastOfferMinute = -1;
                BestPrice = -1;
            }
            else
            {
                NumberOffer = list.Count;
                LastOfferMinute = DateTime.Now.Subtract(list[NumberOffer - 1].DateCreated.GetValueOrDefault(DateTime.Now))
                    .TotalMinutes;
                BestPrice = price + list.OrderBy(o => o.ShippingFee).ToList()[0].ShippingFee;
            }

        }

        #region property
        public int PostId { get; set; }
        public string ProductName { get; set; }
        public int NumberOffer { get; set; }
        public double BestPrice { get; set; }
        public string CreateDate { get; set; }
        public double LastOfferMinute { get; set; }
        public string ImageUrl { get; set; }
        public string BuyFrom { get; set; }
        public string DeliveryTo { get; set; }
        public string TravelerName { get; set; }
        public string TravelerAvartar { get; set; }
        public string TravelerId { get; set; }
        public string DeliveryDate { get; set; }
        public int Deposit { get; set; }
        public int OfferId { get; set; }
        public double Shippingfree { get; set; }

        #endregion
    }

    public class RequestedViewModelrq
    {
        #region property
        public int Type { get; set; }
        public int TripId { get; set; }
        #endregion
    }

    public class RequestedVMReturn
    {
        public RequestedVMReturn()
        {

        }
        public string Status_code { get; set; }
        public string Message { get; set; }
        public List<PostViewModel> ListRequested { get; set; }

    }
}
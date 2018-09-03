using System;
using System.Collections.Generic;

namespace APIProject
{
    public class OfferViewModel
    {
        public int Id { get; set; }
        public string DateCreated { get; set; }
        public string ShippingFee { get; set; }
        public string Total { get; set; }
        public string DeliveryDate { get; set; }
        public int PostId { get; set; }
        public int TripId { get; set; }
        public string TravelerId { get; set; }
        public string TravlerAvartar { get; set; }
        public string TravlerName { get; set; }
        public int TravlerVote { get; set; }
        public int OfferId { get; set; }
        public string Description { get; set; }
        public int Deposit { get; set; }
        public string Message { get; set; }

    }

    public class GetOfferByPostResponse
    {
        public GetOfferByPostResponse()
        {
            Status_code = "200";
            Message = "OK";
            ListItem = new List<GetOfferByPostItem>();
        }
        public string Status_code { get; set; }
        public string Message { get; set; }
        public List<GetOfferByPostItem> ListItem { get; set; }
    }

    public class GetOfferByPostItem
    {
        public GetOfferByPostItem()
        {

        }
        public int OfferId { get; set; }
        public string TravelerId { get; set; }
        public string TravelerName { get; set; }
        public string TravelerImage { get; set; }
        public string CreatedDate { get; set; }
        public string DeliveryDate { get; set; }
        public double Rate { get; set; }
        public string TravelTo { get; set; }
        public double ProductPrice { get; set; }
        public double ShippingFee { get; set; }
        public int Quantity { get; set; }
        public int Type { get; set; }
        public string Message { get; set; }

    }
}

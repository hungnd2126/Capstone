using Newtonsoft.Json;
using System.Collections.Generic;

namespace Redis
{
    public class PostViewModel
    {
        public int Id { get; set; }
        public string ProductName { get; set; }
        public int Price { get; set; }
        public string Quantity { get; set; }
        public Address Buy_Address { get; set; }
        public Address Delivery_Address { get; set; }
        public int ShippingFee { get; set; }
        public string DateCreated { get; set; }
        public string DateUpdated { get; set; }
        public string Description { get; set; }
        public string ImageUrl { get; set; }
        public string Username { get; set; }
        public string UserAvartar { get; set; }
        public int BestPrice { get; set; }
        public long LastOfferMinute { get; set; }
        public int NumberOffer { get; set; }
        public string Nickname { get; set; }
        public string UserId { get; set; }
        public int Deposit { get; set; }
        public string DeliveryDate { get; set; }
        public int OrderId { get; set; }
        public int TripId { get; set; }
        public double Mark { get; set; }
        public string BuyFrom { get; set; }
        public string DeliveryTo { get; set; }
        public string url { get; set; }

        public string domain { get; set; }
        public class Address
        {
            public string Name { get; set; }
            public string City_name { get; set; }
            public string Country_name { get; set; }
            public string Country_code { get; set; }
        }
        public List<OfferViewModel> ListOffer { get; set; }
        public string ToJson()
        {
            return JsonConvert.SerializeObject(this);
        }

        public static PostViewModel FromJson(string json)
        {
            return JsonConvert.DeserializeObject(json, typeof(PostViewModel)) as PostViewModel;
        }
    }
   
}
using APIProject.Model.Models;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using static APIProject.Model.Models.Offer;
using static APIProject.Model.Models.Post;

namespace APIProject.ViewModel
{
    public class PostViewModel
    {
        public PostViewModel() { }
        public PostViewModel(List<Offer> list, int price)
        {
            if (list == null || list.Count == 0)
            {
                NumberOffer = 0;
                LastOfferMinute = -1;
                BestPrice = -1;
            }
            else
            {
                var filteredList = list.Where(m => m.Type == (int)OfferType.TravelerOffer && m.IsActive && !m.IsDelete).ToList();
                if (filteredList != null && filteredList.Count > 0) 
                {
                    var tmp = filteredList.OrderBy(o => o.ShippingFee).ToList()[0];
                    NumberOffer = filteredList.Count;
                    LastOfferMinute = tmp.DateCreated.GetValueOrDefault().Ticks;
                    BestPrice = price + tmp.ShippingFee;
                }
            }

        }

        public int Id { get; set; }
        public string ProductName { get; set; }
        public int Price { get; set; }
        public int Quantity { get; set; }
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
        public int Type { get; set; }
        public string UserId { get; set; }
        public int Deposit { get; set; }
        public string DeliveryDate { get; set; }
        public int OrderId { get; set; }
        public int TripId { get; set; }
        public double Mark { get; set; }
        public string Message { get; set; }
        public string BuyFrom { get; set; }
        public string DeliveryTo { get; set; }
        public string url { get; set; }
        public string domain { get; set; }
        public TrackingOrderViewModel Timeline{ get; set; }
        public string ToJson()
        {
            return JsonConvert.SerializeObject(this);
        }

        public static PostViewModel FromJson(string json)
        {
            return JsonConvert.DeserializeObject(json, typeof(PostViewModel)) as PostViewModel;
        }
    }

    public class Address
    {
        public string Name { get; set; }
        public string City_name { get; set; }
        public string Country_name { get; set; }
        public string Country_code { get; set; }
    }
    public class CreatePostViewModel
    {
        public CreatePostViewModel()
        {
            DateCreated = DateTime.UtcNow.AddHours(7);
            Type = PostType.Requested;
        }

        #region property
        public int Id { get; set; }
        public string ProductName { get; set; }
        public int Price { get; set; }
        public int Quantity { get; set; }
        public string Buy_Address { get; set; }
        public string Buy_City { get; set; }
        public string Buy_Country { get; set; }
        public string Delivery_Address { get; set; }
        public string Delivery_City { get; set; }
        public string Delivery_Country { get; set; }
        public int ShippingFee { get; set; }
        public DateTime? DateCreated { get; set; }
        public DateTime? DateUpdated { get; set; }
        public string Description { get; set; }
        public string Image { get; set; }
        public string ReceivedDate { get; set; }
        public int Deposit { get; set; }
        public PostType Type { get; set; }
        public string UserId { get; set; }
        #endregion
    }
 
    public class CreatePostResponse
    {
        public CreatePostResponse()
        {
            Status_code = "E00";
            Message = "OK";
        }

        public string Status_code { get; set; }
        public string Message { get; set; }
        public double Current_Money { get; set; }
        
    }

}
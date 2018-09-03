using Newtonsoft.Json;
using System;
using System.Collections.Generic;

namespace APIProject.ViewModel
{
    public class TripViewModel
    {
        public int TripId { get; set; }
        public string Name { get; set; }
        public string FromCity { get; set; }
        public string ToCity { get; set; }
        public string FromDate { get; set; }
        public string ToDate { get; set; }
        public string DateCreated { get; set; }
        public string DateUpdated { get; set; }
        public string TravelerName { get; set; }
        public string TravelerAvartar { get; set; }
        public string TravelerId { get; set; }
        public int FromCityId { get; set; }
        public int ToCityId { get; set; }
        public double creditUser { get; set; }

        public string ToJson()
        {
            return JsonConvert.SerializeObject(this);
        }

        public TripViewModel FromJson(string json)
        {

            return JsonConvert.DeserializeObject(json, typeof(TripViewModel)) as TripViewModel;
        }

    }
    public class CreateTripViewModel
    {
        public CreateTripViewModel()
        {
            DateCreated = DateTime.Now;
        }
        public string Name { get; set; }
        public string FromCityGeonameId { get; set; }
        public string ToCityGeonameId { get; set; }
        public string FromDate { get; set; }
        public string ToDate { get; set; }
        public DateTime? DateCreated { get; set; }
        public DateTime? DateUpdated { get; set; }
        public string UserId { get; set; }
    }

    public class CreateTripViewModelResponse
    {
        public CreateTripViewModelResponse()
        {
            DateCreated = DateTime.Now.ToShortDateString();
        }

        public string Status_code { get; set; }
        public string Message { get; set; }
        public int TripId { get; set; }
        public string DateCreated { get; set; }
        public int NumberOrder { get; set; }
        public double Earning { get; set; }
    }

    public class GetAllTripByUserResponse
    {
        public GetAllTripByUserResponse()
        {

        }

        public string Status_code { get; set; }
        public string Message { get; set; }
        public List<GetAllTripByUserItem> listItem { get; set; }
    }

    public class GetAllTripByUserItem {
        public GetAllTripByUserItem()
        {

        }

        public int TripId { get; set; }
        public string Name { get; set; }
        public string TravelDate { get; set; }
        public string CreatedDate { get; set; }
        public int NumberOrder { get; set; }
        public double Earning { get; set; }
    }
}
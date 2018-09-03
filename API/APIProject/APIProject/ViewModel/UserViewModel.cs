using Newtonsoft.Json;
using System;

namespace APIProject.ViewModel
{
    public class UserViewModel
    {
        public UserViewModel()
        {
        }

    }

    public class UpdateInfoViewModel
    {
        public String Message { get; set; }
        public String Status_code { get; set; }
        public String Image { get; set; }
        public String Firstname { get; set; }
        public String Lastname { get; set; }
        public String Bio { get; set; }
        public String Phone { get; set; }
        public String Address { get; set; }
        public String GeoId { get; set; }
        public String Tknl { get; set; }
    }
    public class DialogViewModel
    {
        public String Username { get; set; }
        public String UserId { get; set; }

        public String NickName { get; set; }
        public String UserAvartar { get; set; }
    }
    public class WalletViewModel
    {
        public double Value { get; set; }
    }

    public class UserProfileViewModel
    {
        public string Id { get; set; }
        public string Name { get; set; }
        public string ImageUrl { get; set; }
        public string Bio { get; set; }
        public string DateCreated { get; set; }
        public string BuyerScore { get; set; }
        public string BuyerCount { get; set; }
        public string TravelerScore { get; set; }
        public string TravelerCount { get; set; }
    }
    public class RatingViewModel
    {
        public string Name { get; set; }
        public string ImageUrl { get; set; }
        public string Message { get; set; }
        public string DateCreated { get; set; }
        public string ProductName { get; set; }
        public int Score { get; set; }
    }
}
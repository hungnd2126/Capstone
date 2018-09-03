using System.ComponentModel;
using Microsoft.AspNet.Identity.EntityFramework;
using System;
using System.Security.Claims;
using System.Threading.Tasks;
using Microsoft.AspNet.Identity;

namespace APIProject.Model.Models
{
    public class User : IdentityUser
    {
        public User()
        {
            DateCreated = DateTime.Now;
            Current_Money = 0;
            TravelerScore = 3;
            BuyerScore = 3;

        }

        public async Task<ClaimsIdentity> GenerateUserIdentityAsync(UserManager<User> manager)
        {
            // Note the authenticationType must match the one defined in CookieAuthenticationOptions.AuthenticationType
            var userIdentity = await manager.CreateIdentityAsync(this, DefaultAuthenticationTypes.ApplicationCookie);
            // Add custom user claims here
            return userIdentity;
        }

        public string FirstName { get; set; }

        public string LastName { get; set; }

        public string Address { get; set; }

        public DateTime DateCreated { get; set; }

        public DateTime? LastLoginTime { get; set; }

        public bool Activated { get; set; }

        public Gender Gender { get; set; }

        public SystemRoles RoleId { get; set; }

        public DateTime? DateOfBirth { get; set; }
        public string ImageUrl { get; set; }
        public string FacebookId { get; set; }
        public string GoogleId { get; set; }
        public bool Deleted { get; set; }
        public double Current_Money { get; set; }
        public double BuyerScore { get; set; }
        public double TravelerScore { get; set; }
        public string SignalConnect { get; set; }
        public string TKNL { get; set; }
        public string DisplayName
        {
            get { return LastName; }
        }
    }
    public enum SystemRoles
    {
        [Description("Admin")]
        Role01 = 0,
        [Description("Member")]
        Role02 = 1
    }
    public enum Gender
    {
        [Description("Nam")]
        Male = 0,
        [Description("Nữ")]
        Female = 1
    }
}

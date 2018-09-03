using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.ComponentModel.DataAnnotations.Schema;

namespace APIProject.Model.Models
{
    public class Post : BaseIdEntity
    {
        public Post()
        {
            DateCreated = DateTime.UtcNow.AddHours(7);
            IsActive = true;
            IsDelete = false;
        }

        #region property
        public string ProductName { get; set; }
        public int Price { get; set; }
        public int Quantity { get; set; }
        public string BuyFromAddress { get; set; }
        public string DeliveryToAddress { get; set; }
        public int? BuyFromId { get; set; }
        public int DeliveryToId { get; set; }
        public int ShippingFee { get; set; }
        public DateTime? DateCreated { get; set; }
        public DateTime? DateUpdated { get; set; }
        public string Description { get; set; }
        public string LinkWebsite { get; set; }
        public PostType Type { get; set; }
        public string UserId { get; set; }
        public int Deposit { get; set; }
        public DateTime? DeliveryDate { get; set; }
        public int? CountryId { get; set; }
        #endregion

        [ForeignKey("UserId")]
        public virtual User User { get; set; }
        [ForeignKey("BuyFromId")]
        public virtual City BuyFrom { get; set; }
        [ForeignKey("DeliveryToId")]
        public virtual City DeliveryTo { get; set; }
        [ForeignKey("CountryId")]
        public virtual Country Country { get; set; }

        #region ListObject
        public virtual ICollection<Image> ListImage { get; set; }
        public virtual ICollection<Offer> ListOffer { get; set; }


        #endregion

        public enum PostType
        {
            [Description("Đã đăng")]
            Requested = 0,
            [Description("Đang xử lý")]
            Transit = 1,
            [Description("Hoàn thành")]
            Completed = 2,
            [Description("Hết hạn")]
            Expired = 3,
        }
    }
}

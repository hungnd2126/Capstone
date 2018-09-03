using System;
using System.ComponentModel.DataAnnotations.Schema;
using System.Collections.Generic;
using System.ComponentModel;

namespace APIProject.Model.Models
{
    public class Order : BaseIdEntity
    {
        public Order()
        {
            DateCreated = DateTime.Now;
            IsActive = true;
        }
        #region property
        public DateTime? DateCreated { get; set; }
        public DateTime? DateUpdated { get; set; }
        public int ShippingFee { get; set; }
        public DateTime? DeliveryDate { get; set; }
        public int Total { get; set; }
        public String BuyerId { get; set; }
        public String TravellerId { get; set; }
        public int OfferId { get; set; }
        public int TripId { get; set; }
        public int Status { get; set; }

        #endregion

        #region FK
        [ForeignKey("BuyerId")]
        public virtual User Buyer { get; set; }
        [ForeignKey("TravellerId")]
        public virtual User Traveller { get; set; }
        [ForeignKey("OfferId")]
        public virtual Offer Offer { get; set; }

        #endregion
        #region ListObject
        public virtual ICollection<Rating> ListRating { get; set; }
        public virtual ICollection<TrackingOrder> ListTrackingOrder { get; set; }


        #endregion
        public enum OrderType
        {
            [Description("Đang xử lý")]
            Transit = 1,
            [Description("Hoàn thành")]
            Completed = 2,
            [Description("Chờ admin xác nhận")]
            WaitAdmin = 3,
            [Description("Hủy")]
            Cancel = 4
        }
    }
}

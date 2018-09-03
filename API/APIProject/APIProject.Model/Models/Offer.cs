using System;
using System.ComponentModel.DataAnnotations.Schema;
using System.Collections.Generic;
using System.ComponentModel;

namespace APIProject.Model.Models
{
    public class Offer : BaseIdEntity
    {
        public Offer()
        {
            IsActive = true;
        }
        #region property
        public DateTime? DateCreated { get; set; }
        public DateTime? DateUpdated { get; set; }
        public int ShippingFee { get; set; }
        public DateTime? DeliveryDate { get; set; }
		public String BuyerId { get; set; }
		public String TravellerId { get; set; }
		public int PostId { get; set; }
		public int TripId { get; set; }
        public String Message { get; set; }
        public int Type { get; set; }
        #endregion

        #region FK
		[ForeignKey("BuyerId")]
        public virtual User Buyer { get; set; }
		[ForeignKey("TravellerId")]
		public virtual User Traveller { get; set; }
		[ForeignKey("PostId")]
        public virtual Post Post { get; set; }
		[ForeignKey("TripId")]
        public virtual Trip Trip { get; set; }
        #endregion

        public enum OfferType
        {
            [Description("Traveler make offer")]
            TravelerOffer = 1,
            [Description("Buyer make offer")]
            BuyerOffer = 2,
           
        }
    }
}

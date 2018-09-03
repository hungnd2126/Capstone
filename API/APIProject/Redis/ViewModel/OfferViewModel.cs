using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Redis
{
    public class OfferViewModel
    {
        public int Id { get; set; }
        public Nullable<System.DateTime> DateCreated { get; set; }
        public Nullable<System.DateTime> DateUpdated { get; set; }
        public double ShippingFee { get; set; }
        public Nullable<System.DateTime> DeliveryDate { get; set; }
        public string BuyerId { get; set; }
        public string TravellerId { get; set; }
        public int PostId { get; set; }
        public int TripId { get; set; }
        public Nullable <System.Int64> OrderId { get; set; }

    }
}

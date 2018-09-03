using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace APIProject.Model.Models
{
   public class TrackingOrder : BaseIdEntity
    {
        public TrackingOrder()
        {
            DateCreated = DateTime.Now;
            IsActive = true;
        
        }
        #region property

        public int Status { get; set; }
        public DateTime? DateCreated { get; set; }
        public int OrderId { get; set; }
        public string Description { get; set; }
        public double Longitude { get; set; }
        public double Latitude { get; set; }

        #endregion

        public enum TrackingOrderType
        {
            [Description("Đã khởi hành")]
            Started = 1,
            [Description("Đã đến nơi")]
            Arrived = 2,
            [Description("Đã mua được hàng")]
            Buyed = 3,
            [Description("Đã về")]
            CameBack = 4,
            [Description("Có thể giao hàng")]
            CanShipping = 5,
            [Description("Giao hàng thành công")]
            Finished = 6,
            [Description("Không thể mua hàng")]
            CanNotBuy = 7, 
            [Description("Đơn hàng đã bị hủy")]
            Cancel = 8,
        }
        [ForeignKey("OrderId")]
        public virtual Order Order { get; set; }

    }
}

using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Redis.Enum
{

    public enum PostType
    {
        [Description("Đã đăng")]
        Requested = 0,
        [Description("Đang xử lý")]
        Transit = 1,
        [Description("Hoàn thành")]
        Completed = 2,
        [Description("Hết hạn")]
        Expired = 3
    }
    public enum OrderType
    {

        [Description("Đang xử lý")]
        Transit = 1,
        [Description("Hoàn thành")]
        Completed = 2
    }
    public enum RateType
    {

        BuyerRate = 1,
        TravlerRate = 2
    }
    /* public enum HighestOrder
     {

         [Description("Đang xử lý")]
         Transit = 1,
         [Description("Hoàn thành")]
         Completed = 2
     }*/

}

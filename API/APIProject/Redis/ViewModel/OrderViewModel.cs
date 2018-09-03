using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Redis.ViewModel
{
    class OrderViewModel
    {

    }
   public class SumOrderViewModel
    {
        public int TotalMonney { get; set; }
        public long TotalOrder { get; set; }
        public string ToJson()
        {
            return JsonConvert.SerializeObject(this);
        }

        public static PostViewModel FromJson(string json)
        {
            return JsonConvert.DeserializeObject(json, typeof(PostViewModel)) as PostViewModel;
        }
    }
  
}

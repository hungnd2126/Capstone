using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace APIProject.ViewModel
{
    public class TrackingOrderViewModel
    {
        public int Status { get; set; }
        public string DateCreated { get; set; }
        public int OrderId { get; set; }
        public string Description { get; set; }
        public double Longitude { get; set; }
        public double Latitude { get; set; }
    }
}
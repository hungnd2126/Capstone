using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace APIProject.ViewModel
{
    public class CrawlViewModel
    {
        public String url { get; set; }
        public double productPrice { get; set; }
        public String imageURL { get; set; }
        public String productName { get; set; }
        public String country { get; set; }
        public String domain { get; set; }

        
    }
}
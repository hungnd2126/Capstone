using System;
using System.Collections;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;

namespace APIProject.Model.Models
{
    public class City : BaseIdEntity
    {
        public City() {

        }

        public string Name { get; set; }
        public string Code { get; set; }
        public int CountryID { get; set; }
        
        [ForeignKey("CountryID")]
        public virtual Country Country { get; set; }
        public virtual ICollection<Post> ListPostBuyFrom { get; set; }
        public virtual ICollection<Post> ListPostDeliveryTo { get; set; }
        public virtual ICollection<Trip> ListTripFromCity { get; set; }
        public virtual ICollection<Trip> ListTripToCity { get; set; }

    }
}

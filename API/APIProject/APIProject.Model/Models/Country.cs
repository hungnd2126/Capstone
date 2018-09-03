using System;
using System.Collections.Generic;

namespace APIProject.Model.Models
{
    public class Country : BaseIdEntity
    {
        public Country()
        {
            IsActive = true;
            IsDelete = false;
        }
        public string Name { get; set; }
        public string Code { get; set; }
        //public virtual ICollection<City> ListCity { get; set; }
    }
}

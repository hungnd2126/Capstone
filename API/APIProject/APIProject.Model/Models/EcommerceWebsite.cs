using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace APIProject.Model.Models
{
    public class EcommerceWebsite : BaseIdEntity
    {
        public int CountryId { get; set; }
        public string Url { get; set; }
        [ForeignKey("CountryId")]
        public virtual Country Country { get; set; }
    }
}

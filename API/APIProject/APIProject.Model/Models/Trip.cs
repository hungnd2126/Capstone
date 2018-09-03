using System;
using System.ComponentModel.DataAnnotations.Schema;

namespace APIProject.Model.Models
{
    public class Trip : BaseIdEntity
    {
        public Trip()
        {
            DateCreated = DateTime.UtcNow.AddHours(7);
            IsActive = true;
        }
        public string Name { get; set; }
        public int FromCityId { get; set; }
        public int ToCityId { get; set; }
        public DateTime? FromDate { get; set; }
        public DateTime? ToDate { get; set; }
        public DateTime? DateCreated { get; set; }
        public DateTime? DateUpdated { get; set; }
        public string UserId { get; set; }
        public virtual User User { get; set; }
        [ForeignKey("FromCityId")]
        public virtual City FromCity { get; set; }
        [ForeignKey("ToCityId")]
        public virtual City ToCity { get; set; }
       
    }
}

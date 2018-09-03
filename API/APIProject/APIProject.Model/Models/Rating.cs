using System;
using System.ComponentModel.DataAnnotations.Schema;
using System.ComponentModel;

namespace APIProject.Model.Models
{
    public class Rating : BaseIdEntity
    {
        public Rating()
        {
            DateCreated = DateTime.Now;
            IsActive = true;
        }
        #region property
        public DateTime? DateCreated { get; set; }
		public String UserRateId { get; set; }
		public String UserRatedId { get; set; }
		public string Comment { get; set; }
        public int Point { get; set; }
		public int OrderId { get; set; }
        public int Type { get; set; }

        #endregion

        #region FK
        [ForeignKey("UserRateId")]
        public virtual User UserRate { get; set; }
		[ForeignKey("UserRatedId")]
		public virtual User UserRated { get; set; }
		[ForeignKey("OrderId")]
        public virtual Order Order { get; set; }
        #endregion


        public enum RateRole
        {
            [Description("Vai trò Buyer")]
            Buyer = 0,
            [Description("Vai trò Traveller")]
            Traveller = 1
        }
    }
}

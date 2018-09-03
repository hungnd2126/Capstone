using System;
using System.ComponentModel.DataAnnotations.Schema;
using System.Collections.Generic;
using System.ComponentModel;

namespace APIProject.Model.Models
{
    public class Transaction : BaseIdEntity
    {
        public Transaction()
        {
            IsActive = true;
        }
        #region property
        public DateTime? DateCreated { get; set; }
        public int Value { get; set; }
		public String UserId { get; set; }
        public TypeTrans Type { get; set; }
		public bool IsSuccess { get; set; }
        public string Description { get; set; }
        public int? OrderId { get; set; }
        public int? PostId { get; set; }
        #endregion

        #region FK
		[ForeignKey("UserId")]
        public virtual User User { get; set; }
		[ForeignKey("OrderId")]
        public virtual Order Order { get; set; }
        [ForeignKey("PostId")]
        public virtual Post Post { get; set; }

        #endregion
        public enum TypeTrans
        {
            [Description("Chuyển vào hệ thống")]
            CashIn = 0,
            [Description("Hệ thống chuyển cho Traveler")]
            CashOutToTraveler = 1,
            [Description("Nạp tiền vào post")]
            PostIn = 2,
            [Description("Rút tiền từ post")]
            PostOut = 3,
            [Description("User rút tiền")]
            CashOutToUser = 4,
        }
    }
}

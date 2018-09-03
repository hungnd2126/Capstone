using System;
using System.ComponentModel.DataAnnotations.Schema;

namespace APIProject.Model.Models
{
    public class Notification : BaseIdEntity
    {
        public Notification()
        {
            DateCreated = DateTime.Now;
            IsActive = true;
            IsDelete = false;
        }
        #region property
        public string Message { get; set; }
        public DateTime? DateCreated { get; set; }
        public string Title { get; set; }
        public bool IsRead { get; set; }
        public string UserId { get; set; }
        #endregion
        [ForeignKey("UserId")]
        public virtual User User { get; set; }

    }
}

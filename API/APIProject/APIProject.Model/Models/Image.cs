using System;
using System.ComponentModel.DataAnnotations.Schema;

namespace APIProject.Model.Models
{
    public class Image : BaseIdEntity
    {
        public Image()
        {
            IsActive = true;
            DateCreated = DateTime.Now;
        }
        public string Path { get; set; }
        public DateTime? DateCreated { get; set; }
        public int PostId { get; set; }
        [ForeignKey("PostId")]
        public virtual Post Post { get; set; }
    }
}

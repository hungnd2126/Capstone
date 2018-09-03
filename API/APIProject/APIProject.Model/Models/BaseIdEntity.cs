using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace APIProject.Model.Models
{
    public abstract partial class BaseIdEntity
    {
        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public int Id { get; set; }
        public Boolean IsActive { get; set; }
        public Boolean IsDelete { get; set; }
    }
}

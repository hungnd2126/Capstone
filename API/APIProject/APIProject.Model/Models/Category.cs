using System;

namespace APIProject.Model.Models
{
    public class Category
    {
        public int ID { get; set; }
        public string Name { get; set; }
        public DateTime? DateCreated { get; set; }
        public DateTime? DateUpdated { get; set; }
        public Category()
        {
            DateCreated = DateTime.Now;
        }
    }
}

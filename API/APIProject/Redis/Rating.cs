//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated from a template.
//
//     Manual changes to this file may cause unexpected behavior in your application.
//     Manual changes to this file will be overwritten if the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace Redis
{
    using System;
    using System.Collections.Generic;
    
    public partial class Rating
    {
        public int Id { get; set; }
        public Nullable<System.DateTime> DateCreated { get; set; }
        public string UserRateId { get; set; }
        public string UserRatedId { get; set; }
        public string Comment { get; set; }
        public int OrderId { get; set; }
        public bool IsActive { get; set; }
        public bool IsDelete { get; set; }
        public int Point { get; set; }
        public int Type { get; set; }
    
        public virtual AspNetUser AspNetUser { get; set; }
        public virtual AspNetUser AspNetUser1 { get; set; }
        public virtual Order Order { get; set; }
    }
}

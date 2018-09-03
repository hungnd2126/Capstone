using APIProject.Model.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace APIProject.ViewModel
{
    public class TransactionViewModel
    {
        public TransactionViewModel()
        {
        }

        #region property
        public string Name { get; set; }
        public string Value { get; set; }
        public string CreateDate { get; set; }
        public string IsSuccess { get; set; }
        public string Dau { get; set; }
        #endregion
    }

}
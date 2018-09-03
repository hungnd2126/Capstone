using APIProject.Data.Infrastructure;
using APIProject.Data.Repositories;
using APIProject.Model.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace APIProject.Service
{
    public interface IEcommerceWebsiteService
    {
        IEnumerable<EcommerceWebsite> GetEcommerceWebsites();
        EcommerceWebsite GetEcommerceWebsite(int id);
        void CreateEcommerceWebsite(EcommerceWebsite ecommerceWebsite);
        void SaveEcommerceWebsite();
    }

    public class EcommerceWebsiteService : IEcommerceWebsiteService
    {
        private readonly IEcommerceWebsiteRepository ecommerceWebsiteRepository;
        private readonly IUnitOfWork unitOfWork;

        public EcommerceWebsiteService(IEcommerceWebsiteRepository ecommerceWebsiteRepository, IUnitOfWork unitOfWork)
        {
            this.ecommerceWebsiteRepository = ecommerceWebsiteRepository;
            this.unitOfWork = unitOfWork;
        }

        #region ICountryService Members

        public IEnumerable<EcommerceWebsite> GetEcommerceWebsites()
        {
            return ecommerceWebsiteRepository.GetAll();
        }

        public EcommerceWebsite GetEcommerceWebsite(int id)
        {
            var Country = ecommerceWebsiteRepository.GetById(id);
            return Country;
        }

        public void CreateEcommerceWebsite(EcommerceWebsite ecommerceWebsite)
        {
            ecommerceWebsiteRepository.Add(ecommerceWebsite);
        }

        public void SaveEcommerceWebsite()
        {
            unitOfWork.Commit();
        }

       

        #endregion
    }
}

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
    public interface ICountryService
    {
        IEnumerable<Country> GetCountries();
        Country GetCountry(int id);
        Country GetCountry(string name);
        void CreateCountry(Country Country);
        void SaveCountry();
    }

    public class CountryService : ICountryService
    {
        private readonly ICountryRepository CountryRepository;
        private readonly IUnitOfWork unitOfWork;

        public CountryService(ICountryRepository CountryRepository, IUnitOfWork unitOfWork)
        {
            this.CountryRepository = CountryRepository;
            this.unitOfWork = unitOfWork;
        }

        #region ICountryService Members

        public IEnumerable<Country> GetCountries()
        {
            return CountryRepository.GetAll();
        }

        public Country GetCountry(int id)
        {
            var Country = CountryRepository.GetById(id);
            return Country;
        }

        public Country GetCountry(string name)
        {
            var Country = CountryRepository.GetAll().Where(c => c.Name.Equals(name)).FirstOrDefault();
            return Country;
        }

        public void CreateCountry(Country Country)
        {
            CountryRepository.Add(Country);
        }

        public void SaveCountry()
        {
            unitOfWork.Commit();
        }

        #endregion
    }
}

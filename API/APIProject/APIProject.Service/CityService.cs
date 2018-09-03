using APIProject.Data.Infrastructure;
using APIProject.Data.Repositories;
using APIProject.Model.Models;
using System.Collections.Generic;
using System.Linq;

namespace APIProject.Service
{
    public interface ICityService
    {
        IEnumerable<City> GetCities();
        City GetCity(int id);
        City GetCity(string name);
        void CreateCity(City City);
        void SaveCity();
    }

    public class CityService : ICityService
    {
        private readonly ICityRepository CityRepository;
        private readonly IUnitOfWork unitOfWork;

        public CityService(ICityRepository CityRepository, IUnitOfWork unitOfWork)
        {
            this.CityRepository = CityRepository;
            this.unitOfWork = unitOfWork;
        }

        #region ICityService Members

        public IEnumerable<City> GetCities()
        {
            return CityRepository.GetAll();
        }

        public City GetCity(int id)
        {
            var City = CityRepository.GetById(id);
            return City;
        }

        public City GetCity(string name)
        {
            var City = CityRepository.GetAll().Where(c => c.Name == name).FirstOrDefault();
            return City;
        }

        public void CreateCity(City City)
        {
            CityRepository.Add(City);
        }

        public void SaveCity()
        {
            unitOfWork.Commit();
        }

        #endregion
    }
}

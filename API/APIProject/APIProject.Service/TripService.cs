using APIProject.Data.Infrastructure;
using APIProject.Data.Repositories;
using APIProject.Model.Models;
using System.Collections.Generic;

namespace APIProject.Service
{
    public interface ITripService
    {
        IEnumerable<Trip> GetTrips();
        Trip GetTrip(int id);
        void UpdateTrip(Trip Trip);
        void CreateTrip(Trip Trip);
        void SaveTrip();
    }

    public class TripService : ITripService
    {
        private readonly ITripRepository TripRepository;
        private readonly IUnitOfWork unitOfWork;

        public TripService(ITripRepository TripRepository, IUnitOfWork unitOfWork)
        {
            this.TripRepository = TripRepository;
            this.unitOfWork = unitOfWork;
        }

        #region ITripService Members

        public IEnumerable<Trip> GetTrips()
        {
            return TripRepository.GetAll();
        }

        public Trip GetTrip(int id)
        {
            var Trip = TripRepository.GetById(id);
            return Trip;
        }

        public void CreateTrip(Trip Trip)
        {
            TripRepository.Add(Trip);
        }

        public void UpdateTrip(Trip Trip)
        {
            TripRepository.Update(Trip);
        }
        public void SaveTrip()
        {
            unitOfWork.Commit();
        }

        #endregion
    }
}

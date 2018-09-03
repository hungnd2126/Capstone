using APIProject.Data.Infrastructure;
using APIProject.Data.Repositories;
using APIProject.Model.Models;
using System.Collections.Generic;

namespace APIProject.Service
{
    public interface IOfferService
    {
        IEnumerable<Offer> GetOffers();
        Offer GetOffer(int id);
        void UpdateOffer(Offer Offer);
        void CreateOffer(Offer Offer);
        void SaveOffer();
    }

    public class OfferService : IOfferService
    {
        private readonly IOfferRepository OfferRepository;
        private readonly IUnitOfWork unitOfWork;

        public OfferService(IOfferRepository OfferRepository, IUnitOfWork unitOfWork)
        {
            this.OfferRepository = OfferRepository;
            this.unitOfWork = unitOfWork;
        }

        #region IOfferService Members

        public IEnumerable<Offer> GetOffers()
        {
            return OfferRepository.GetAll();
        }

        public Offer GetOffer(int id)
        {
            var Offer = OfferRepository.GetById(id);
            return Offer;
        }

        public void CreateOffer(Offer Offer)
        {
            OfferRepository.Add(Offer);
        }

        public void UpdateOffer(Offer Offer)
        {
            OfferRepository.Update(Offer);
        }
        public void SaveOffer()
        {
            unitOfWork.Commit();
        }

        #endregion
    }
}

using APIProject.Data.Infrastructure;
using APIProject.Data.Repositories;
using APIProject.Model.Models;
using System.Collections.Generic;

namespace APIProject.Service
{
    public interface IRatingService
    {
        IEnumerable<Rating> GetRatings();
        Rating GetRating(int id);
        void UpdateRating(Rating Rating);
        void CreateRating(Rating Rating);
        void SaveRating();
    }

    public class RatingService : IRatingService
    {
        private readonly IRatingRepository RatingRepository;
        private readonly IUnitOfWork unitOfWork;

        public RatingService(IRatingRepository RatingRepository, IUnitOfWork unitOfWork)
        {
            this.RatingRepository = RatingRepository;
            this.unitOfWork = unitOfWork;
        }

        #region IRatingService Members

        public IEnumerable<Rating> GetRatings()
        {
            return RatingRepository.GetAll();
        }

        public Rating GetRating(int id)
        {
            var Rating = RatingRepository.GetById(id);
            return Rating;
        }

        public void CreateRating(Rating Rating)
        {
            RatingRepository.Add(Rating);
        }

        public void UpdateRating(Rating Rating)
        {
            RatingRepository.Update(Rating);
        }
        public void SaveRating()
        {
            unitOfWork.Commit();
        }

        #endregion
    }
}

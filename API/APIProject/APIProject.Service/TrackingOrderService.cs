using APIProject.Data.Infrastructure;
using APIProject.Data.Repositories;
using APIProject.Model.Models;
using System.Collections.Generic;

namespace APIProject.Service
{
    public interface ITrackingOrderService
    {
        IEnumerable<TrackingOrder> GetTrackingOrders();
        TrackingOrder GetTrackingOrder(int id);
        void UpdateTrackingOrder(TrackingOrder trackingOrder);
        void CreateTrackingOrder(TrackingOrder trackingOrder);
        void SaveTrackingOrder();
    }

    public class TrackingOrderService : ITrackingOrderService
    {
        private readonly ITrackingOrderRepository trackingOrderRepository;
        private readonly IUnitOfWork unitOfWork;

        public TrackingOrderService(ITrackingOrderRepository trackingOrderRepository, IUnitOfWork unitOfWork)
        {
            this.trackingOrderRepository = trackingOrderRepository;
            this.unitOfWork = unitOfWork;
        }

        #region IRatingService Members

        public IEnumerable<TrackingOrder> GetTrackingOrders()
        {
            return trackingOrderRepository.GetAll();
        }

        public TrackingOrder GetTrackingOrder(int id)
        {
            var trackingOrder = trackingOrderRepository.GetById(id);
            return trackingOrder;
        }

        public void UpdateTrackingOrder(TrackingOrder trackingOrder)
        {
            trackingOrderRepository.Update(trackingOrder);
        }

        public void CreateTrackingOrder(TrackingOrder trackingOrder)
        {
            trackingOrderRepository.Add(trackingOrder);
        }

        public void SaveTrackingOrder()
        {
            unitOfWork.Commit();
        }

     

        #endregion
    }
}

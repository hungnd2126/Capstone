using APIProject.Data.Infrastructure;
using APIProject.Data.Repositories;
using APIProject.Model.Models;
using System.Collections.Generic;

namespace APIProject.Service
{
    public interface IOrderService
    {
        IEnumerable<Order> GetOrders();
        Order GetOrder(int id);
        void UpdateOrder(Order Order);
        void CreateOrder(Order Order);
        void SaveOrder();
    }

    public class OrderService : IOrderService
    {
        private readonly IOrderRepository OrderRepository;
        private readonly IUnitOfWork unitOfWork;

        public OrderService(IOrderRepository OrderRepository, IUnitOfWork unitOfWork)
        {
            this.OrderRepository = OrderRepository;
            this.unitOfWork = unitOfWork;
        }

        #region IOrderService Members

        public IEnumerable<Order> GetOrders()
        {
            return OrderRepository.GetAll();
        }

        public Order GetOrder(int id)
        {
            var Order = OrderRepository.GetById(id);
            return Order;
        }

        public void CreateOrder(Order Order)
        {
            OrderRepository.Add(Order);
        }

        public void UpdateOrder(Order Order)
        {
            OrderRepository.Update(Order);
        }
        public void SaveOrder()
        {
            unitOfWork.Commit();
        }

        #endregion
    }
}

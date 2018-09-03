using APIProject.Data.Infrastructure;
using APIProject.Data.Repositories;
using APIProject.Model.Models;
using System.Collections.Generic;

namespace APIProject.Service
{
    public interface INotificationService
    {
        IEnumerable<Notification> GetNotifications();
        Notification GetNotification(int id);
        void UpdateNotification(Notification Notification);
        void CreateNotification(Notification Notification);
        void SaveNotification();
    }

    public class NotificationService : INotificationService
    {
        private readonly INotificationRepository NotificationRepository;
        private readonly IUnitOfWork unitOfWork;

        public NotificationService(INotificationRepository NotificationRepository, IUnitOfWork unitOfWork)
        {
            this.NotificationRepository = NotificationRepository;
            this.unitOfWork = unitOfWork;
        }

        #region INotificationService Members

        public IEnumerable<Notification> GetNotifications()
        {
            return NotificationRepository.GetAll();
        }

        public Notification GetNotification(int id)
        {
            var Notification = NotificationRepository.GetById(id);
            return Notification;
        }

        public void CreateNotification(Notification Notification)
        {
            NotificationRepository.Add(Notification);
        }

        public void UpdateNotification(Notification Notification)
        {
            NotificationRepository.Update(Notification);
        }
        public void SaveNotification()
        {
            unitOfWork.Commit();
        }

        #endregion
    }
}

using APIProject.Data.Infrastructure;
using APIProject.Model.Models;

namespace APIProject.Data.Repositories
{
    public class NotificationRepository : RepositoryBase<Notification>, INotificationRepository
    {
        public NotificationRepository(IDbFactory dbFactory)
            : base(dbFactory) { }
    }

    public interface INotificationRepository : IRepository<Notification>
    {
    }
}

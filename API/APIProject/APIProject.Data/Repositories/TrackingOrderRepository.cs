using APIProject.Data.Infrastructure;
using APIProject.Model.Models;

namespace APIProject.Data.Repositories
{
    public class TrackingOrderRepository : RepositoryBase<TrackingOrder>, ITrackingOrderRepository
    {
        public TrackingOrderRepository(IDbFactory dbFactory)
            : base(dbFactory) { }
    }

    public interface ITrackingOrderRepository : IRepository<TrackingOrder>
    {
    }
}

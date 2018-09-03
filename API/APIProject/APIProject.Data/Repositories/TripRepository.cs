using APIProject.Data.Infrastructure;
using APIProject.Model.Models;

namespace APIProject.Data.Repositories
{
    public class TripRepository : RepositoryBase<Trip>, ITripRepository
    {
        public TripRepository(IDbFactory dbFactory)
            : base(dbFactory) { }
    }

    public interface ITripRepository : IRepository<Trip>
    {
    }
}

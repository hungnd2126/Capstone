using APIProject.Data.Infrastructure;
using APIProject.Model.Models;

namespace APIProject.Data.Repositories
{
    public class RatingRepository : RepositoryBase<Rating>, IRatingRepository
    {
        public RatingRepository(IDbFactory dbFactory)
            : base(dbFactory) { }
    }

    public interface IRatingRepository : IRepository<Rating>
    {
    }
}

using APIProject.Data.Infrastructure;
using APIProject.Model.Models;

namespace APIProject.Data.Repositories
{
    public class CityRepository : RepositoryBase<City>, ICityRepository
    {
        public CityRepository(IDbFactory dbFactory)
            : base(dbFactory) { }
    }

    public interface ICityRepository : IRepository<City>
    {
    }
}

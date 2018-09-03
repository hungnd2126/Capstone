using APIProject.Data.Infrastructure;
using APIProject.Model.Models;

namespace APIProject.Data.Repositories
{
    public class EcommerceWebsiteRepository : RepositoryBase<EcommerceWebsite>, IEcommerceWebsiteRepository
    {
        public EcommerceWebsiteRepository(IDbFactory dbFactory)
            : base(dbFactory) { }
    }

    public interface IEcommerceWebsiteRepository : IRepository<EcommerceWebsite>
    {
    }
}

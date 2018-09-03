using APIProject.Data.Infrastructure;
using APIProject.Model.Models;

namespace APIProject.Data.Repositories
{
    public class ImageRepository : RepositoryBase<Image>, IImageRepository
    {
        public ImageRepository(IDbFactory dbFactory)
            : base(dbFactory) { }
    }

    public interface IImageRepository : IRepository<Image>
    {
    }
}

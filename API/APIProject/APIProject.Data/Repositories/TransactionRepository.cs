using APIProject.Data.Infrastructure;
using APIProject.Model.Models;

namespace APIProject.Data.Repositories
{
    public class TransactionRepository : RepositoryBase<Transaction>, ITransactionRepository
    {
        public TransactionRepository(IDbFactory dbFactory)
            : base(dbFactory) { }
    }

    public interface ITransactionRepository : IRepository<Transaction>
    {
    }
}

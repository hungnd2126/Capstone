using APIProject.Data.Infrastructure;
using APIProject.Data.Repositories;
using APIProject.Model.Models;
using System.Collections.Generic;

namespace APIProject.Service
{
    public interface ITransactionService
    {
        IEnumerable<Transaction> GetTransactions();
        Transaction GetTransaction(int id);
        void UpdateTransaction(Transaction Transaction);
        void CreateTransaction(Transaction Transaction);
        void SaveTransaction();
    }

    public class TransactionService : ITransactionService
    {
        private readonly ITransactionRepository TransactionRepository;
        private readonly IUnitOfWork unitOfWork;

        public TransactionService(ITransactionRepository TransactionRepository, IUnitOfWork unitOfWork)
        {
            this.TransactionRepository = TransactionRepository;
            this.unitOfWork = unitOfWork;
        }

        #region ITransactionService Members

        public IEnumerable<Transaction> GetTransactions()
        {
            return TransactionRepository.GetAll();
        }

        public Transaction GetTransaction(int id)
        {
            var Transaction = TransactionRepository.GetById(id);
            return Transaction;
        }

        public void CreateTransaction(Transaction Transaction)
        {
            TransactionRepository.Add(Transaction);
        }

        public void UpdateTransaction(Transaction Transaction)
        {
            TransactionRepository.Update(Transaction);
        }
        public void SaveTransaction()
        {
            unitOfWork.Commit();
        }

        #endregion
    }
}

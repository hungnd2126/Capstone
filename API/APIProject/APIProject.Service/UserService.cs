using APIProject.Data.Infrastructure;
using APIProject.Data.Repositories;
using APIProject.Model.Models;
using System.Collections.Generic;

namespace APIProject.Service
{
    public interface IUserService
    {
        IEnumerable<User> GetUsers();
        void UpdateUser(User user);
        void CreateUser(User user);
        void SaveUser();
    }

    public class UserService : IUserService
    {
        private readonly IUserRepository userRepository;
        private readonly IUnitOfWork unitOfWork;

        public UserService(IUserRepository userRepository, IUnitOfWork unitOfWork)
        {
            this.userRepository = userRepository;
            this.unitOfWork = unitOfWork;
        }

        #region IUserService

        public void CreateUser(User user)
        {
            userRepository.Add(user);
        }

        public IEnumerable<User> GetUsers()
        {
            return userRepository.GetAll();
        }

        public void SaveUser()
        {
            unitOfWork.Commit();
        }

        public void UpdateUser(User user)
        {
            userRepository.Update(user);
        }
        #endregion
    }
}

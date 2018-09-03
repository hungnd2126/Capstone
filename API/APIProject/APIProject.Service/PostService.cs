using APIProject.Data.Infrastructure;
using APIProject.Data.Repositories;
using APIProject.Model.Models;
using System.Collections.Generic;
using System.Linq;

namespace APIProject.Service
{
    public interface IPostService
    {
        IEnumerable<Post> GetPosts();
        Post GetPost(int id);
        void UpdatePost(Post Post);
        void CreatePost(Post Post);
        void SavePost();
    }

    public class PostService : IPostService
    {
        private readonly IPostRepository PostRepository;
        private readonly IUnitOfWork unitOfWork;

        public PostService(IPostRepository PostRepository, IUnitOfWork unitOfWork)
        {
            this.PostRepository = PostRepository;
            this.unitOfWork = unitOfWork;
        }

        #region IPostService Members

        public IEnumerable<Post> GetPosts()
        {
            return PostRepository.GetAll().Where(p => !p.IsDelete);
        }

        public Post GetPost(int id)
        {
            var Post = PostRepository.GetById(id);
            return Post;
        }

        public void CreatePost(Post Post)
        {
            PostRepository.Add(Post);
        }

        public void UpdatePost(Post Post)
        {
            PostRepository.Update(Post);
        }
        public void SavePost()
        {
            unitOfWork.Commit();
        }

        #endregion
    }
}

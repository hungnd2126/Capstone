using APIProject.Data.Infrastructure;
using APIProject.Data.Repositories;
using APIProject.Model.Models;
using System.Collections.Generic;

namespace APIProject.Service
{
    public interface IImageService
    {
        IEnumerable<Image> GetImages();
        Image GetImage(int id);
        void UpdateImage(Image image);
        void CreateImage(Image Image);
        void SaveImage();
    }

    public class ImageService : IImageService
    {
        private readonly IImageRepository ImageRepository;
        private readonly IUnitOfWork unitOfWork;

        public ImageService(IImageRepository ImageRepository, IUnitOfWork unitOfWork)
        {
            this.ImageRepository = ImageRepository;
            this.unitOfWork = unitOfWork;
        }

        #region IImageService Members

        public IEnumerable<Image> GetImages()
        {
            return ImageRepository.GetAll();
        }

        public Image GetImage(int id)
        {
            var Image = ImageRepository.GetById(id);
            return Image;
        }

        public void CreateImage(Image Image)
        {
            ImageRepository.Add(Image);
        }

        public void UpdateImage(Image Image)
        {
            ImageRepository.Update(Image);
        }
        public void SaveImage()
        {
            unitOfWork.Commit();
        }

        #endregion
    }
}

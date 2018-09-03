using APIProject.Model.Models;
using APIProject.Service;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace APIProject.Controllers
{
    [AllowAnonymous]
    public class GenerateDataController : ApiController
    {
        private readonly ICityService cityService;
        private readonly ICountryService countryService;
        private readonly IUserService userService;
        private readonly IPostService postService;
        private readonly ITripService tripService;
        private readonly IImageService imageService;
        private Random random = new Random();

        public GenerateDataController(ICityService cityService, ICountryService countryService,
            IUserService userService, IPostService postService, ITripService tripService, IImageService imageService)
        {
            this.cityService = cityService;
            this.countryService = countryService;
            this.userService = userService;
            this.postService = postService;
            this.tripService = tripService;
            this.imageService = imageService;
        }

        public IHttpActionResult GeneratePost(int min, int max)
        {
            var vietnam = cityService.GetCities().Where(c => c.Country.Name.Equals("Vietnam"));
            var listcity = cityService.GetCities();
            var listUser = userService.GetUsers();
            int count = 0;
            for (int i = min; i < max; i++)
            {
                Post post = new Post()
                {
                    BuyFromId = listcity.ElementAt(random.Next(listcity.Count() - 1)).Id,
                    DeliveryToId = vietnam.ElementAt(random.Next(vietnam.Count() - 1)).Id,
                    UserId = listUser.ElementAt(random.Next(listUser.Count() - 1)).Id,
                    Deposit = 0,
                    IsActive = true,
                    IsDelete = false,
                    DateCreated = DateTime.Now,
                    DateUpdated = DateTime.Now,
                    DeliveryDate = DateTime.Now.AddDays(random.Next(20)),
                    ProductName = "Sản phẩm " + (i + 1),
                    Price = 10000,
                    Type = Post.PostType.Requested,
                    ShippingFee = 1000,
                    Quantity = random.Next(0, 3),
                    Description = "Sản phẩm " + (i + 1)
                };

                postService.CreatePost(post);
            }

            try
            {
                postService.SavePost();
                count++;
            }
            catch (Exception)
            {

            }

           

            return Ok(count);
        }

        public IHttpActionResult GenerateImage()
        {
            var listpost = postService.GetPosts();
            foreach (var item in listpost)
            {
                Image image = new Image()
                {
                    IsActive = true,
                    IsDelete = false,
                    Path = "/Media/ProductImage/ssd.png",
                    DateCreated = DateTime.Now,
                    PostId = item.Id
                };
                imageService.CreateImage(image);
            }
            try
            {
                imageService.SaveImage();
            }
            catch (Exception)
            {
            }
            return Ok();
        }

        public IHttpActionResult GeneratePostForEmail(string username, int numOfPost)
        {
            var vietnam = cityService.GetCities().Where(c => c.Country.Name.Equals("Vietnam"));
            var listcity = cityService.GetCities();
            var user = userService.GetUsers().FirstOrDefault(u => u.UserName.Equals(username));
            int count = 0;
            for (int i = 0; i < numOfPost; i++)
            {
                try
                {
                    Post post = new Post()
                    {
                        BuyFromId = listcity.ElementAt(random.Next(listcity.Count() - 1)).Id,
                        DeliveryToId = vietnam.ElementAt(random.Next(vietnam.Count() - 1)).Id,
                        UserId = user.Id,
                        Deposit = 0,
                        IsActive = true,
                        IsDelete = false,
                        DateCreated = DateTime.Now,
                        DateUpdated = DateTime.Now,
                        DeliveryDate = DateTime.Now.AddDays(random.Next(20)),
                        ProductName = "Sản phẩm " + (i + 1)+ " " + username,
                        Price = 10000,
                        Type = Post.PostType.Requested,
                        ShippingFee = 1000,
                        Quantity = random.Next(0, 3),
                        Description = "Sản phẩm " + (i + 1)+ " " + username
                    };

                    postService.CreatePost(post);
                    postService.SavePost();

                    Image image = new Image()
                    {
                        IsActive = true,
                        IsDelete = false,
                        Path = "/Media/ProductImage/ssd.png",
                        DateCreated = DateTime.Now,
                        PostId = post.Id
                    };

                    imageService.CreateImage(image);
                    imageService.SaveImage();

                    count++;
                }
                catch (Exception)
                {

                }

            }
            return Ok(count);
        }
    }
}

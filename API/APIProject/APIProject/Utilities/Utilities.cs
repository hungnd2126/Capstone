using APIProject.Core.JWT;
using APIProject.Model.Models;
using APIProject.Redis;
using APIProject.Service;
using APIProject.ViewModel;
using StackExchange.Redis;
using System;
using System.IO;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Collections.Generic;

namespace APIProject
{
    public class Utilities
    {
        static IDatabase db = null;
        public static IDatabase GetDatabase()
        {
            if (db == null)
            {
                //Store to Redis
                var RedisConnection = RedisConnectionFactory.GetConnection();
                db = RedisConnection.GetDatabase();
            }
            return db;
        }
        public static String FormatPrice(string str, int loop)
        {
            var strReturn = "";

            if (str.Length <= 3)
            {
                return str;
            }
            else
            {
                loop = (loop - 1) < 0 ? 0 : (loop - 1);
                var tmp = FormatPrice(str.Substring(0, str.Length - 3), loop);
                strReturn = tmp + "," + str.Substring(tmp.Length - loop, str.Length - tmp.Length + loop);

            }
            return strReturn;
        }
        public static bool updatePostRedis(Post item, bool makeOffer)
        {
            try
            {         
                string key = "";
                if (item.BuyFromId.GetValueOrDefault() > 0)
                {
                     key = "Post_" + item.BuyFromId+ "_" + item.DeliveryToId;

                }
                else
                {
                    key = "Post_onWeb_" + item.CountryId + "_" + item.DeliveryToId;

                }

                var db = GetDatabase();

                var tmp = db.HashGet(key, item.Id.ToString());
                var modelRedis = PostViewModel.FromJson(tmp);
                var model = new PostViewModel()
                {
                    Id = item.Id,
                    Buy_Address = new Address()
                    {
                        Name = item.BuyFromAddress,
                        City_name = modelRedis.Buy_Address == null ? "" : modelRedis.Buy_Address.Name,
                        Country_name = modelRedis.Buy_Address == null ? "" : modelRedis.Buy_Address.Country_name,
                        Country_code = modelRedis.Buy_Address == null ? "" : modelRedis.Buy_Address.Country_code,
                    },
                    Delivery_Address = new Address()
                    {
                        Name = item.DeliveryToAddress,
                        City_name = modelRedis.Delivery_Address.Name,
                        Country_name = modelRedis.Delivery_Address.Country_name,
                        Country_code = modelRedis.Delivery_Address.Country_code,
                    },
                    DateCreated = item.DateCreated.GetValueOrDefault().ToString("yyyyMMdd HHmmss"),
                    DateUpdated = item.DateUpdated.GetValueOrDefault().ToString("yyyyMMdd HHmmss"),
                    Description = item.Description,
                    Price = item.Price,
                    ProductName = item.ProductName,
                    Quantity = item.Quantity,
                    ShippingFee = item.ShippingFee,
                    ImageUrl = modelRedis.ImageUrl,
                    NumberOffer = (makeOffer == true ? (item.ListOffer.Count + 1) : item.ListOffer.Count),
                    UserAvartar = modelRedis.UserAvartar,
                    Username = modelRedis.Username,
                    Nickname = modelRedis.Nickname,
                    Deposit = modelRedis.Deposit,
                    DeliveryDate = item.DeliveryDate.GetValueOrDefault().ToString("yyyyMMdd HHmmss"),
                    Mark = modelRedis.Mark,
                };
                db.HashSet(key, item.Id.ToString(), model.ToJson());
                return true;
            }
            catch
            {
                return false;
            }
        }
        public static bool deletePostRedis(string key, string feild)
        {
            try
            {
                var db = GetDatabase();
                db.HashDelete(key, feild);
                return true;
            }
            catch
            {
                return false;
            }

        }
        public static List<PostViewModel> reloadPostRedis(ICityService cityService, IPostService postService, IDatabase db)
        {
            try
            {
                var posts = postService.GetPosts().Where(p => p.IsActive == true).ToList();
                List<PostViewModel> lisResult = new List<PostViewModel>();
                foreach (var item in posts)
                {
                    var city_BuyFrom = cityService.GetCity(item.BuyFromId.GetValueOrDefault());
                    var city_DeliveryTo = cityService.GetCity(item.DeliveryToId);
                    var tmp = db.HashGet("Post_" + item.UserId.ToString(), item.Id.ToString());
                    var modelRedis = PostViewModel.FromJson(tmp);
                    var model = new PostViewModel()
                    {
                        Id = item.Id,
                        Buy_Address = new Address()
                        {
                            Name = item.BuyFromAddress,
                            City_name = item.BuyFrom.Name,
                            Country_name = item.BuyFrom.Country.Name,
                            Country_code = item.BuyFrom.Country.Code,
                        },
                        Delivery_Address = new Address()
                        {
                            Name = item.DeliveryToAddress,
                            City_name = item.DeliveryTo.Name,
                            Country_name = item.DeliveryTo.Country.Name,
                            Country_code = item.DeliveryTo.Country.Code,
                        },
                        DateCreated = item.DateCreated.GetValueOrDefault().ToString("yyyyMMdd HHmmss"),
                        DateUpdated = item.DateUpdated.GetValueOrDefault().ToString("yyyyMMdd HHmmss"),
                        Description = item.Description,
                        Price = item.Price,
                        ProductName = item.ProductName,
                        Quantity = item.Quantity,
                        ShippingFee = item.ShippingFee,
                        ImageUrl = modelRedis.ImageUrl,
                        UserAvartar = modelRedis.UserAvartar,
                        Username = modelRedis.Username,
                        NumberOffer = item.ListOffer.Count,
                        Nickname = modelRedis.Nickname,
                    };
                    lisResult.Add(model);
                    db.HashSet("Post_" + item.UserId.ToString(), item.Id.ToString(), model.ToJson());
                }

                return lisResult;
            }
            catch
            {
                return null;
            }

        }

        public static void UpdateRedis(IPostService postService, ICityService cityService,
            IUserService userService, IImageService imageService)
        {
            var db = GetDatabase();
            db.Multiplexer.GetServer("127.0.0.1", 6379).FlushAllDatabases();

            var listPost = postService.GetPosts().Where(p => p.IsActive && !p.IsDelete).ToList();

            foreach (var item in listPost)
            {
                var city_BuyFrom = cityService.GetCities().FirstOrDefault(ac => ac.Id == item.BuyFromId);

                var city_DeliveryTo = cityService.GetCities().FirstOrDefault(ac => ac.Id == item.DeliveryToId);

                var user = userService.GetUsers().Where(u => u.Id == item.UserId).FirstOrDefault();

                var model = new PostViewModel()
                {
                    Id = item.Id,
                    Buy_Address = new Address()
                    {
                        Name = item.BuyFromAddress,
                        City_name = item.BuyFrom.Name,
                        Country_name = item.BuyFrom.Country.Name,
                        Country_code = item.BuyFrom.Country.Code,
                    },
                    Delivery_Address = new Address()
                    {
                        Name = item.DeliveryToAddress,
                        City_name = item.DeliveryTo.Name,
                        Country_name = item.DeliveryTo.Country.Name,
                        Country_code = item.DeliveryTo.Country.Code,
                    },
                    DateCreated = item.DateCreated.GetValueOrDefault().ToString("yyyyMMdd HHmmss"),
                    DateUpdated = item.DateUpdated.GetValueOrDefault().ToString("yyyyMMdd HHmmss"),
                    Description = item.Description,
                    Price = item.Price,
                    DeliveryDate = item.DeliveryDate.GetValueOrDefault().ToString("dd/MM/yyyy"),
                    ProductName = item.ProductName,
                    Quantity = item.Quantity,
                    ShippingFee = item.ShippingFee,
                    ImageUrl = imageService.GetImages().Where(p => p.PostId == item.Id).FirstOrDefault().Path,
                    NumberOffer = item.ListOffer.Count(),
                    UserAvartar = user.ImageUrl,
                    Username = user.UserName,
                    Nickname = user.LastName
                };
                db.HashSet("Post_" + item.UserId.ToString(), item.Id.ToString(), model.ToJson());
            }
        }

        public static bool DeleteonRedis(Post post, string feild)
        {
            try
            {
                string key = "";
                if (post.BuyFromId.GetValueOrDefault() > 0)
                {
                    key = "Post_" + post.BuyFromId + "_" + post.DeliveryToId;

                }
                else
                {
                    key = "Post_onWeb_" + post.CountryId + "_" + post.DeliveryToId;

                }
                var db = GetDatabase();
                db.HashDelete(key, feild);
            }
            catch (Exception)
            {
                return false;
            }
            return true;
        }
        public static string GetUserNameFromRequest(HttpRequestMessage request)
        {
            string username = JwtManager.GetUserName(request.Headers.Authorization.Parameter);
            return username;
        }

        public static System.Drawing.Image ConvertFromBitmap(string base64String)
        {
            try
            {
                byte[] bytes = Convert.FromBase64String(base64String);
                System.Drawing.Image image = System.Drawing.Image.FromStream(new MemoryStream(bytes));
                return image;
            }
            catch (Exception)
            {
                return null;
            }
        }

        public static System.Drawing.Image GetImageFormUrl(string url)
        {
            WebClient client = new WebClient();
            Stream stream = client.OpenRead(url);
            System.Drawing.Image image = System.Drawing.Image.FromStream(stream);
            return image;
        }
    }
}
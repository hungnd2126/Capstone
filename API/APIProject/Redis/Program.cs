using APIProject.Redis;
using Redis.ViewModel;
using StackExchange.Redis;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading;
using System.Threading.Tasks;
using static Redis.PostViewModel;

namespace Redis
{
    class Program
    {
        static IDatabase db = null;
        private static int loopTime = 0;
        private static int percent = 100;

        private static IDatabase GetDatabase()
        {
            //if (db == null)
            //{
            //Store to Redis
            var RedisConnection = RedisConnectionFactory.GetConnection();

            //}
            return RedisConnection.GetDatabase();
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
        static void Main(string[] args)
        {
            var db = GetDatabase();
            db.Multiplexer.GetServer("127.0.0.1", 6379).FlushAllDatabases();
            Thread thread1 = new Thread(ScheduleHightCompleteOrder);
            Thread thread2 = new Thread(ScheduleCreditBuyer);       
            Thread thread3 = new Thread(SchedulePost);
            Thread thread4 = new Thread(ScheduleTrip);
            thread1.Start();
            thread2.Start();
            thread3.Start();
            thread4.Start();
      /*
             OneTimeScheduleUser();
             OneTimeSchedulePost();*/

        }
        public static void ScheduleHightCompleteOrder()
        {
            while (true)
            {
                var db = GetDatabase();
                var dbEntity = new CP_TAS_v2Entities();
                var listUser = dbEntity.AspNetUsers.ToList();
                long totalBuyer = 0;
                long totalTraveler = 0;
                int largestSizeOrder_Buyer = 0;
                long largestValueOrder_Buyer = 0;
                int largestSizeOrder_Traveler = 0;
                long largestValueOrder_Traveler = 0;
                SumOrderViewModel model = new SumOrderViewModel();
                foreach (var user in listUser)
                {
                    #region buyer
                    //lấy list order of buyer
                    var orders = dbEntity.Orders.Where(o => o.IsActive == true
                                                             && o.IsDelete == false
                                                             && o.Status == (int)Enum.OrderType.Completed
                                                             ).ToList();
                    // lấy transaction theo order
                    foreach (var p in orders)
                    {
                        // sum value on transaction
                        var tmp = p.Transactions.Sum(v => v.Value);
                        totalBuyer += tmp;

                    }


                    //exception null Size
                    try
                    {
                        largestSizeOrder_Buyer = (int)db.HashGet("HighestOrder_Buyer", "Size");
                        largestValueOrder_Buyer = (long)db.HashGet("HighestOrder_Buyer", "Value");

                    }
                    catch { }
                    // so sánh với giá trị có sẵn trong redis để cập nhật
                    if (largestSizeOrder_Buyer < orders.Count())
                    {
                        db.HashSet("HighestOrder_Buyer", "Size", largestSizeOrder_Buyer);
                    }
                    if (largestValueOrder_Buyer < totalBuyer)
                    {
                        db.HashSet("HighestOrder_Buyer", "Value", largestValueOrder_Buyer);
                    }
                    #endregion

                    #region travler
                    //lấy list order of buyer
                    var orders_travler = dbEntity.Orders.Where(o => o.IsActive == true
                                                             && o.IsDelete == false
                                                             && o.Status == (int)Enum.OrderType.Completed
                                                             && o.TravellerId == user.Id).ToList();
                    // lấy transaction theo order
                    foreach (var p in orders_travler)
                    {
                        // sum value on transaction
                        var tmp = p.Transactions.Sum(v => v.Value);
                        totalTraveler += tmp;

                    }


                    //exception null object
                    try
                    {
                        largestSizeOrder_Traveler = (int)db.HashGet("HighestOrder_Travler", "Size");
                        largestValueOrder_Traveler = (long)db.HashGet("HighestOrder_Travler", "Value");

                    }
                    catch { }
                    // so sánh với giá trị có sẵn trong redis để cập nhật
                    if (largestSizeOrder_Traveler < orders_travler.Count())
                    {
                        db.HashSet("HighestOrder_Travler", "Size", largestSizeOrder_Traveler);
                    }
                    if (largestValueOrder_Traveler < totalTraveler)
                    {
                        db.HashSet("HighestOrder_Travler", "Value", largestValueOrder_Traveler);
                    }
                    #endregion
                }
                Console.WriteLine("Process HighestOrder completed !!!");
                Thread.Sleep(1000 * 60 * 2);
                ++loopTime;
                if (loopTime == 25)
                {
                    loopTime = 0;
                    Console.Clear();
                }
            }
        }
        
        public static void ScheduleCreditBuyer()
        {
            while (true)
            {
                var db = GetDatabase();
                var dbEntity = new CP_TAS_v2Entities();
                var listUser = dbEntity.AspNetUsers.ToList();
                foreach (var item in listUser)
                {
                    double rate;
                    var rates = dbEntity.Ratings.Where(p => p.UserRatedId == item.Id && p.Type==(int)Enum.RateType.TravlerRate).ToList();
                    #region rate
                    // lấy total rate 
                    if (rates == null || rates.Count == 0)
                    {
                        rate = 50;
                    }
                    else
                    {
                        // tổng rate score trên số lần rate 
                        rate = (double)rates.Sum(a => a.Point) / (rates.Count() * 5) * percent;
                    }
                    item.BuyerScore = rate;
                    dbEntity.Entry(item).State = System.Data.Entity.EntityState.Modified;
                    dbEntity.SaveChanges();
                   
                    #endregion

                    #region complete
                    //tìm list post ra trc để query 2 lần co lẹ
                    var listPost = dbEntity.Posts.Where(p => p.IsActive == true && item.Id == p.UserId).ToList();
                    // tính số post complete
                    int postComplete = listPost.Where(p => p.Type == (int)Enum.PostType.Completed).Count();
                    
                    double completeRate = ((double)postComplete == 0 ? 0 : (((double)postComplete / listPost.Count) *percent)) ;
                    #endregion

                    #region transaction
                    double totalTransaction =0;
                    try
                    {
                        var tmp = dbEntity.Transactions.Where(t => t.UserId == item.Id).ToList();
                        totalTransaction = tmp.Sum(s => s.Value);
                    }
                    catch
                    {
                    }

                    double transactionRate = 0;
                    if (totalTransaction >= 2000000)
                    {
                        transactionRate = 20;
                    }
                    else if (totalTransaction > 50)
                    {
                        transactionRate = 2;

                    }
                    else if (totalTransaction > 80)
                    {
                        transactionRate = 3;

                    }
                    else if (totalTransaction > 100)
                    {
                        transactionRate = 4;
                    }
                    #endregion

                    #region Order score
                    int largestSizeOrder = 0;
                    try
                    {
                        largestSizeOrder = (int)db.HashGet("HighestOrder_Buyer", "Size");
                    }
                    catch { }
                    double orderScore = (largestSizeOrder == 0 ? 1 : (double)postComplete / largestSizeOrder) * percent;

                    #endregion

                    db.HashSet("CreditBuyer", item.Id.ToString(), ((rate + completeRate + transactionRate+ orderScore)/4));
                }
                Console.WriteLine("Process CreditBuyer completed !!!");
                Thread.Sleep(1000 * 60 * 2);
                ++loopTime;
                if (loopTime == 25)
                {
                    loopTime = 0;
                    Console.Clear();
                }

            }
        }

        public static void SchedulePost()
        {
            while (true)
            {
                var db = GetDatabase();
                var dbEntity = new CP_TAS_v2Entities();
                var listPost = dbEntity.Posts.Where(p => p.IsActive == true && p.Type == 0).ToList();

                foreach (var item in listPost)
                {
                    var city_BuyFrom = dbEntity.Cities.Where(ac => ac.Id == item.BuyFromId).FirstOrDefault();

                    var city_DeliveryTo = dbEntity.Cities.Where(ac => ac.Id == item.DeliveryToId).FirstOrDefault();
                    if (item.DeliveryDate < DateTime.UtcNow.AddHours(7))
                    {
                            try
                            {
                                item.Type = (int)Enum.PostType.Expired;
                                dbEntity.Entry(item).State = System.Data.
                                Entity.EntityState.Modified;
                                dbEntity.SaveChanges();
                                db.HashDelete("Post_" + city_BuyFrom.Id + "_" + city_DeliveryTo.Id, item.Id.ToString());
                            }
                            catch { }
                    }
                    else
                    {
                        var user = dbEntity.AspNetUsers.Where(u => u.Id == item.UserId).FirstOrDefault();
                        int total = item.Price* item.Quantity + item.ShippingFee;
                        //double profitMargin = ((double)item.ShippingFee / total) >= 0.3 ? 100 
                        //    : (((double)item.ShippingFee / total) / 0.3 * percent);

                        double profitMargin = ((double)item.ShippingFee / total) *100;
                        double DepositRate = ((double)item.Deposit / total) >= 1 ? 100
                            : (((double)item.Deposit / total)  * percent);

                        TimeSpan span = DateTime.UtcNow.AddHours(7).Subtract(item.DateUpdated.GetValueOrDefault());
                        int time = 1;
                        if (span.Hours > 1)
                        {
                            time = span.Days + 1;
                        }
                        else
                        {
                            time = span.Days;
                        }
                        var postMark = (profitMargin * 0.4 + DepositRate * 0.6);

                        double user_mark = (double)db.HashGet("CreditBuyer", item.UserId);
                        try
                        {
                            var model = new PostViewModel()
                            {
                                Id = item.Id,

                                DateCreated = item.DateCreated.GetValueOrDefault().ToString("yyyyMMdd HHmmss"),
                                DateUpdated = item.DateUpdated.GetValueOrDefault().ToString("yyyyMMdd HHmmss"),
                                Buy_Address = new Address()
                                {
                                    Name = item.BuyFromAddress,
                                    City_name = city_BuyFrom == null ? "" : city_BuyFrom.Name,
                                    Country_name = city_BuyFrom == null ? "" : city_BuyFrom.Country.Name,
                                    Country_code = city_BuyFrom == null ? "" : city_BuyFrom.Country.Code,
                                },
                                Delivery_Address = new PostViewModel.Address()
                                {
                                    Name = item.DeliveryToAddress,
                                    City_name = city_DeliveryTo.Name,
                                    Country_name = city_DeliveryTo.Country.Name,
                                    Country_code = city_DeliveryTo.Country.Code,
                                },
                                BuyFrom = city_BuyFrom == null ? item.Country.Name : item.BuyFromAddress + ", " + city_BuyFrom.Name + ", " + city_BuyFrom.Country.Name,
                                DeliveryTo = item.DeliveryToAddress + ", " + city_DeliveryTo.Name + ", " + city_DeliveryTo.Country.Name,
                                url = city_BuyFrom == null ? item.LinkWebsite : "",
                                domain = city_BuyFrom == null ? item.BuyFromAddress : "",
                                Description = item.Description,
                                Price = item.Price,
                                ProductName = item.ProductName,
                                Quantity = item.Quantity.ToString(),
                                ShippingFee = item.ShippingFee,
                                ImageUrl = dbEntity.Images.Where(p => p.PostId == item.Id).FirstOrDefault().Path,
                                NumberOffer = item.Offers.Count(),
                                UserAvartar = user.ImageUrl,
                                Username = user.UserName,
                                UserId = user.Id,
                                Nickname = user.LastName ,
                                Deposit = item.Deposit.GetValueOrDefault(),
                                DeliveryDate = item.DeliveryDate.GetValueOrDefault().ToString("dd/MM/yyyy"),
                                Mark = (postMark * 0.6 + user_mark * 0.4) / time
                            };
                            if (city_BuyFrom != null)
                            {
                                db.HashSet("Post_" + city_BuyFrom.Id + "_" + city_DeliveryTo.Id, item.Id.ToString(), model.ToJson());
                            }
                            else
                            {
                                db.HashSet("Post_onWeb_" + item.CountryId + "_" + city_DeliveryTo.Id, item.Id.ToString(), model.ToJson());

                            }
                        }
                        catch(Exception ex)
                        {
                            ex.ToString();
                        }
                    }
                }
                Console.WriteLine("Process post completed !!!");
                Thread.Sleep(1000 * 60 * 2);
                ++loopTime;
                if (loopTime == 25)
                {
                    loopTime = 0;
                    Console.Clear();
                }

            }
        }

        public static void ScheduleTrip()
        {
            while (true)
            {
                var db = GetDatabase();
                var dbEntity = new CP_TAS_v2Entities();
                var listUser = dbEntity.AspNetUsers.ToList();
                foreach (var item in listUser)
                {
                    double rate;
                    var rates = dbEntity.Ratings.Where(p => p.UserRateId == item.Id && p.Type == (int)Enum.RateType.BuyerRate).ToList();
                    #region rate
                    // lấy total rate 
                    if (rates == null || rates.Count == 0)
                    {
                        rate = 50;
                    }
                    else
                    {
                        // tổng rate score trên số lần rate 
                        rate = (double)rates.Sum(a => a.Point) / (rates.Count() * 5) * percent;
                    }
                    item.BuyerScore = rate;
                    dbEntity.Entry(item).State = System.Data.Entity.EntityState.Modified;
                    dbEntity.SaveChanges();
                    #endregion

                    #region complete
                    //tìm list order ra trc để query 2 lần co lẹ
                    var orders = dbEntity.Orders.Where(o => o.TravellerId == item.Id
                                               && o.IsActive).ToList();
                    // tính số order complete
                    int orderComplete = orders.Where(p => p.Status == (int)Enum.OrderType.Completed).Count();

                    double completeRate = ((double)orderComplete == 0 ? 0 : (((double)orderComplete / orders.Count) * percent));
                    #endregion

                    #region transaction
                    double totalTransaction = 0;
                    try
                    {
                        var tmp = dbEntity.Transactions.Where(t => t.UserId == item.Id).ToList();
                        totalTransaction = tmp.Sum(s => s.Value);
                    }
                    catch
                    {
                    }

                    double transactionRate = 0;
                    if (totalTransaction >= 2000000)
                    {
                        transactionRate = 20;
                    }
                    else if (totalTransaction > 50)
                    {
                        transactionRate = 2;

                    }
                    else if (totalTransaction > 80)
                    {
                        transactionRate = 3;

                    }
                    else if (totalTransaction > 100)
                    {
                        transactionRate = 4;
                    }
                    #endregion

                    #region Order score
                    int largestSizeOrder = 0;
                    try
                    {
                        largestSizeOrder = (int)db.HashGet("HighestOrder_Traveler", "Size");
                    }
                    catch { }
                    double orderScore = (largestSizeOrder == 0 ? 1 : orderComplete / largestSizeOrder) * percent;
                    #endregion
                    #region trip
                    
                    db.HashSet("CreditTraveler", item.Id.ToString(), ((rate + completeRate + transactionRate + orderScore) / 4));

                    #endregion
                }
                Console.WriteLine("Process CreditTraveler completed !!!");
                Thread.Sleep(1000 * 60 * 2);
                ++loopTime;
                if (loopTime == 25)
                {
                    loopTime = 0;
                    Console.Clear();
                }

            }

        }
    
    }
}

using APIProject.Model.Models;
using APIProject.Service;
using APIProject.ViewModel;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web.Http;

namespace APIProject.Controllers
{
    [RoutePrefix("api/notification")]
    [Authorize]
    public class NotificationController : ApiBaseController
    {

        #region service
        private readonly IUserService userService;
        private readonly INotificationService notificationService;

        #endregion

        #region contructor
        public NotificationController(IUserService userService, INotificationService notificationService)
        {
            this.userService = userService;
            this.notificationService = notificationService;
        }
        #endregion

        [HttpGet]
        [Route("get-notification")]
        public IHttpActionResult GetNotificationByUser()
        {
            string username = Utilities.GetUserNameFromRequest(Request);
            if (username == null)
            {
                return Unauthorized();
            }
            List<NotificationViewModel> response = new List<NotificationViewModel>();
            try
            {
                User user = userService.GetUsers().FirstOrDefault(u => u.UserName.Equals(username));
                if (user == null)
                {
                    return BadRequest();
                }
                var collection = notificationService.GetNotifications().Where(t => t.UserId.Equals(user.Id) && t.IsActive && !t.IsDelete).OrderByDescending(n => n.DateCreated);
                foreach (var item in collection)
                {
                    response.Add(new NotificationViewModel()
                    {
                        DateCreated = item.DateCreated.GetValueOrDefault().ToString(TIMEFORMAT),
                        IsRead = item.IsRead,
                        Message = item.Message,
                        Title = item.Title
                    });
                }
            }
            catch (Exception)
            {
                return BadRequest();
            }
            return Ok(response);
        }


    }
}
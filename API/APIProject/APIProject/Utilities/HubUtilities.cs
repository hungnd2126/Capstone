using APIProject.SignalRHub;
using Microsoft.AspNet.SignalR;

namespace APIProject
{
    public class HubUtilities
    {
        public static IHubContext GetHub()
        {
            return GlobalHost.ConnectionManager.GetHubContext<NotificationHub>();
        }
    }
}
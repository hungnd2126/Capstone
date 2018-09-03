using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.Web;
using APIProject.Data;
using APIProject.Model.Models;
using Microsoft.AspNet.SignalR;
using Microsoft.AspNet.SignalR.Hubs;

namespace APIProject.SignalRHub
{
    [HubName("NotificationHub")]
    public class NotificationHub : Hub
    {

        private static ConcurrentDictionary<string, string> ConnectionIDs = new ConcurrentDictionary<string, string>();         // <connectionId, userName>
        private static ConcurrentDictionary<string, string> Users = new ConcurrentDictionary<string, string>();           // <userName, connectionId>
        private APIProjectEntities Entities = new APIProjectEntities();
        public override Task OnConnected()
        {           
            DoConnect();
            return base.OnConnected();
        }

        private void DoConnect()
        {
            var userNamemobile = Context.Request.Headers["username"];
            string userNamejs = Context.Request.QueryString["accessToken"];
            var userName = "";
            if (userNamemobile != null && userNamemobile.Length > 0)
            {
                userName = userNamemobile;
            }
            else if (userNamejs != null && userNamejs.Length > 0)
            {
                userName = userNamejs;
            }
            if (userName == null || userName.Length == 0)
            {
                return;
            }
            ConnectionIDs.TryAdd(Context.ConnectionId, userName);
            string OldId;
            Users.TryRemove(userName, out OldId);
            Users.TryAdd(userName, Context.ConnectionId);

            //update connectionId in entities
            User CurrentUser = Entities.Users.FirstOrDefault(u => u.UserName.Equals(userName));
            if (CurrentUser != null)
            {
                CurrentUser.SignalConnect = Context.ConnectionId;
                Entities.SaveChangesAsync();
            }
        }

        public override Task OnDisconnected(bool stopCalled)
        {
            string userName;
            if (stopCalled) // Client explicitly closed the connection
            {
                var id = Context.ConnectionId;
                ConnectionIDs.TryRemove(id, out userName);
                Users.TryRemove(userName, out id);
            }
            else // Client timed out
            {
                // Do nothing here...
                // FromUsers.TryGetValue(Context.ConnectionId, out userName);            
                // Clients.AllExcept(Context.ConnectionId).broadcastMessage(new ChatMessage() { UserName = userName, Message = "I'm Offline By TimeOut"});                
            }

            return base.OnDisconnected(stopCalled);
        }

       
    }
}
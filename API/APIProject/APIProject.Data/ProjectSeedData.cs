using APIProject.Model.Models;
using Microsoft.AspNet.Identity;
using Microsoft.AspNet.Identity.EntityFramework;
using System.Collections.Generic;
using System.Data.Entity;

namespace APIProject.Data
{
    public class ProjectSeedData : DropCreateDatabaseAlways<APIProjectEntities>
    {

        private UserManager<User> UserManager;
        private RoleManager<IdentityRole> RoleManager;

        protected override void Seed(APIProjectEntities context)
        {
            UserManager = new UserManager<User>(new UserStore<User>(context));
            RoleManager = new RoleManager<IdentityRole>(new RoleStore<IdentityRole>(context));


            //Create Roles
            var listRoles = new List<string>(new string[] { "Admin", "Member" });
            foreach (var role in listRoles)
            {
                if (!RoleManager.RoleExists(role))
                {
                    RoleManager.Create(new IdentityRole(role));
                }
            }

            //Create User

            var users = new List<User>() {
                  new User(){ UserName = "admin", FirstName = "Bao", LastName = "Admin",RoleId = SystemRoles.Role01},
                new User(){ UserName = "baobao", FirstName = "Bao", LastName = "Bao",RoleId = SystemRoles.Role02}};
            foreach (var user in users)
            {
                if (UserManager.FindByName(user.UserName) == null)
                {
                    UserManager.Create(user, "Zaq@123");
                }
            }

            GetCategories().ForEach(c => context.Categories.Add(c));

            context.Commit();
        }
        

        private static List<Category> GetCategories()
        {
            return new List<Category>
            {
                new Category {
                    Name = "Food"
                }
            };
        }
    }
}

using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using System.Web.Routing;

namespace APIProject
{
    public class RouteConfig
    {
        public static void RegisterRoutes(RouteCollection routes)
        {
            routes.IgnoreRoute("{resource}.axd/{*pathInfo}");

            //routes.MapRoute(
            //    name: "Default",
            //    url: "{controller}/{action}/{id}",
            //    defaults: new { controller = "Home", action = "Index", id = UrlParameter.Optional }
            //);
            //  routes.MapRoute(
            //    name: "Checkouts-New",
            //    url: "checkouts/new/id=5",
            //    defaults: new { controller = "Checkouts", action = "New", total = "" },
            //    constraints: new { httpMethod = new HttpMethodConstraint(new string[] { "GET" }) }
            //);
            routes.MapRoute(
                name: "Checkouts-New",
                url: "checkouts/new",
                defaults: new { controller = "Checkouts", action = "New", total = "" },
                constraints: new { httpMethod = new HttpMethodConstraint(new string[] { "POST" }) }
            );

            routes.MapRoute(
              name: "Checkouts-Create",
              url: "checkouts",
              defaults: new { controller = "Checkouts", action = "Create" },
              constraints: new { httpMethod = new HttpMethodConstraint(new string[] { "POST" }) }
          );
        }
    }
}

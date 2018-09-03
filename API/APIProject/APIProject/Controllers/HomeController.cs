using APIProject.Model.Models;
using APIProject.Service;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace APIProject.Controllers
{
    public class HomeController : Controller
    {
        private readonly ICategoryService _categoryService;
        public HomeController(ICategoryService _categoryService)
        {
            this._categoryService = _categoryService;
        }

        public ActionResult Index()
        {
            ViewBag.Title = "Home Page";

            return View();
        }

        public ActionResult Read()
        {
            return this.Json(_categoryService.GetCategories().ToList(), JsonRequestBehavior.AllowGet);
        }
    }
}

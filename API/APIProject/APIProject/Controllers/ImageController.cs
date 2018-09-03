using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web;
using System.Web.Http;
using APIProject.Model.Models;

namespace APIProject.Controllers
{
    [RoutePrefix("api/image")]
    [Authorize]
    public class ImageController : ApiController
    {
        [HttpPost]
        [Route("upload-image")]
        public IHttpActionResult UploadImage(Image image)
        {
            try
            {
                string fileName = Guid.NewGuid().ToString() + ".jpg";
                string filePath = HttpContext.Current.Server.MapPath("~/Media/MessageImage/") + fileName;
                Utilities.ConvertFromBitmap(image.Path).Save(filePath);
                var result = "/Media/MessageImage/" + fileName;
                return Ok(result);
            }
            catch
            {
                return BadRequest("Upload fail");

            }
        }
    }
}

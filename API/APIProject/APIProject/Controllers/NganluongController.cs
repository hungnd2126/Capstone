using APIProject.ViewModel;
using System.IO;
using System.Net;
using System.Web.Http;
using System.Xml;

namespace APIProject.Controllers
{
    public class NganluongController : ApiController
    {
        private readonly NLUtilities nganluong;
        public NganluongController()
        {
            nganluong = new NLUtilities();
        }
        public object GetInfo()
        {
            NganluongResponse<ResponseInfo> result = nganluong.DoGetInfo();
            return result;
        }

        public IHttpActionResult Tranfers(string receive_email, string amout, string description)
        {
            NganluongResponse<ResponseTranfer> result = nganluong.DoTranfer(receive_email, amout, description);
            return Ok(result);
        }
    }
}

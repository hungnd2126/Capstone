using APIProject.Core.JWT.Filters;
using APIProject.Model.Models;
using APIProject.Service;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace APIProject.Controllers
{
    public class ValuesController : ApiBaseController
    {
        private readonly ICategoryService _categoryService;
        private readonly ITransactionService transactionService;
        public ValuesController(ICategoryService _categoryService, ITransactionService transactionService)
        {
            this._categoryService = _categoryService;
            this.transactionService = transactionService;
        }
        // GET api/values
        public HttpResponseMessage Get()
        {
            return CreateHttpResponse(Request, () =>
            {
                var result = _categoryService.GetCategories();
                return Request.CreateResponse(HttpStatusCode.OK, result);

            });
        }
        [JwtAuthentication]
        // GET api/values/5
        public string Get(int id)
        {
            return "value";
        }
        // POST api/values
        public void Post([FromBody]string value)
        {
        }
        // PUT api/values/5
        public void Put(int id, [FromBody]string value)
        {
        }
        [Authorize]
        // DELETE api/values/5
        public string Delete(int id)
        {
            return "delete";
        }  
        public void TestSR(string id)
        {
            HubUtilities.GetHub().Clients.All.receivedOffer("Nhận được 1 đề nghị mới", id );
            HubUtilities.GetHub().Clients.All.hello("connected");
        }
        public IHttpActionResult GetAllTrans()
        {
            var list = transactionService.GetTransactions().ToList();
            return Ok(list);
        }
    }
}

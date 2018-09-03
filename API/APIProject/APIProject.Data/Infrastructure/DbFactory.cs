using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace APIProject.Data.Infrastructure
{
    public class DbFactory : Disposable, IDbFactory
    {
        APIProjectEntities dbContext;

        public APIProjectEntities Init()
        {
            return dbContext ?? (dbContext = new APIProjectEntities());
        }

        protected override void DisposeCore()
        {
            if (dbContext != null)
                dbContext.Dispose();
        }
    }
}

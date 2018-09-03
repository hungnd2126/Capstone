using System;
using MongoDB.Bson;
namespace APIProject.Core.Common
{
    public static class Validation
    {
        public static string Id(string id)
        {
            if (string.IsNullOrEmpty(id))
            {
                return null;
            }

            if (id.Length != 24)
            {
                return null;
            }

            try
            {
                return new ObjectId(id).ToString();
            }
            catch (FormatException)
            {
                return null;
            }
        }
    }
}
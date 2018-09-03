using APIProject.Core.JWT;
using APIProject.ViewModel;
using System;
using System.Drawing;
using System.IO;
using System.Net;
using System.Net.Http;
using System.Security.Cryptography;
using System.Text;
using System.Xml;
using System.Xml.Serialization;

namespace APIProject
{
    public static class XmlUtilites<T>
    {
        public static NganluongResponse<T> ConvertXmlToObject(string xmlText)
        {
            NganluongResponse<T> result = new NganluongResponse<T>();
            try
            {
                XmlDocument dom = new XmlDocument();
                dom.LoadXml(xmlText);
                XmlSerializer serializer = new XmlSerializer(typeof(NganluongResponse<T>));
                using (TextReader reader = new StringReader(dom.DocumentElement.FirstChild.FirstChild.InnerXml.ToString()))
                {
                    result = (NganluongResponse<T>)serializer.Deserialize(reader);
                }
            }
            catch (Exception)
            {
                return null;
            }
            return result;
        }
    }
}
using APIProject.ViewModel;
using System;
using System.IO;
using System.Net;
using System.Security.Cryptography;
using System.Text;
using System.Xml;

namespace APIProject
{
    public class NLUtilities
    {
        private readonly string root = "https://sandbox.nganluong.vn:8088/nl35/payoutTranfer.php";
        public NLUtilities()
        {

        }
        private HttpWebRequest CreateWebRequest(string action, int lenght)
        {
            HttpWebRequest webRequest = (HttpWebRequest)WebRequest.Create(root);
            webRequest.Headers.Add(@"SOAPAction:" + root + action);
            webRequest.ContentType = "text/xml;charset=\"utf-8\"";
            webRequest.Accept = "text/xml";
            webRequest.Method = "POST";
            webRequest.ContentLength = lenght;
            return webRequest;
        }
        private string Base_request(string body)
        {
            string request = "";
            request += @"<?xml version=""1.0"" encoding=""utf-8""?>";
            request += @"<SOAP-ENV:Envelope SOAP-ENV:encodingStyle=""http://schemas.xmlsoap.org/soap/encoding/"" ";
            request += @"xmlns:SOAP-ENV=""http://schemas.xmlsoap.org/soap/envelope/"" ";
            request += @"xmlns:xsi=""http://www.w3.org/2001/XMLSchema-instance"">";
            request += @"<SOAP-ENV:Body>";
            request += body;
            request += @"</SOAP-ENV:Body>";
            request += @"</SOAP-ENV:Envelope>";

            return request;
        }
        private string getInfoRequest()
        {
            string body = "";
            body += @"<getInfo>";
            body += @"<merchant_id xsi:type=""xsd:string"">46257</merchant_id>";
            body += @"<merchant_password xsi:type=""xsd:string"">6a3d0a1373cbb28b3016ec833007ce99</merchant_password>";
            body += @"<user_email xsi:type=""xsd:string"">baonguyenquoc357@gmail.com</user_email>";
            body += @"</getInfo>";

            string request = Base_request(body);

            return request;
        }
        private string getTranferRequest(string receiver, string amout, string description)
        {
            string body = "";
            body += @"<tranfer>";
            body += @"<merchant_id xsi:type=""xsd:string"">46257</merchant_id>";
            body += @"<merchant_password xsi:type=""xsd:string"">6a3d0a1373cbb28b3016ec833007ce99</merchant_password>";
            body += @"<user_email xsi:type=""xsd:string"">baonguyenquoc357@gmail.com</user_email>";
            body += @"<receive_email xsi:type=""xsd:string"">"+receiver+"</receive_email>";
            body += @"<amount xsi:type=""xsd:string"">"+amout+"</amount>";
            body += @"<reference_code xsi:type=""xsd:string"">"+description+"</reference_code>";
            body += @"</tranfer>";

            string request = Base_request(body);

            return request;
        }
        private void InsertSoapEnvelopeIntoWebRequest(XmlDocument soapEnvelopeXml, HttpWebRequest webRequest)
        {
            webRequest.SendChunked = true;
            using (Stream stream = webRequest.GetRequestStream())
            {
                soapEnvelopeXml.Save(stream);
            }
        }
        private String MD5Hash(string input)
        {
            // Use input string to calculate MD5 hash
            MD5 md5 = MD5.Create();
            byte[] inputBytes = System.Text.Encoding.ASCII.GetBytes(input);
            byte[] hashBytes = md5.ComputeHash(inputBytes);

            // Convert the byte array to hexadecimal string
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < hashBytes.Length; i++)
            {
                sb.Append(hashBytes[i].ToString("x2"));
            }
            return sb.ToString();
        }
        private XmlDocument CreateSoapBody(string request)
        {
            XmlDocument soapEnvelopeDocument = new XmlDocument();
            soapEnvelopeDocument.LoadXml(request);
            return soapEnvelopeDocument;
        }
        public NganluongResponse<ResponseInfo> DoGetInfo()
        {
            var _action = "/getInfo";
            XmlDocument soapEnvelopeXml = CreateSoapBody(getInfoRequest());
            HttpWebRequest webRequest = CreateWebRequest(_action, soapEnvelopeXml.InnerText.Length);
            InsertSoapEnvelopeIntoWebRequest(soapEnvelopeXml, webRequest);

            string soapResult = "";
            using (WebResponse webResponse = webRequest.GetResponse())
            {
                using (StreamReader rd = new StreamReader(webResponse.GetResponseStream()))
                {
                    soapResult = rd.ReadToEnd();
                }

            }
            NganluongResponse<ResponseInfo> result = XmlUtilites<ResponseInfo>.ConvertXmlToObject(soapResult);
            return result;
        }

        public NganluongResponse<ResponseTranfer> DoTranfer(string receive_email, string amout, string description)
        {
            var _action = "/tranfer";
            XmlDocument soapEnvelopeXml = CreateSoapBody(getTranferRequest(receive_email, amout, description));
            HttpWebRequest webRequest = CreateWebRequest(_action, soapEnvelopeXml.InnerText.Length);
            InsertSoapEnvelopeIntoWebRequest(soapEnvelopeXml, webRequest);

            string soapResult = "";
            using (WebResponse webResponse = webRequest.GetResponse())
            {
                using (StreamReader rd = new StreamReader(webResponse.GetResponseStream()))
                {
                    soapResult = rd.ReadToEnd();
                }

            }
            NganluongResponse<ResponseTranfer> result = XmlUtilites<ResponseTranfer>.ConvertXmlToObject(soapResult);
            return result;
        }

    }
}
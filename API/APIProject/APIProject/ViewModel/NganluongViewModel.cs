using System.Xml.Serialization;

namespace APIProject.ViewModel
{
    [XmlRoot("result")]
    public partial class NganluongResponse<T>
    {
        [XmlElement("response_code")]
        public string Response_code { get; set; }
        [XmlElement("response")]
        public T Response { get; set; }
    }
    public class ResponseInfo
    {
        [XmlElement("birthday")]
        public int Birthday { get; set; }
        [XmlElement("verified_status")]
        public int Verified_status { get; set; }
        [XmlElement("gender")]
        public int Gender { get; set; }
        [XmlElement("fullname")]
        public string Fullname { get; set; }
        [XmlElement("status")]
        public string Status { get; set; }
        [XmlElement("email")]
        public string Email { get; set; }
        [XmlElement("created_time")]
        public int Created_time { get; set; }
        [XmlElement("mobile")]
        public string Mobile { get; set; }
    }

    public class ResponseTranfer
    {
        [XmlElement("request_id")]
        public string Request_id { get; set; }
        [XmlElement("time_created")]
        public int Time_created { get; set; }
        [XmlElement("client_ip")]
        public string Client_ip { get; set; }
        [XmlElement("transaction_id")]
        public int Transaction_id { get; set; }
        
    }
}
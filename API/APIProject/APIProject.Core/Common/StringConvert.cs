using System.Text;
using System.Text.RegularExpressions;
using System.Web.WebPages;

namespace APIProject.Core.Common
{
    public class StringConvert
    {
        public static string Chop(string text, int chopLength = 400, string postfix = "...")
        {
            if (text == null || text.Length < chopLength) return text;
            return text.Substring(0, chopLength - postfix.Length) + postfix;
        }

        public static string RemoveMultiSpace(string str)
        {
            if (string.IsNullOrEmpty(str)) return null;
            const RegexOptions options = RegexOptions.None;
            var regex = new Regex("[ ]{2,}", options);
            str = regex.Replace(str, " ");

            return str.Trim();
        }

        public static string StripHtml(string str)
        {
            if (str.IsEmpty()) return "";
            str = RemoveMultiSpace(str);
            str = RemoveVietnameseString(str);
            return Regex.Replace(str, "<.*?>", string.Empty);
        }

        private static readonly string[] VietnameseSigns =
        {
            "aAeEoOuUiIdDyY", "áàạảãâấầậẩẫăắằặẳẵ", "ÁÀẠẢÃÂẤẦẬẨẪĂẮẰẶẲẴ", "éèẹẻẽêếềệểễ",
            "ÉÈẸẺẼÊẾỀỆỂỄ", "óòọỏõôốồộổỗơớờợởỡ", "ÓÒỌỎÕÔỐỒỘỔỖƠỚỜỢỞỠ", "úùụủũưứừựửữ",
            "ÚÙỤỦŨƯỨỪỰỬỮ", "íìịỉĩ", "ÍÌỊỈĨ", "đ", "Đ", "ýỳỵỷỹ", "ÝỲỴỶỸ"
        };

        public static string RemoveVietnameseString(string str)
        {
            if (str.IsEmpty()) return "";
            str = RemoveMultiSpace(str);
            for (var i = 1; i < VietnameseSigns.Length; i++)
            {
                for (var j = 0; j < VietnameseSigns[i].Length; j++)
                    str = str.Replace(VietnameseSigns[i][j], VietnameseSigns[0][i - 1]);
            }
            return str.ToLower();
        }

        public static string ConvertShortName(string strVietNamese)
        {
            if (strVietNamese.IsEmpty()) return "";
            //Loại bỏ dấu ':'
            char[] delimiter = {':', '?', '"', '/', '!', ',', '-', '=', '%', '$', '&', '*', '(', ')', '{', '}'};
            strVietNamese = RemoveMultiSpace(strVietNamese);
            var subString = strVietNamese.Split(delimiter);
            var sb = new StringBuilder("");
            foreach (var t in subString)
            {
                sb.Append(t);
            }
            strVietNamese = sb.ToString();
            //Loại bỏ tiếng việt
            const string textToFind =
                " .áàảãạâấầẩẫậăắằẳẵặđéèẻẽẹêếềểễệíìỉĩịóòỏõọôốồổỗộơớờởỡợúùủũụưứừửữựýỳỷỹỵÁÀẢÃẠÂẤẦẨẪẬĂẮẰẲẴẶĐÉÈẺẼẸÊẾỀỂỄỆÍÌỈĨỊÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢÚÙỦŨỤƯỨỪỬỮỰÝỲỶỸỴ";
            const string textToReplace =
                "--aaaaaaaaaaaaaaaaadeeeeeeeeeeeiiiiiooooooooooooooooouuuuuuuuuuuyyyyyAAAAAAAAAAAAAAAAADEEEEEEEEEEEIIIIIOOOOOOOOOOOOOOOOOUUUUUUUUUUUYYYYY";
            int index;
            while ((index = strVietNamese.IndexOfAny(textToFind.ToCharArray())) != -1)
            {
                int index2 = textToFind.IndexOf(strVietNamese[index]);
                strVietNamese = strVietNamese.Replace(strVietNamese[index], textToReplace[index2]);
            }

            return strVietNamese.ToLower();
        }
    }
}
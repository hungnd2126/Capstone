using System.ComponentModel.DataAnnotations;

namespace APIProject.ViewModel
{
    /// <summary>
    /// 
    /// </summary>
    public class LoginRequest
    {
        /// <summary>
        /// 
        /// </summary>
        public string Email { get; set; }

        /// <summary>
        /// 
        /// </summary>
        public string Password { get; set; }
    }

    /// <summary>
    /// 
    /// </summary>
    public class LoginViewModel
    {
        public string Status_code { get; set; }
        public string Message { get; set; }
        public string AccessToken { get; set; }
        public string Uid { get; set; }
        public string Firstname { get; set; }
        public string Lastname { get; set; }
        public string Email { get; set; }
        public string Avatar { get; set; }
        public string Type { get; set; }
        public double Amount { get; set; }
        public string Bio { get; set; }
        public string Phone { get; set; }
        public string Address { get; set; }
        public string GeoId { get; set; }
        public string Tknl { get; set; }
    }

    /// <summary>
    /// 
    /// </summary>
    public class ChangePasswordRequest
    {
        /// <summary>
        /// 
        /// </summary>
        public string Username { get; set; }
        /// <summary>
        /// 
        /// </summary>
        public string OldPassword { get; set; }

        /// <summary>
        /// 
        /// </summary>
        [DataType(DataType.Password)]
        public string NewPassword { get; set; }

        /// <summary>
        /// 
        /// </summary>
        [Compare("NewPassword")]
        [DataType(DataType.Password)]
        public string ConfirmPassword { get; set; }
    }
}
namespace APIProject.ViewModel
{
    public class NotificationViewModel
    {
        public NotificationViewModel()
        {
        }

        #region property
        public string Message { get; set; }
        public string Title { get; set; }
        public string DateCreated { get; set; }
        public bool IsRead { get; set; }
        #endregion
    }

}
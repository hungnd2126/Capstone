namespace APIProject.ViewModel
{
    public class CompleteOrderResponse
    {
        public string Status_code { get; set; }
        public int OrderId { get; set; }
        public int TransactionId { get; set; }
        public int Deposit { get; set; }
        public double Current_Money { get; set; }

    }
}
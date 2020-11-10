
interface Printable {

    public void printData();
    public void printDataHidden();
    public void printAccountType();

    /**
     * This method is used to store transactions in a log file or bank statement
     * @param acct
     */
    public static void printTransaction(Customer acct){

    }
}

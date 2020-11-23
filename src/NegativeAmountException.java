public class NegativeAmountException extends Exception{
    double amount;

    NegativeAmountException(double amountIn){
        amount = amountIn;
    }

    public String toString() {
        return("Invalid amount: " + amount + " is negative.");
    }

}

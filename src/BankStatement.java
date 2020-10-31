public class BankStatement {
	private String dateTime;
	private String transType;
	private String account;
	private double amount;
	private double prevBalance;
	private double newBalance;
	
	public BankStatement(String dateTime, String transType, String account, double amount, double prevBalance, double newBalance) {
		super();
		this.dateTime = dateTime;
		this.transType = transType;
		this.account = account;
		this.amount = amount;
		this.prevBalance = prevBalance;
		this.newBalance = newBalance;
	}
	/**
	 * This method is used to get date and time
	 * @return date and time
	 */
	public String getDateTime() {
		return dateTime;
	}
	/**
	 * This method is used to set date and time
	 * @param dateTime - date and time
	 */
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	/**
	 * This method is used to get transaction type
	 * @return transaction type
	 */
	public String getTransType() {
		return transType;
	}
	/**
	 * This method is used to set transaction type
	 * @param transType - transaction type
	 */
	public void setTransType(String transType) {
		this.transType = transType;
	}
	
	/**
	 * This method is used to get account type
	 * @return account type
	 */
	public String getAccount() {
		return account;
	}
	/**
	 * This method is used to set account type
	 * @param account - account type
	 */
	public void setAccount(String account) {
		this.account = account;
	}
	/**
	 * This method is used to get amount of transaction
	 * @return amount of transaction
	 */
	public double getAmount() {
		return amount;
	}
	/**
	 * This method is used to set transaction amount
	 * @param amount - transaction amount
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	/**
	 * This method is used to get previous balance
	 * @return previous balance
	 */
	public double getPrevBalance() {
		return prevBalance;
	}
	/**
	 * This method is used to set previous balance
	 * @param prevBalance - previous balance
	 */
	public void setPrevBalance(double prevBalance) {
		this.prevBalance = prevBalance;
	}
	/**
	 * This method is used to get current balance
	 * @return current balance
	 */
	public double getNewBalance() {
		return newBalance;
	}
	/**
	 * This method is used to set current balance
	 * @param newBalance - new balance
	 */
	public void setNewBalance(double newBalance) {
		this.newBalance = newBalance;
	}
	
	
	
}

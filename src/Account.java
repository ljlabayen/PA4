/**
* @author Laurence Labayen
* @version 2.0
* 
* CS 3331 – Advanced Object-Oriented Programming – Fall 2020
* Dr. Daniel Mejia 
* 
* Assignment 2 - RunBank
* Description: Continuation of assignment 1. This is a program that reads CSV file
* that holds bank accounts and simulates a simple bank with multiple scenarios that
* would be used in a real-life bank.
* 
* I confirm that the work of this assignment is completely my own. By turning in this 
* assignment, I declare that I did not receive unauthorized assistance. Moreover, all
* deliverables including, but not limited to the source code, lab report and output 
* files were written and produced by me alone.
*/

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public abstract class Account implements Printable{
	private String firstName;
	private String lastName;
	private int accountNumber;
	private double accountBalance;
	

	/**
	 * Constructor for Account Object
	 * @param firstName - 
	 * @param lastName
	 * @param accountNumber
	 * @param accountBalance
	 */
	public Account(String firstName, String lastName, int accountNumber, double accountBalance) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.accountNumber = accountNumber;
		this.accountBalance = accountBalance;
	}
	
	/**
	 * This method is used to get first name
	 * @return first name of account holder
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * This method is used to set first name
	 * @param firstName desired first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * This method is used to get last name
	 * @return last name of account holder
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * This method is used to set last name
	 * @param lastName desired last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * This method is used to get account number
	 * @return account number of account
	 */
	public int getAccountNumber() {
		return accountNumber;
	}
	/**
	 * This method is used to set account number
	 * @param accountNumber - desired account number
	 */
	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}
	/**
	 * This method is used to get account balance
	 * @return balance of account holder
	 */
	public double getAccountBalance() {
		return accountBalance;
	}
	/**
	 * This method is used to set account balance
	 * @param accountBalance - desired account balance
	 */
	public void setAccountBalance(double accountBalance) {
		this.accountBalance = accountBalance;
	}
	
	/**
	 * This method is used to withdraw an input and amount will be 
	 * subtracted from current account
	 * @param amount - amount to be withdrawn
	 * @return current balance of account after withdrawal
	 */
	public double withdrawal(double amount) {
		if(amount > getAccountBalance()) {
			System.out.println("Insufficient funds!");
		}
		else if(amount < 0) {
			System.out.println("Invalid amount!");
		}
		else {
			setAccountBalance(getAccountBalance() - amount);
			System.out.println("Withdrawal " + amount + " - your new balance is: " + getAccountBalance());
		}
		return getAccountBalance();
	}
	/**
	 * This method is used to deposit an input and amount will be added
	 * to current account
	 * @param amount - amount to be deposited
	 * @return account balance after deposit
	 */
	public double deposit(double amount) {
		if(amount < 0)
			System.out.println("Invalid amount!");
		else {
			setAccountBalance(getAccountBalance() + amount);
			System.out.println("Deposit " + amount + " - your new balance is: " + getAccountBalance());
		}
		return getAccountBalance();
	}
	/**
	 * This method is used to transfer desired amount to another account
	 * from the same customer
	 * @param acct - current accessed account
	 * @param acctRec - account receiving amount
	 * @param amount - amount to be transferred
	 * @return boolean if transaction was a success
	 */
	public static boolean transferAmount(Account acct, Account acctRec, double amount) {
		
		if (acctRec instanceof Credit) {
			if (amount > acctRec.getAccountBalance())
				System.out.println("Amount being transferred is greater than balance");
				return false;
		}
		acct.setAccountBalance(acct.getAccountBalance() - amount);
		acctRec.setAccountBalance(acctRec.getAccountBalance() + amount);
		System.out.println("Transferred from " + acct.getAccountNumber() + " to " + acctRec.getAccountNumber() + " Amount: " + amount);
		return true;		
	}
	
	/**
	 * This method is implements a transfer of funds from account to
	 * another customer's checking account.
	 * @param acct - account being accessed
	 * @param custList - list of customer objects
	 * @param amount - amount to be paid
	 * @return string used for logging
	 */
	public String paySomeone2(Account acct, List<Customer> custList, double amount) {
		Scanner sc = new Scanner(System.in); 
		System.out.println("Enter recipient's account number: ");
		int rec = sc.nextInt();
		// check if account number entered is current user's acccount
		if(rec == acct.getAccountNumber()) {
			System.out.println("You entered your account number");
			return "INVALID RECIPIENT";
		}
		
		// iterate through list of accounts to match recipient account number
		for(Customer acctRec : custList) {
			if(acctRec.getChecking().getAccountNumber() == rec) {
				// check if account number is correct
				if (acctRec.getChecking().getAccountNumber() != rec) {
					System.out.println("Recipient not found!");
					return "RECIPIENT NOT FOUND";
				}
				// otherwise, proceed with the transaction, print and log.
				acct.setAccountBalance(acct.getAccountBalance() - amount);
				acctRec.getChecking().setAccountBalance(acctRec.getChecking().getAccountBalance() + amount);
				System.out.println("Your new balance is: " + acct.getAccountBalance());
				System.out.println(amount +  " to " + acctRec.getFirstName() + " " + acctRec.getLastName()
				+ " - " + acctRec.getChecking().getAccountNumber());
				return "Transfer amount of " + amount +  " to " + acctRec.getFirstName() + " " + acctRec.getLastName()
				+ " - " + acctRec.getChecking().getAccountNumber();
			}
		}
		return null;
	}
	
	public String paySomeone(Account acct, Account acctRec, double amount) {
		
		acct.setAccountBalance(acct.getAccountBalance() - amount);
		acctRec.setAccountBalance(acctRec.getAccountBalance() + amount);
		
		System.out.println("Your new balance is: " + acct.getAccountBalance());
		System.out.println(amount +  " to " + acctRec.getFirstName() + " " + acctRec.getLastName());
		
		return "Transfer amount of " + amount +  " to " + acctRec.getFirstName() + " " + acctRec.getLastName()
		+ " - " + acctRec.getAccountNumber();
	}

	public static void printData(Account acct) {
		System.out.println("Account Number: " + acct.getAccountNumber());
		System.out.println("Account Balance: " + acct.getAccountBalance());
	}

	public static void printHidden(Account acct) {
		System.out.println("Account Number: " + acct.getAccountNumber());
	}

	/**
	 * From Laurence
	 * Modified by Alfonso
	 * This is used to write transaction to a LOG FILE
	 * This method is used to write customer list to a CSV
	 * @param option - option selected by user from UserInt
	 * @param newAcct - account that is being accessed
	 * @param logMessage - log message depending on user selection
	 * @param amountTemp - log amount depending on user selection
	 * @param logMessage2 - another log message used for logging
	 * @exception
	 */
	public void printTransaction(int option, Account newAcct, String logMessage, double amountTemp, String logMessage2) throws IOException {
		Date date = new Date();
		try {
			FileWriter myWriter = new FileWriter("./PA4/src/prjBank/bankLog.txt",true);
			// check if transaction was NOT a transfer AND there was a change in balance
			if((option <= 3) && (amountTemp != newAcct.getAccountBalance())) {
				myWriter.write("(" + date.toString() + ") ACCOUNT NUMBER: " + newAcct.getAccountNumber() + " - " + newAcct.getFirstName() + " "
						+ newAcct.getLastName() + ":\n" + logMessage + " " + logMessage2 + ", NEW BALANCE: " + newAcct.getAccountBalance() + "\n");
				myWriter.write("\n");
				myWriter.close();
			}
			if((option == 4) && (amountTemp != newAcct.getAccountBalance())) {
				myWriter.write("(" + date.toString() + ") ACCOUNT NUMBER: " + newAcct.getAccountNumber() + " - " + newAcct.getFirstName() + " "
						+ newAcct.getLastName() + ":\n" + logMessage + " AMOUNT:  " + logMessage2 + ", NEW BALANCE: " + newAcct.getAccountBalance() + "\n");
				myWriter.write("\n");
				myWriter.close();
			}
			// check if transaction was paySomeone AND there was a change in balance
			else if((option == 5) && (amountTemp != newAcct.getAccountBalance())) {
				myWriter.write("(" + date.toString() + ") ACCOUNT NUMBER: " + newAcct.getAccountNumber() + " - " + newAcct.getFirstName() + " "
						+ newAcct.getLastName() + ":\n" + logMessage + ", NEW BALANCE: " + newAcct.getAccountBalance() + "\n");
				myWriter.write("\n");
				myWriter.close();
			}

			// check if transaction was not successful
			else if (amountTemp == newAcct.getAccountBalance()) {
				myWriter.write("(" + date.toString() + ") ACCOUNT NUMBER: " + newAcct.getAccountNumber() + " - " + newAcct.getFirstName() + " "
						+ newAcct.getLastName() + ":\nTRANSACTION NOT COMPLETE! " + logMessage2 + "\n");
				myWriter.write("\n");
				myWriter.close();
			}

		}
		catch (IOException e) {
		}

	}
}
	

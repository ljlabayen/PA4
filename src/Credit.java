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

public class Credit extends Account{
	private double creditMax;
	/**
	 * Constructor for Credit Object
	 * @param firstName - First name
	 * @param lastName - Last name
	 * @param accountNumber - Account Number
	 * @param accountBalance - Account Balance
	 * @param creditMaxIn - credit limit
	 */
	public Credit(String firstName, String lastName, int accountNumber, double accountBalance, double creditMaxIn) {
		super(firstName, lastName, accountNumber, accountBalance);
		creditMax = creditMaxIn;
	}
	/**
	 * This method is used to get credit max
	 * @return credit max
	 */
	public double getCreditMax() {
		return creditMax;
	}
	/**
	 * This method is used to set credit max
	 * @param creditMax - credit limit
	 */
	public void setCreditMax(double creditMax) {
		this.creditMax = creditMax;
	}
	
}
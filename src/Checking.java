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

public class Checking extends Account {
	
	/**
	 * Constructor for Checking Object
	 * @param firstName - First name
	 * @param lastName - Last name
	 * @param accountNumber - Account Number
	 * @param accountBalance - Account Balance
	 */
	public Checking(String firstName, String lastName, int accountNumber, double accountBalance) {
		super(firstName, lastName, accountNumber, accountBalance);
		
	}

}

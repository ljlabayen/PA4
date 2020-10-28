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
package prjBank;

import java.util.ArrayList;
import java.util.List;

public class Customer extends Person{
	
	private Checking checking;
	private Savings savings;
	private Credit credit;
	private List<BankStatement> transList = new ArrayList<BankStatement>();

	/**
	 * Constructor for Customer Object
	 * @param firstName - First name
	 * @param lastName - Last name
	 * @param dateOfBirth - Date of birth
	 * @param identNum - ID number
	 * @param address - Address of customer
	 * @param phoneNum - Phone number
	 * @param checking - Checking account object
	 * @param savings - Savings account object
	 * @param credit - Credit account object

	 */
	public Customer(String firstName, String lastName, String dateOfBirth, int identNum, String address,
			String phoneNum, Checking checking, Savings savings, Credit credit) {
		super(firstName, lastName, dateOfBirth, identNum, address, phoneNum);
		this.checking = checking;
		this.savings = savings;
		this.credit = credit;
	}

	/**
	 * This method is used to get checking object
	 * @return checking object
	 */
	public Checking getChecking() {
		return checking;
	}

	/**
	 * This method is used to set checking object
	 * @param checking - checking object
	 */
	public void setChecking(Checking checking) {
		this.checking = checking;
	}

	/**
	 * This method is used to get savings object
	 * @return savings object
	 */
	public Savings getSavings() {
		return savings;
	}

	/**
	 * This method is used to set savings object
	 * @param savings - savings object
	 */
	public void setSavings(Savings savings) {
		this.savings = savings;
	}

	/**
	 * This method is used to get credit object
	 * @return credit object
	 */
	public Credit getCredit() {
		return credit;
	}

	/**
	 * This method is used to set credit object
	 * @param credit - credit object
	 */
	public void setCredit(Credit credit) {
		this.credit = credit;
	}
	/**
	 * This method is used to get list of bank statements
	 * @return list of transactions
	 */
	public List<BankStatement> getTransList() {
		return transList;
	}
	/**
	 * This method is used to add bank statements to
	 * transaction list
	 * @param bankStatement - bank statement holding
	 * transaction information
	 */
	public void appendTransList(BankStatement bankStatement) {
		transList.add(bankStatement);
	}
	

}
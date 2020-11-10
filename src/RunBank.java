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

import java.io.BufferedReader;
import java.io.InputStreamReader; 
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class RunBank {
	
	/**
	 * Main method to start program
	 * Taken from Laurence
	 */
	public static void main(String args[]) throws IOException {
		Scanner sc = new Scanner(System.in);
		//Fixed pathfile
		String file = "./PA4/src/CS 3331 - Bank Users 4.csv";
		List<Customer> custList = readCSV(file);
		//List<BankStatement> bsList = new ArrayList<BankStatement>();
		System.out.println("Welcome to the Bank of Miners!\n");
		while(true) {
			System.out.println("1. Customer");
			System.out.println("2. New Account");
			System.out.println("3. Bank Manager");
			System.out.println("4. Transaction Reader");
			System.out.println("5. Exit Bank");
			
			int option = sc.nextInt();
			switch(option) {
			case 1:
				userInt(custList);
				break;
			case 2:
				custList = createNewUser(custList);
				break;
			case 3:
				bankManager(custList);
				break;
			case 4:
				transactionReader(custList);
				break;
			case 5:
				System.exit(0);
			}
		}
	}
	/**
	 * Taken from Laurence
	 * This method is used to login with a given string
	 * @param firstNameIn - Name of customer logging in
	 * @param custList -  Customer list to search for name
	 * @return Account object of name given with logIn param
	 */
	public static Customer logIn(String firstNameIn, String lastNameIn, List<Customer> custList) {
		for (Customer acct : custList) {
			if (acct.getFirstName().equalsIgnoreCase(firstNameIn) && 
					acct.getLastName().equalsIgnoreCase(lastNameIn)) {
				return acct;
				}
			}
		return null;
	}
	/**
	 * Taken from Laurence
	 * Modified by Alfonso
	 * This method is used for the bank user interface. It will check
	 * customer credentials. If successful, will give the customer and
	 * option to select which account to access and interact with. After
	 * selecting account, options to withdraw, deposit, transfer, and pay
	 * someone is printed on screen. All transactions are logged
	 * @param custList list of customers
	 * @throws IOException 
	 */
	public static void userInt(List<Customer> custList) throws IOException {
                                             		Scanner sc = new Scanner(System.in);
		BufferedReader inp = new BufferedReader (new InputStreamReader(System.in));
		int option1 = 0;
		String logMessage = "";
		String recipient = "";
		double balanceTemp = -9999;
		System.out.println("Enter your first name: ");
		String firstNameIn = sc.nextLine();
		System.out.println("Enter your last name: ");
		String lastNameIn = sc.nextLine();
		Account acct = null;
		System.out.println("Welcome " + firstNameIn + " " + lastNameIn + "\nChecking your credentials...");
		Customer customer = logIn(firstNameIn, lastNameIn, custList);
		String curAcct = "";


		if(customer == null) {
			System.out.println("Name not found! Exiting...");
			return;
		}

		System.out.println("Enter password: ");
		String password = sc.nextLine();

		if(!(customer.getPassword().equals(password))) {
			System.out.println("Invalid password! Exiting...");
			return;
		}
		
		while(true) {
			System.out.println("\nChoose Account: ");
			System.out.println("1. Checking");
			System.out.println("2. Savings ");
			System.out.println("3. Credit ");
			System.out.println("4. Main Menu");
			System.out.println("5. Exit Bank");
			option1 = sc.nextInt();
			switch(option1) {
			case 1:
				if (customer.getChecking() != null) {
					balanceTemp = customer.getChecking().getAccountBalance();
					acct = customer.getChecking();
					System.out.println("Checking account number: " + acct.getAccountNumber());
					curAcct = "CHECKING";
					break;
				}
				System.out.println("Checking account does not exist, returning to main menu");
				return;
			case 2:
				balanceTemp = customer.getSavings().getAccountBalance();
				acct = customer.getSavings();
				System.out.println("Savings account number: " + acct.getAccountNumber());
				curAcct = "SAVINGS";
				break;
			case 3:
				if (customer.getCredit() != null) {
					balanceTemp = customer.getCredit().getAccountBalance();
					acct = customer.getCredit();
					System.out.println("Credit account number: " + acct.getAccountNumber());
					curAcct = "CREDIT";
					break;
				}
				System.out.println("Credit account does not exist, returning to main menu");
				return;
			case 4:
				return;
			case 5:
				System.exit(0);
			}
			//acct.getAccountNumber();
			if(option1 > 0) {
				boolean inMenu = true;
				while(inMenu) {
					System.out.println("1. Inquire Balance");
					System.out.println("2. Withdraw");
					System.out.println("3. Deposit ");
					System.out.println("4. Transfer Between Accounts");
					System.out.println("5. Pay Someone");
					System.out.println("6. Main Menu");
					int option2 = sc.nextInt();
					double prevBalance = acct.getAccountBalance();
					Date date = new Date();
					switch (option2) {
						case 1:
							System.out.println(acct.getAccountNumber() + " - Balance: " + acct.getAccountBalance());
							acct.printTransaction(option2, acct, logMessage, balanceTemp, recipient);
							break;
						case 2:

							System.out.println("Enter amount to be widthrawn: ");
							double withdrawAmount = sc.nextDouble();
							System.out.println(acct.getAccountNumber() + " - Balance: " + acct.getAccountBalance());
							acct.withdrawal(withdrawAmount);
							System.out.println(acct.getAccountNumber() + " - New balance: " + acct.getAccountBalance() + "\n");

							customer.printTransaction(new BankStatement(date.toString(), "withdraws", curAcct, withdrawAmount, prevBalance, acct.getAccountBalance()));
							toCSV(custList);
							acct.printTransaction(option2, acct, "Withdraws", balanceTemp, Double.toString(withdrawAmount));
							break;
						case 3:
							System.out.println("Enter amount to be deposited: ");
							double depositAmount = sc.nextDouble();
							double tempBal = Math.abs(acct.getAccountBalance());
							System.out.println(acct.getAccountNumber() + " - Balance: " + acct.getAccountBalance());
							acct.deposit(depositAmount);
							if (acct instanceof Credit) {
								if (depositAmount > tempBal) {
									System.out.println("Deposit amount is more than balance");
									acct.setAccountBalance(acct.getAccountBalance() - depositAmount);
								}
							}
							System.out.println(acct.getAccountNumber() + " - New balance: " + acct.getAccountBalance() + "\n");
							customer.printTransaction(new BankStatement(date.toString(), "deposits", curAcct, depositAmount, prevBalance, acct.getAccountBalance()));
							toCSV(custList);
							acct.printTransaction(option1, acct, "Deposits", balanceTemp, Double.toString(depositAmount));
							break;
						case 4: //transfer between accounts
							System.out.println("Enter Amount to be transferred");
							double amount = sc.nextDouble();
							Account accountType = transferUI(acct, customer, amount);
							customer.printTransaction(new BankStatement(date.toString(), "transfers", curAcct, amount, prevBalance, acct.getAccountBalance()));

							if (accountType instanceof Checking)
								acct.printTransaction(option2, acct, "Transfers to Checking", balanceTemp, recipient);
							else if (accountType instanceof Savings)
								acct.printTransaction(option2, acct, "Transfers to Savings", balanceTemp, recipient);
							else if (accountType instanceof Credit)
								acct.printTransaction(option2, acct, "Transfers to Credit", balanceTemp, recipient);
							break;
						case 5:
							// prompt user for amount
							System.out.println("Enter amount to be transferred: ");
							double transferAmount = sc.nextDouble();

							// check if amount entered is available in account
							if (transferAmount > acct.getAccountBalance()) {
								System.out.println("Insufficient funds!");
								return;
							}
							// check for negative number input
							else if (transferAmount < 0) {
								System.out.println("Invalid amount!");
								return;
							}
							System.out.println("Enter recipient's first name: ");
							String recFirstNameIn = inp.readLine();
							System.out.println("Enter recipient's last name: ");
							String recLastNameIn = inp.readLine();
							Customer rec = logIn(recFirstNameIn, recLastNameIn, custList);

							if (rec == null) {
								System.out.println("Name not found!");
								return;
							}

							System.out.println("Choose recipient account: ");
							System.out.println("1. Checking ");
							System.out.println("2. Savings ");
							System.out.println("3. Credit ");

							int paysOpt = sc.nextInt();
							Account acctRec = paySomeoneHelper(paysOpt, rec);
							System.out.println("Transferring to: ");
							rec.printDataHidden(); //PA4 requirement
							recipient = acct.paySomeone(acct, acctRec, transferAmount);
							toCSV(custList);
							customer.printTransaction(new BankStatement(date.toString(), "pays", curAcct, transferAmount, prevBalance, acct.getAccountBalance()));
							acct.printTransaction(option2, acct, recipient, balanceTemp, " TO CHECKING ");

							break;
						case 6:
							inMenu = false;
							return;
						case 7:
							System.exit(0);
					}
				}
			}
		return;
		}
	}

	/**
	 * This method aids the process of paying another bank user
	 * @param paysOpt This variable dictates which account is receiving the payment
	 * @param rec this returns the account that is receiving a payment
	 * @return
	 */
	public static Account paySomeoneHelper(int paysOpt, Customer rec) {
		Account acctRec = null;
		switch(paysOpt) {
		case 1:
			if (rec.getChecking() != null) {
				acctRec = rec.getChecking();
				return acctRec;
			}
			System.out.println("Account does not exist");
			return null;
		case 2:
			if (rec.getSavings() != null) {
				acctRec = rec.getSavings();
				return acctRec;
			}
			System.out.println("Account does not exist");
			return null;
		case 3:
			if (rec.getCredit() != null) {
				acctRec = rec.getCredit();
				return acctRec;
			}
			System.out.println("Account does not exist");
			return null;
		}
		return null;
	}
	/**
	 * Taken from Laurence
	 * This method is used to modularize UI for transfer
	 * between accounts
	 * @param acct - Current account selected from previous menu
	 * @param customer - Main account that holds customer data
	 * @param amount - Amount to be transferred to receiving account
	 * @return Account type selected from this method
	 */
	public static Account transferUI(Account acct, Customer customer, double amount) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Choose receiving Account: ");
		System.out.println("1. Checking: ");
		System.out.println("2. Savings: ");
		System.out.println("3. Credit: ");
		int option = sc.nextInt();
		switch(option) {
			case 1:
				if (acct instanceof Checking) {
					System.out.println("Transfering to Same account. Going back to main menu");
					break;
				}
				Account.transferAmount(acct, customer.getChecking(), amount);
				System.out.println("Transferred " + amount + " to Checking. Your new balance is: " + acct.getAccountBalance());
				return customer.getChecking();
			case 2:
				if (acct instanceof Savings) {
					System.out.println("Transfering to Same account. Going back to main menu");
				}
				Account.transferAmount(acct, customer.getSavings(), amount);
				System.out.println("Transferred " + amount + " to Savings. Your new balance is: " + acct.getAccountBalance());
				return customer.getSavings();
			case 3:
				if (acct instanceof Credit) {
					System.out.println("Transfering to Same account. Going back to main menu");
				}
				Account.transferAmount(acct, customer.getCredit(), amount);
				System.out.println("Transferred " + amount + " to Credit. Your new balance is: " + acct.getAccountBalance());
				return customer.getChecking();
			default:
				System.out.println("Unknown Command. Returning to main menu.");
		}
		return null;
	}
	/**
	 * This from Laurence
	 * This method is used to read a CSV and convert to a list
	 * @param fileName - File name String
	 * @return List of customers
	 * @exception IOException throw an exception if file name is incorrect
	 */
	public static List<Customer> readCSV(String fileName) throws IOException {
		//FileWriter myWriter = new FileWriter("./PA4/src/bankLog.txt",true);
		List<Customer> custList = new ArrayList<Customer>();
		try(BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			String line = "";
			String[] header = br.readLine().split(",");
			
			int id=-1, ln=-1, fn=-1, ad=-1, pn=-1, dob=-1, chan=-1, san=-1, cran=-1, chsb=-1, ssb=-1, crsb=-1, cm=-1, pwd=-1, em=-1;
			
			for (int i = 0; i<header.length; i++)
				if(header[i].contains("Identification Number")){ id = i; }
				else if(header[i].contains("Last Name")) { ln = i; }
				else if(header[i].contains("First Name")) { fn = i; }
				else if(header[i].contains("Address")) { ad = i; }
				else if(header[i].contains("Phone Number")) { pn = i; }
				else if(header[i].contains("Date of Birth")) { dob = i; }
				else if(header[i].contains("Checking Account Number")) { chan = i; }
				else if(header[i].contains("Savings Account Number")) { san = i; }
				else if(header[i].contains("Credit Account Number")) { cran = i; }
				else if(header[i].contains("Checking Starting Balance")) { chsb = i; }
				else if(header[i].contains("Savings Starting Balance")) { ssb = i; }
				else if(header[i].contains("Credit Starting Balance")) { crsb = i; }
				else if(header[i].contains("Credit Max")) { cm = i; }
				else if(header[i].contains("Password")) { pwd = i; }
				else if(header[i].contains("Email")) { em = i; }
			
			while ((line = br.readLine()) != null) {
				String[] fields = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
				String firstName = fields[fn];
				String lastName = fields[ln];
				String dateOfBirth = fields[dob];
				int identNum = Integer.parseInt(fields[id]);
				String address = fields[ad];
				String phoneNum = fields[pn];
				int checkingNum = Integer.parseInt(fields[chan]);
				int savingsNum = Integer.parseInt(fields[san]);
				int creditNum = Integer.parseInt(fields[cran]);
				double checkingBal = Double.parseDouble(fields[chsb]);
				double savingsBal = Double.parseDouble(fields[ssb]);
				double creditBal = Double.parseDouble(fields[crsb]);
				double creditMax = Double.parseDouble(fields[cm]);
				String email = fields[em];
				String password = fields[pwd];

				Checking newCheckingAcct = new Checking(firstName, lastName, checkingNum, checkingBal);
				Savings newSavingsAcct = new Savings(firstName, lastName, savingsNum, savingsBal);
				Credit newCreditAcct = new Credit(firstName, lastName, creditNum, creditBal, creditMax);

				Customer newCust = new Customer(firstName, lastName, dateOfBirth, identNum, address, phoneNum, email, password, newCheckingAcct, newSavingsAcct, newCreditAcct);
				newCust.getCredit().setCreditMax(creditMax);
				custList.add(newCust);	

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return custList;
	}
	/**
	 * This was taken from Laurence
	 * This method is used to write customer list to a CSV
	 * @param custList - List of customers
	 * @exception
	 */
	public static void toCSV(List<Customer> custList) throws IOException {
		
		String file = "./PA4/src/updatedCSV.csv";
		try {
			FileWriter writer = new FileWriter(file);
			writer.append("First Name,Last Name,Date of Birth,IdentificationNumber,Address,"
					+ "Phone Number,Email,Password,Checking Account Number,Savings Account Number,Credit Account Number"
					+ ",Checking Starting Balance,Savings Starting Balance,Credit Starting Balance, Credit Max\n");
			for (Customer cust : custList)	{
				List<String> list = new ArrayList<String>();
				String result = "";
				list.add(cust.getFirstName());
				list.add(cust.getLastName());
				list.add(cust.getDateOfBirth());
				list.add(Integer.toString(cust.getIdentNum()));
				list.add(cust.getAddress()); // quotes already added
				list.add(cust.getPhoneNum());
				list.add(cust.getEmail());
				list.add(cust.getPassword());
				String chNum = (cust.getChecking() != null) ? Integer.toString(cust.getChecking().getAccountNumber()) : "";
				list.add(chNum);
				list.add(Integer.toString(cust.getSavings().getAccountNumber()));
				
				String crNum = (cust.getCredit() != null) ? Integer.toString(cust.getCredit().getAccountNumber()) : "";
				list.add(crNum);
				
				String chBal = (cust.getChecking() != null) ? Double.toString(cust.getChecking().getAccountBalance()) : "";
				list.add(chBal);
				list.add(Double.toString(cust.getSavings().getAccountBalance()));
				
				String crBal = (cust.getCredit() != null) ? Double.toString(cust.getCredit().getAccountBalance()) : "";
				list.add(crBal);
				String crMax = (cust.getCredit() != null) ? Double.toString(cust.getCredit().getCreditMax()) : "";
				list.add(crMax);
				result = String.join(",", list);
				writer.append(result);
				writer.append('\n');
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
	/*
	 * This method is used to write customer list to a CSV
	 * @param option - option selected by user from UserInt
	 * @param newAcct - account that is being accessed
	 * @param logMessage - log message depending on user selection
	 * @param amountTemp - log amount depending on user selection
	 * @param logMessage2 - another log message used for logging
	 * @exception
	 */
//	public static void toLog(int option, Account newAcct, String logMessage, double amountTemp, String logMessage2) throws IOException {
//		Date date = new Date();
//		try {
//			FileWriter myWriter = new FileWriter("./src/prjBank/bankLog.txt",true);
//			// check if transaction was NOT a transfer AND there was a change in balance
//			if((option <= 3) && (amountTemp != newAcct.getAccountBalance())) {
//				myWriter.write("(" + date.toString() + ") ACCOUNT NUMBER: " + newAcct.getAccountNumber() + " - " + newAcct.getFirstName() + " "
//						+ newAcct.getLastName() + ":\n" + logMessage + " " + logMessage2 + ", NEW BALANCE: " + newAcct.getAccountBalance() + "\n");
//				myWriter.write("\n");
//				myWriter.close();
//			}
//			if((option == 4) && (amountTemp != newAcct.getAccountBalance())) {
//				myWriter.write("(" + date.toString() + ") ACCOUNT NUMBER: " + newAcct.getAccountNumber() + " - " + newAcct.getFirstName() + " "
//						+ newAcct.getLastName() + ":\n" + logMessage + " AMOUNT:  " + logMessage2 + ", NEW BALANCE: " + newAcct.getAccountBalance() + "\n");
//				myWriter.write("\n");
//				myWriter.close();
//			}
//			// check if transaction was paySomeone AND there was a change in balance
//			else if((option == 5) && (amountTemp != newAcct.getAccountBalance())) {
//				myWriter.write("(" + date.toString() + ") ACCOUNT NUMBER: " + newAcct.getAccountNumber() + " - " + newAcct.getFirstName() + " "
//						+ newAcct.getLastName() + ":\n" + logMessage + ", NEW BALANCE: " + newAcct.getAccountBalance() + "\n");
//				myWriter.write("\n");
//				myWriter.close();
//			}
//
//			// check if transaction was not successful
//			else if (amountTemp == newAcct.getAccountBalance()) {
//				myWriter.write("(" + date.toString() + ") ACCOUNT NUMBER: " + newAcct.getAccountNumber() + " - " + newAcct.getFirstName() + " "
//						+ newAcct.getLastName() + ":\nTRANSACTION NOT COMPLETE! " + logMessage2 + "\n");
//				myWriter.write("\n");
//				myWriter.close();
//			}
//
//		}
//		catch (IOException e) {
//		}
//
//	}

	/**
	 * Taken from Laurence
	 * Modified by Alfonso
	 * This method is used for bank manager inquiries
	 * @param custList - List of customers
	 * @exception
	 */
	public static void bankManager(List<Customer> custList) throws IOException {
		Scanner sc = new Scanner(System.in); 
		BufferedReader inp = new BufferedReader (new InputStreamReader(System.in));
		boolean inMenu = true;
		while(inMenu) {  //taken from alfonso
			System.out.println("Welcome Bank Manager!\n");
			System.out.println("1. Inquire account by name");
			System.out.println("2. Inquire account by type/number");
			System.out.println("3. Print all customers");
			System.out.println("4. Write bank statement by name");
			System.out.println("5. Main Menu");
			System.out.println("6. Exit");
			int option = sc.nextInt();
			sc.nextLine();
			switch (option) {
				case 1:
					System.out.println("Enter first name of customer: ");
					String firstNameIn = sc.nextLine();
					System.out.println("Enter last name of customer: ");
					String lastNameIn = sc.nextLine();
					Customer customer = logIn(firstNameIn, lastNameIn, custList);

					if (customer == null) {
						System.out.println("Name not found! Going back!");
						return;
					}
					customer.printData();

					break;
				case 2:
					System.out.println("1. Checking");
					System.out.println("2. Savings");
					System.out.println("3. Credit");
					int option2 = sc.nextInt();
					System.out.println("Enter Account Number: ");
					int acctNum = sc.nextInt();
					switch (option2) {
						case 1:
							for (Customer acct : custList) {
								if (acct.getChecking().getAccountNumber() == acctNum) {
									acct.getChecking().printAccountType();
									acct.getChecking().printData();
									break;
								}
							}
							System.out.println("No match found in checking!");
							break;
						case 2:
							for (Customer acct : custList) {
								if (acctNum == acct.getSavings().getAccountNumber()) {
									acct.getSavings().printAccountType();
									acct.getSavings().printData();
									break;
								}
							}
							System.out.println("No match found in savings!");
							break;
						case 3:
							for (Customer acct : custList) {
								if (acctNum == acct.getCredit().getAccountNumber()) {
									acct.getCredit().printAccountType();
									acct.getCredit().printData();
									break;
								}
							}
							System.out.println("No match found in credit!");
							break;
					}
					break;
				case 3:
					for (Customer acct : custList) {
						acct.printData();
					}
					break;
				case 4:

					System.out.println("Enter first name of customer: ");
					firstNameIn = inp.readLine();
					System.out.println("Enter last name of customer: ");
					lastNameIn = inp.readLine();
					Customer cust = logIn(firstNameIn, lastNameIn, custList);

					if (cust == null) {
						System.out.println("Name not found! Going back!");
						return;
					}

					String file = "./PA4/src/BankStatements/" + cust.getFirstName() + "_" + cust.getLastName() + "_BankStatement.txt";
					try {
						FileWriter writer = new FileWriter(file);
						writer.append("Name: " + cust.getFirstName() + " " + cust.getLastName());
						writer.append("\n Identification #" + cust.getIdentNum());
						writer.append("\n Date of Birth: " + cust.getDateOfBirth());
						writer.append("\n Address: " + cust.getAddress());
						writer.append("\n Phone Number: " + cust.getPhoneNum());
						writer.append("\n Email: " + cust.getEmail());
						writer.append("\n--------------------------------------------------------------\n");

						writer.append("\n Savings Account #" + cust.getSavings().getAccountNumber());
						writer.append(" Balance: " + cust.getSavings().getAccountBalance());
						if (cust.getChecking() != null) {
							writer.append("\n Checking Account #" + cust.getChecking().getAccountNumber());
							writer.append(" Balance: " + cust.getChecking().getAccountBalance());
						}
						if (cust.getCredit() != null) {
							writer.append("\n Credit Account: #" + cust.getCredit().getAccountNumber());
							writer.append(" Balance: " + cust.getCredit().getAccountBalance());
						}
						writer.append("\r\n");
						writer.append("TRANSACTION HISTORY: ");
						writer.append("\n==============================================================\n");
						if (cust.getTransList() != null) {
							writer.append("\n" + printTrans(cust.getTransList()));
						}

						System.out.println("Written to " + cust.getFirstName() + "_" + cust.getLastName() + "_BankStatement.txt\n");
						writer.close();
						return;
					} catch (IOException e) {
						e.printStackTrace();
					}
					return;
				case 5:
					inMenu = false;
					System.out.println("Goodbye!");
					return;
				case 6:
					System.exit(0);
			}
		}
		return;
	}
	/**
	 * Taken from Laurence
	 * This method is used to print data of a given account
	 * @param transList - List of bank statements for a customer
	 * @return string to be used for writing bank statements
	 */
	public static String printTrans(List<BankStatement> transList) {
		Date date = new Date();
		String print = "";
		for (BankStatement trans : transList) {
			
			//test only
			System.out.println("(" + date.toString() + ") " + trans.getAccount() + " PREVIOUS BALANCE: " + trans.getPrevBalance() 
			+ " TYPE: " + trans.getTransType() + " AMOUNT: " + trans.getAmount() + " NEW BALANCE: " + trans.getNewBalance() + "\n");
			
			print +=  "(" + date.toString() + ") " + trans.getAccount() + " PREVIOUS BALANCE: " + trans.getPrevBalance() 
			+ " TYPE: " + trans.getTransType() + " AMOUNT: " + trans.getAmount() +  " NEW BALANCE: " + trans.getNewBalance() + "\n";
		}
		return print;
	}

	/*
	 * This method is used to print data of a given account
	 * @param acct - account used for printing

	public static void printData(Customer acct) {
		System.out.println("Name: " + acct.getFirstName() + " " + acct.getLastName());
		System.out.println("Date of Birth: " + acct.getDateOfBirth());
		System.out.println("Address: " + acct.getAddress());
		System.out.println("ID: " + acct.getIdentNum());
		System.out.println("Phone number: " + acct.getPhoneNum());
		System.out.println("Email: " + acct.getEmail());
		if(acct.getChecking() != null) { System.out.println("Checking Account #" + acct.getChecking().getAccountNumber() + " - Balance: " + acct.getChecking().getAccountBalance()); }
		System.out.println("Savings Account #" + acct.getSavings().getAccountNumber() + " - Balance: " + acct.getSavings().getAccountBalance());
		if(acct.getCredit() != null) { System.out.println("Credit Account #" + acct.getCredit().getAccountNumber() + " - Balance: " + acct.getCredit().getAccountBalance()); }
		System.out.println("\n");
	}
	*/

	/**
	 * Taken from Laurence
	 * This way of reading the transaction file matches the transaction reader from alfonso
	 * This to read a CSV to simulate multiple users accessing
	 * the bank and their accounts
	 * @param custList - list of customer objects
	 */
	public static void transactionReader(List<Customer> custList) throws IOException {
		
		
		// fix hard code
		try(BufferedReader br = new BufferedReader(new FileReader("./PA4/src/Transaction Actions.csv"))) {
			String line = "";
			// used to skip header
			br.readLine();
			
			
			while ((line = br.readLine()) != null) {
				ArrayList<BankStatement> transList = new ArrayList<BankStatement>();
				Date date = new Date();
				int logOpt = 0;
				Account logAcct = null;
				line = line.replaceAll("(?<=,)(?=,)"," ");
				String filler = " ";
				if(line.substring(line.length()-1).contains(",")) { line = line + 0; }
				if(line.substring(0,1).contains(",")) { line = filler + line; }
				//System.out.println(line);
				String[] fields = line.split(",");
				String fromFirstName = fields[0];
				String fromLastName = fields[1];
				String fromWhere = fields[2];
				String action = fields[3];
				String toFirstName = fields[4];
				String toLastName = fields[5];
				String toWhere = fields[6];
				double actionAmount  = Double.parseDouble(fields[7]);
				
				if (action.contains("inquires")) {
					Customer cust = logIn(fromFirstName, fromLastName, custList);
					logOpt = 1;
					if(fromWhere.contains("Checking")) { System.out.println("Checking balance is: " + cust.getChecking().getAccountBalance()); }
					else if(fromWhere.contains("Savings")) { System.out.println("Savings balance is: " + cust.getSavings().getAccountBalance()); }
					else if(fromWhere.contains("Credit")) { System.out.println("Credit balance is: " + cust.getCredit().getAccountBalance()); }
					System.out.println("\n");
				}
				
				else if(action.contains("withdraws")) {
					Customer cust = logIn(fromFirstName, fromLastName, custList);
					double prevBalance = 0;
					double curBalance = 0;
					logOpt = 2;
					if(fromWhere.contains("Checking")) { 
						logAcct = cust.getChecking();
						prevBalance = cust.getChecking().getAccountBalance();
						cust.getChecking().withdrawal(actionAmount); 
						curBalance = cust.getChecking().getAccountBalance();
					}
					else if(fromWhere.contains("Savings")) {
						logAcct = cust.getSavings();
						prevBalance = cust.getChecking().getAccountBalance();
						cust.getSavings().withdrawal(actionAmount);
						curBalance = cust.getChecking().getAccountBalance();
						}
					else if(fromWhere.contains("Credit")) { 
						logAcct = cust.getCredit();
						prevBalance = cust.getChecking().getAccountBalance();
						cust.getChecking().withdrawal(actionAmount);
						curBalance = cust.getChecking().getAccountBalance();
						}
					System.out.println("\n");
					cust.printTransaction(new BankStatement(date.toString(), action, fromWhere, actionAmount, prevBalance, curBalance));
					toCSV(custList);
					logAcct.printTransaction(logOpt, logAcct, action, prevBalance, Double.toString(curBalance));
				}
				else if(action.contains("deposits")) {
					Customer cust = logIn(toFirstName, toLastName, custList);
					double prevBalance = 0;
					double curBalance = 0;
					logOpt = 3;
					if(toWhere.contains("Checking")) { 
						logAcct = cust.getChecking();
						prevBalance = cust.getChecking().getAccountBalance();
						cust.getChecking().deposit(actionAmount); 
						curBalance = cust.getChecking().getAccountBalance();
					}
					else if(toWhere.contains("Savings")) {
						logAcct = cust.getSavings();
						prevBalance = cust.getChecking().getAccountBalance();
						cust.getSavings().deposit(actionAmount);
						curBalance = cust.getChecking().getAccountBalance();
						}
					else if(toWhere.contains("Credit")) { 
						logAcct = cust.getCredit();
						prevBalance = cust.getChecking().getAccountBalance();
						cust.getChecking().deposit(actionAmount);
						curBalance = cust.getChecking().getAccountBalance();
						}
					System.out.println("\n");
					
					cust.printTransaction(new BankStatement(date.toString(), action, toWhere, actionAmount, prevBalance, curBalance));
					toCSV(custList);
					logAcct.printTransaction(logOpt, logAcct, action, prevBalance, Double.toString(curBalance));
				}
				
				else if(action.contains("transfers")) {
					Customer cust = logIn(fromFirstName, fromLastName, custList);
					Account acct = null;
					Account acctRec = null;
					double prevBalance = 0;
					double curBalance = 0;
					logOpt = 4;
					if(fromWhere.contains("Checking")) { acct = cust.getChecking(); }
					else if(fromWhere.contains("Savings")) { acct = cust.getSavings(); }
					else if(fromWhere.contains("Credit")) { acct = cust.getCredit(); }
					
					if(toWhere.contains("Checking")) { acctRec = cust.getChecking(); }
					else if(toWhere.contains("Savings")) { acctRec = cust.getSavings(); }
					else if(toWhere.contains("Credit")) { acctRec = cust.getCredit(); }
					
					prevBalance = acct.getAccountBalance();
					Account.transferAmount(acct, acctRec, actionAmount);
					curBalance = acct.getAccountBalance();
					System.out.println("\n");
					
					cust.printTransaction(new BankStatement(date.toString(), action, fromWhere, actionAmount, prevBalance, curBalance));
					toCSV(custList);
					acct.printTransaction(logOpt, acct, action + " " + toWhere, prevBalance, "");
				}
				else if(action.contains("pays")) {
					Customer cust = logIn(fromFirstName, fromLastName, custList);
					Customer custRec = logIn(toFirstName, toLastName, custList);
					Account acct = null;
					Account acctRec = null;
					double prevBalance = 0;
					double curBalance = 0;
					logOpt = 5;
					if(fromWhere.contains("Checking")) { acct = cust.getChecking(); }
					else if(fromWhere.contains("Savings")) { acct = cust.getSavings(); }
					else if(fromWhere.contains("Credit")) { acct = cust.getCredit(); }
					
					if(toWhere.contains("Checking")) { acctRec = custRec.getChecking(); }
					else if(toWhere.contains("Savings")) { acctRec = custRec.getSavings(); }
					else if(toWhere.contains("Credit")) { acctRec = custRec.getCredit(); }
					
					prevBalance = acct.getAccountBalance();
					String recipientMsg = acct.paySomeone(acct, acctRec, actionAmount);
					curBalance = acct.getAccountBalance();
					System.out.println("\n");
					
					cust.printTransaction(new BankStatement(date.toString(), action, fromWhere, actionAmount, prevBalance, curBalance));
					toCSV(custList);
					acct.printTransaction(logOpt, acct, recipientMsg, prevBalance, "");
				}
				//change to where to search by name	
			}
		}
			catch (FileNotFoundException e) {
				e.printStackTrace();
		}
	}
	/**
	 * From Laurence
	 * Modified by Alfonso
	 * This method is used to create a new customer and add
	 * to the database
	 * @param custList - list of customer objects
	 * @return updated customer list
	 */
	public static List<Customer> newAccount(List<Customer> custList) {
		Scanner sc = new Scanner(System.in); 

		String firstName = null;
		String lastName = null;
		System.out.println("Enter First Name:");
		firstName = sc.nextLine();
		System.out.println("Enter Last Name:");
		lastName = sc.nextLine();

		for (Customer acct : custList) {
			if (acct.getFirstName().equals(firstName) && acct.getLastName().equals(lastName)) {
				System.out.println("Full name already exists in database");
				return custList;
			}
		}
		//PA4 requirement from rubric
		System.out.println("Enter email address: ");
		String email = sc.nextLine();
		System.out.println("Enter desired password for your account: ");
		String password = sc.nextLine();

		System.out.println("Enter date of birth (first 3 letters of month, day and year): ");
		String[] dobIn = sc.nextLine().toLowerCase().split(" ");
		String dob = dobIn[1]+"-"+dobIn[0].substring(0,1).toUpperCase()+dobIn[0].substring(1)+ "-" + dobIn[2].substring(2,4);
		
		System.out.println("Enter street number: ");
		String sNum = sc.nextLine();
		System.out.println("Enter street name: ");
		String sName = sc.nextLine();
		sName = sName.substring(0, 1) + sName.substring(1);
		System.out.println("Enter city: ");
		String city = sc.nextLine();
		city = city.substring(0, 1) + city.substring(1);
		System.out.println("Enter 2-letter state: ");
		String state = sc.nextLine();
		System.out.println("Enter 5-digit zip code: ");
		String zip = sc.nextLine();
		
		String address = "\"" + sNum + " " + sName + ", " + city + " "+ state.toUpperCase() + " " + zip + "\"";
		String inputPhNum = "000";
		
		while (inputPhNum.length()!=10) {
			System.out.println("Enter phone number: ");
			inputPhNum = sc.nextLine();
		}
		String phoneNum = inputPhNum.replaceFirst("(\\d{3})(\\d{3})(\\d+)", "($1) $2-$3");

		//int[] max = findMaxNum(custList); //max[0] = checking, max[1] = savings, max[2] = credit;

		// From Alfonso
		int idMax = getNextAccountNumber("ID", custList);
		int checkingMax = getNextAccountNumber("Checking", custList);
		int savingsMax = getNextAccountNumber("Savings", custList);
		int creditMax = getNextAccountNumber("Credit", custList);

		Savings newSavingsAcct = new Savings(firstName, lastName, savingsMax, 0);
		
		System.out.println("Would you like to create a checking account? (Y/N)"); //handle null 
		String inp1 = sc.nextLine();
		System.out.println("Would you like to create a credit account? (Y/N)");
		String inp2 = sc.nextLine();
		
		Checking newCheckingAcct = null;
		Credit newCreditAcct = null;
		if (inp1.equalsIgnoreCase("y")){
			newCheckingAcct = new Checking(firstName, lastName, checkingMax, 0);
			
		}
		if (inp2.equalsIgnoreCase("y")) {
			newCreditAcct = new Credit(firstName, lastName, creditMax, 0, 5000);
		}

		//String password = lastName + "*" + firstName + "!987";
		//String tempEmail = firstName + lastName + "@disney.com";

		Customer newCust = new Customer(firstName, lastName, dob, idMax, address, phoneNum, email, password, newCheckingAcct, newSavingsAcct, newCreditAcct);
		custList.add(newCust);
		System.out.println("Welcome to the Bank of Miners! Here is your information: ");
		newCust.printData();
		return custList;
	}
	/**
	 * This method is used to find the maximum number for each
	 * account in the list
	 * @param custList - list of customer objects
	 * @return array of maximum account values
	 */
	public static int[] findMaxNum(List<Customer> custList) {
		int maxCheck = 0;
		int maxSavings = 0;
		int maxCredit = 0;
		int maxID = 0;
		int max[] = new int[4];
		for (Customer acct : custList) {
			if(acct.getChecking().getAccountNumber() > maxCheck) {
				maxCheck = acct.getChecking().getAccountNumber();
			}
			if(acct.getSavings().getAccountNumber() > maxSavings) {
				maxSavings = acct.getSavings().getAccountNumber();
			}
			if (acct.getCredit().getAccountNumber() > maxCredit) {
				maxCredit = acct.getCredit().getAccountNumber();
			}
			if (acct.getIdentNum() > maxID) {
				maxID = acct.getIdentNum();
			}
		}
		max[0] = maxCheck;
		max[1] = maxSavings;
		max[2] = maxCredit;
		max[3] = maxID;
		
		return max;
	}

	/**
	 * From Alfonso
	 * Modified by Laurence
	 * This method is used to find the maximum number for each
	 * account in the list
	 * @param customer - list of customer objects
	 * @return largest number for given account type or ID
	 */
	private static int getNextAccountNumber(String account, List<Customer> customer){
		int largest = 0;
		switch (account){
			case "ID":
				for(int i = 0; i < customer.size(); i++){
					if (customer.get(i).getIdentNum() > largest)
						largest = customer.get(i).getIdentNum();
				}
				break;
			case "Checking":
				for(int i = 0; i < customer.size(); i++){
					if (customer.get(i).getChecking().getAccountNumber() > largest)
						largest = customer.get(i).getChecking().getAccountNumber();
				}
				break;
			case "Savings":
				for(int i = 0; i < customer.size(); i++){
					if (customer.get(i).getSavings().getAccountNumber() > largest)
						largest = customer.get(i).getSavings().getAccountNumber();
				}
				break;
			case "Credit":
				for(int i = 0; i < customer.size(); i++){
					if (customer.get(i).getCredit().getAccountNumber() > largest)
						largest = customer.get(i).getCredit().getAccountNumber();
				}
				break;
			default:
				System.out.println("ERROR. Unknown account type");

		}
		return largest + 1;
	}
	/**
	 * From Alfonso, modified by Laurence
	 * Create new customer for bank
	 * @param custList - list of customer objects
	 * @return updated customer list
	 * @throws IOException If there is no file to read.
	 */
	private static List<Customer> createNewUser(List<Customer> custList) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// Getting basic information
		boolean getName = true;
		String firstName = null;
		String lastName = null;
		while(getName) {
			System.out.println("Lets start with your name:");
			System.out.println("Enter First Name:");
			firstName = br.readLine();
			System.out.println("Enter Last Name:");
			lastName = br.readLine();

			if (validateNewUser(firstName, lastName, custList)) {
				System.out.println("User Already Exists!");
				return custList; //modified
			}else{
				getName = false;
			}
		}

		//PA4 requirement from rubric
		System.out.println("Enter email address: ");
		String email = br.readLine();
		System.out.println("Enter desired password for your account: ");
		String password = br.readLine();


		System.out.println("Enter Date Of Birth (DD-MON-YY):");
		String dob = br.readLine();
		System.out.println("Enter your Address: ");
		String address = br.readLine();
		System.out.println("Enter your Phone Number ((000) 000-0000)");
		String phoneNum = br.readLine();
		System.out.println();

		//Setting up savings account
		System.out.println("You will start with a savings account");
		int savingsID = getNextAccountNumber("Savings", custList);
		double savingsStartingBalance = 0;

		int checkingID = 0;
		double checkingStartingBalance = 0;

		int creditID = 0;
		double creditStartingBalance = 0;
		double creditMax = 0;
		Savings newSavingsAcct = new Savings(firstName, lastName, savingsID, 0);
		Checking newCheckingAcct = null;
		Credit newCreditAcct = null;
		//Setting up checking and savings account
		boolean inMenu = true;
		while(inMenu) {
			System.out.println("Would you like to to open a Checking or Credit account?");
			int choice;
			System.out.println("1. No");
			System.out.println("2. Checking");
			System.out.println("3. Credit");
			System.out.println("4. Checking AND Credit");
			choice = Integer.parseInt(br.readLine());
			switch (choice) {
				case 1:
					System.out.println("Ok!");
					inMenu = false;
					break;
				case 2:
					checkingID = getNextAccountNumber("Checking", custList);
					System.out.println("Checking account Created!");
					newCheckingAcct = new Checking(firstName, lastName, checkingID, 0);
					inMenu = false;
					break;
				case 3:
					creditID = getNextAccountNumber("Credit", custList);
					System.out.println("Default MAX CREDIT set to $1000");
					System.out.println("Credit account Created!");
					creditMax = 1000;
					newCreditAcct = new Credit(firstName, lastName, creditID, 0, creditMax);
					inMenu = false;
					break;
				case 4:
					checkingID = getNextAccountNumber("Checking", custList);
					System.out.println("Checking account Created!");
					creditID = getNextAccountNumber("Credit", custList);
					System.out.println("Default MAX CREDIT set to $1000");
					creditMax = 1000;
					newCheckingAcct = new Checking(firstName, lastName, checkingID, 0);
					newCreditAcct = new Credit(firstName, lastName, creditID, 0, creditMax);
					inMenu = false;
					break;
				default:
					System.out.println("Unknown Command");
			}
		}

		int customerID = getNextAccountNumber("ID", custList);
		Customer newCust = new Customer(firstName, lastName, dob, customerID, address, phoneNum, email, password, newCheckingAcct ,newSavingsAcct, newCreditAcct);
		custList.add(newCust);
		System.out.println("Welcome to the Bank of Miners! Here is your information:\n ");
		newCust.printData();
		return custList;
	}

	/**
	 * From Alfonso
	 * This method looks for any user in the file that has the first AND last name that was given
	 * @param firstName First name String
	 * @param lastName Last Name String
	 * @return Returns true if it finds the target first AND last name in file.
	 */
	public static boolean validateNewUser(String firstName,String lastName, List<Customer> customer){
		int i;
		for(i = 0; i < customer.size(); i++){
			if (customer.get(i).getFirstName().equals(firstName) && customer.get(i).getLastName().equals(lastName)){
				return true;
				//i = customer.size()+1;
			}
		}
		if(i == customer.size()) {
			return false;
		}
		return false;
	}
}

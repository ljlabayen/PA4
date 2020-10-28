package prjBank;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

class transferAmountTest {

	@Test
	void test1() throws IOException {
		List<Customer> custList = RunBank.readCSV("/Users/Justin/eclipse-workspace/prjBank/src/prjBank/CS 3331 - Bank Users 2.csv");
		Customer cust = null;
		for (Customer acct : custList) {
			if (acct.getFirstName().equalsIgnoreCase("Donald") && 
					acct.getLastName().equalsIgnoreCase("Duck")) {
				cust = acct;
			}
		}
		assertTrue(Account.transferAmount(cust.getChecking(), cust.getSavings(), 200)); //Should be true
	}
	
	@Test
	void test2() throws IOException {
		List<Customer> custList = RunBank.readCSV("/Users/Justin/eclipse-workspace/prjBank/src/prjBank/CS 3331 - Bank Users 2.csv");
		Customer cust = null;
		for (Customer acct : custList) {
			if (acct.getFirstName().equalsIgnoreCase("MICKEY") && 
					acct.getLastName().equalsIgnoreCase("MOUSE")) {
				cust = acct;
			}
		}
		assertTrue(Account.transferAmount(cust.getChecking(), cust.getCredit(), 20000)); //Should be false
	}
}

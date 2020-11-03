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

public abstract class Person {

    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private int identNum;
    private String address;
    private String phoneNum;
    private String email;


    /**
     * Constructor for Person Object
     * @param firstName - First name
     * @param lastName - Last name
     * @param dateOfBirth - Date of birth
     * @param identNum - ID number
     * @param address - Address of customer
     * @param phoneNum - Phone number
     */
    public Person(String firstName, String lastName, String dateOfBirth, int identNum, String address,
                  String phoneNum, String email) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.identNum = identNum;
        this.address = address;
        this.phoneNum = phoneNum;
        this.email = email;
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
     * @param firstName - desired first name
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
     * @param lastName - desired last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    /**
     * This method is used to get date of birth
     * @return date of birth of account holder
     */
    public String getDateOfBirth() {
        return dateOfBirth;
    }
    /**
     * This method is used to set date of birth
     * @param dateOfBirth - desired date of birth
     */
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * This method is used to get ID number
     * @return ID number of person
     */
    public int getIdentNum() {
        return identNum;
    }

    /**
     * This method is used to set ID number
     * @param identNum - desired ID number
     */
    public void setIdentNum(int identNum) {
        this.identNum = identNum;
    }

    /**
     * This method is used to get address
     * @return address of person
     */
    public String getAddress() {
        return address;
    }

    /**
     * This method is used to set address
     * @param address - desired address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * This method is used to get phone number
     * @return phone number of person
     */
    public String getPhoneNum() {
        return phoneNum;
    }

    /**
     * This method is used to set phone number
     * @param phoneNum - desired phone number
     */
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    /**
     * This method is used to get email
     * @return email of person
     */
    public String getEmail() {
        return email;
    }
    /**
     * This method is used to set email
     * @param email - desired email
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
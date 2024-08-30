package sprint11;

import java.util.*;
import java.io.*;
//import java.util.Scanner;
//import java.io.Serializable;
//import java.util.Comparator;

public class Customer implements Comparable<Customer>, Serializable{
	 private static final long serialVersionUID = 1L;
	private static int idCounter = 100;
	private int customerId;
	private String customerName;
	private int age;
	private long mobileNumber;
	private String passportNumber;
	private String dob;
	private BankAccount account;
	
	public Customer(String customerName, int age, long mobileNumber, String passportNumber, String dob) {
		this.customerId=idCounter++;
		this.customerName= customerName;
		this.age=age;
		this.mobileNumber=mobileNumber;
		this.passportNumber=passportNumber;
		
//		while(!isValidDateOfBirth(dob)) {
//			System.out.println("Invalid date of birth");
//			Scanner sc = new Scanner(System.in);
//			dob = sc.nextLine();
//		}
		this.dob=dob;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public long getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getPassportNumber() {
		return passportNumber;
	}

	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}

	public String getDob() {
		return dob;
	}
	
	public void setDob(String dob) {
		this.dob=dob;
	}
//	public void setDob(String dob) {
//		if(isValidDateOfBirth(dob)) {
//			this.dob= dob;
//		}else {
//			System.out.println("Invalid Date of Birth");
//		}
//	}
//
//	private boolean isValidDateOfBirth(String dob) {
//		// TODO Auto-generated method stub
//		String[] dateParts = dob.split("/");
//		if(dateParts.length == 3) {
//			try {
//				int day = Integer.parseInt(dateParts[0]);
//				int month = Integer.parseInt(dateParts[1]);
//				int year = Integer.parseInt(dateParts[2]);
//			}catch(NumberFormatException e){
//				return false;
//			}
//		}
//		
//		return false;
//	}

	public BankAccount getAccount() {
		return account;
	}

	public void setAccount(BankAccount account) {
		this.account = account;
	}
	@Override
	public String toString() {
		return "Customer ID: " + customerId +
                ",\nName: " + customerName +
                ",\nAge: " + age +
                ",\nMobile Number: " + mobileNumber +
                ",\nPassport Number: " + passportNumber +
                ",\nDOB: " + dob +
                (account != null ? ", Account: " + account.toString() : "");
	}

	@Override
	public int compareTo(Customer o) {
		// TODO Auto-generated method stub
		return Integer.compare(this.customerId, o.customerId);
	}
	
}

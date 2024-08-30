package sprint11;

import java.io.Serializable;

public abstract class BankAccount implements Serializable {
    private static final long serialVersionUID = 1L;
	private static long accountNumberCounter =1000;//it will generate unique variable
	protected long accountNumber;
	protected long bsbCode;
	protected String bankName;
	protected double balance;
	protected String openingDate;
	
	public BankAccount(long bsbCode, String bankName, double balance, String openingDate) {
		this.accountNumber= accountNumberCounter++;//it will increment, for the next account
		this.bsbCode=bsbCode;
		this.bankName=bankName;
		this.balance=balance;
		this.openingDate = openingDate;
	}
	
	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public abstract double calculateInterest();//abstract method, will have specific implementation for the child classes
	
	@Override
	public String toString() {
		return "Account Number: "+accountNumber+
				", BSB Code:  "+bsbCode+
				", Bank Name: "+bankName+
				", Balance: "+balance+
				", Opening Date: "+openingDate;
	}
}
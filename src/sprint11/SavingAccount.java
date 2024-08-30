package sprint11;

class InsufficientBalanceException extends Exception {
    public InsufficientBalanceException(String message) {
        super(message);
    }
}
public class SavingAccount extends BankAccount {
	private boolean isSalaryAccount;
	private static final double INTEREST_RATE =0.04;
	private static final double MINIMUM_BALANCE = 100;
	

	public SavingAccount(long bsbCode, String bankName, double balance, String openingDate, boolean isSalaryAccount) throws InsufficientBalanceException {
        super(bsbCode, bankName, balance, openingDate);
        this.isSalaryAccount = isSalaryAccount;
        
        if (isSalaryAccount) {
            if (balance != 0) {
                throw new InsufficientBalanceException("For Salary Accounts, the balance must be zero.");
            }
        } else {
            if (balance < MINIMUM_BALANCE) {
                throw new InsufficientBalanceException("Insufficient balance for Savings Account, Minimum balance should be 100 $");
            }
        }
    }

	@Override
	public double calculateInterest() {
		return balance*INTEREST_RATE;
	}
	@Override
	public String toString() {
		return super.toString()+
				", isSalaryAccount: "+isSalaryAccount+", Minimum Balance: "+MINIMUM_BALANCE+
				", Interest Earned: "+calculateInterest();
	}	
}
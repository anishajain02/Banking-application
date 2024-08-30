package sprint11;

public class FixedDepositAccount extends BankAccount{
	private double depositAmount;
	private int tenure;
	private static final double INTEREST_RATE = 0.08;
	private static final double MINIMUM_DEPOSIT = 1000;
	private static final int MINIMUM_TENURE = 1;
	private static final int MAXIMUM_TENURE = 7;
	
	public FixedDepositAccount(long bsbCode, String bankName, double balance, String openingDate, double depositAmount, int tenure) {
		super(bsbCode, bankName, balance, openingDate);
		// TODO Auto-generated constructor stub
		this.depositAmount=depositAmount;
		this.tenure=tenure;
	}

	@Override
	public double calculateInterest() {
		// TODO Auto-generated method stub
		return depositAmount*INTEREST_RATE*tenure;
	}
	@Override
    public String toString() {
        return super.toString() +
                ", Deposit Amount: " + depositAmount +
                ", Tenure: " + tenure +
                ", Interest Earned: " + calculateInterest();
    }

}

package sprint11;

import java.io.*;
import java.util.*;

public class Banking {

    private static ArrayList<Customer> customers = new ArrayList<>();

    private static final String DATA_FILE = "customers.dat";
    private static void displayMenu() {
        System.out.println("1. Create new Customer");
        System.out.println("2. Assign a bank account to a customer");
        System.out.println("3. Display balance or interest earned of a customer");
        System.out.println("4. Sort Customer Data");
        System.out.println("5. Persist Customer Data");
        System.out.println("6. Show all customers");
        System.out.println("7. Search customer by name");
        System.out.println("8. Exit");
        System.out.println("Enter your choice");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        loadCustomerData(); // Load data at the start
        int choice;

        do {
            displayMenu();
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    createNewCustomer(sc);
                    break;
                case 2:
                    assignBankAccount(sc);
                    break;
                case 3:
                    displayBalanceOrInterest(sc);
                    break;
                case 4:
                    sortCustomerData();
                    break;
                case 5:
                    persistCustomerData();
                    break;
                case 6:
                    showAllCustomers();
                    break;
                case 7:
                    searchCustomerByName(sc);
                    break;
                case 8:
                    persistCustomerData(); // Save data before exit
                    System.out.println("Exiting the program, GoodBye!");
                    break;
                case 9:
                    // Test persist and load customer data
                    testPersistAndLoadCustomerData();
                    break;
                default:
                    System.out.println("Invalid Choice, enter between 1-8");
            }
        } while (choice != 8);

        sc.close();
    }

    // for option 1
    private static void createNewCustomer(Scanner sc) {
        System.out.println("Enter customer Name: ");
        String name = sc.nextLine();
        System.out.println("Enter age: ");
        int age = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter mobile number: ");
        long mobile = sc.nextLong();
        sc.nextLine();
        System.out.println("Enter passport number: ");
        String passport = sc.nextLine();
        System.out.println("Enter date of birth (DD/MM/YYYY): ");
        String dob = sc.nextLine();

        Customer customer = new Customer(name, age, mobile, passport, dob);
        customers.add(customer);
        System.out.println("Customer created successfully.");
    }

    // for option 2
    private static void assignBankAccount(Scanner sc) {
        System.out.println("Enter customer ID to assign account: ");
        int customerId = sc.nextInt();
        Customer customer = findCustomerById(customerId);

        if (customer != null) {
            System.out.println("Enter BSB Code: ");
            long bsbCode = sc.nextLong();
            sc.nextLine();
            System.out.println("Enter Bank Name: ");
            String bankName = sc.nextLine();
            System.out.println("Enter balance: ");
            double balance = sc.nextDouble();
            sc.nextLine();
            System.out.println("Enter opening date: (DD/MM/YYYY): ");
            String openingDate = sc.nextLine();

            System.out.println("Choose account type(1. Savings, 2. Fixed deposit): ");
            int accountType = sc.nextInt();
            sc.nextLine();

            BankAccount account = null;
            if (accountType == 1) {
                System.out.println("Is Salary Account(true/false): ");
                boolean isSalary = sc.nextBoolean();
                try {
                    account = new SavingAccount(bsbCode, bankName, balance, openingDate, isSalary);
                    customer.setAccount(account);
                    System.out.println("Account assigned successfully.");
                } catch (InsufficientBalanceException e) {
                    System.out.println(e.getMessage());
                }
            } else if (accountType == 2) {
                System.out.print("Enter deposit amount: ");
                double depositAmount = sc.nextDouble();
                System.out.print("Enter tenure (in years): ");
                int tenure = sc.nextInt();
                account = new FixedDepositAccount(bsbCode, bankName, balance, openingDate, depositAmount, tenure);
                customer.setAccount(account);
                System.out.println("Account assigned successfully.");
            } else {
                System.out.println("Invalid account type.");
            }
        } else {
            System.out.println("Customer not found.");
        }
    }

    private static Customer findCustomerById(int customerId) {
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getCustomerId() == customerId) {
                return customers.get(i);
            }
        }
        return null;
    }

    // for option 3
    private static void displayBalanceOrInterest(Scanner sc) {
        System.out.println("enter customer Id to view balance or interest:  ");
        int customerId = sc.nextInt();
        Customer customer = findCustomerById(customerId);

        if (customer != null) {
            BankAccount account = customer.getAccount();
            if (account != null) {
                System.out.println("Balance: " + account.getBalance());
                System.out.println("Interest earned: " + account.calculateInterest());
            } else {
                System.out.println("No account assigned to this customer.");
            }
        } else {
            try {
                throw new ClassNotFoundException("Customer not found.");
            } catch (ClassNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    // for option 4
    private static void sortCustomerData() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Choose Sorting Criteria: 1.Name, 2.ID, 3.Bank Balance");
        int criteria = sc.nextInt();
        sc.nextLine();

        switch (criteria) {
            case 1:
                Collections.sort(customers, new CustomerNameComparator());
                System.out.println("Customer sorted by name");
                break;
            case 2:
                Collections.sort(customers);
                System.out.println("Customer sorted by ID.");
                break;
            case 3:
                Collections.sort(customers, new CustomerBalanceComparator());
                System.out.println("Customer sorted by Balance");
                break;
            default:
                System.out.println("Invalid choice. Sorting by ID");
                Collections.sort(customers);
                break;
        }
        showAllCustomers();
    }

    // for option 5
    private static void loadCustomerData() {
        File file = new File("customers.dat");
        if (!file.exists()) {
            System.out.println("Customer data file not found. A new file will be created.");
            return;
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            customers = (ArrayList<Customer>) ois.readObject();
            System.out.println("Customer data loaded successfully.");
        } catch (FileNotFoundException e) {
            System.out.println("Customer data file not found.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading customer data: " + e.getMessage());
        }
    }

    private static void persistCustomerData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("customers.dat"))) {
            oos.writeObject(customers);
            System.out.println("Customer data persisted successfully.");
        } catch (IOException e) {
            System.out.println("Error persisting customer data: " + e.getMessage());
        }
    }
    
    //for testing persist data method
    private static void testPersistAndLoadCustomerData() {
        // Step 1: Create some customer data
        Customer customer1 = new Customer("John Doe", 30, 1234567890L, "A1234567", "01/01/1990");
        Customer customer2 = new Customer("Jane Smith", 25, 9876543210L, "B7654321", "02/02/1995");
        customers.add(customer1);
        customers.add(customer2);

        BankAccount account1 = null;
        try {
            account1 = new SavingAccount(100, "Bank A", 5000.0, "01/01/2020", false);
        } catch (InsufficientBalanceException e) {
            e.printStackTrace();
        }
        BankAccount account2 = new FixedDepositAccount(200, "Bank B", 10000.0, "02/02/2021", 5000.0, 5);

        customer1.setAccount(account1);
        customer2.setAccount(account2);

        System.out.println("Initial Customers:");
        for (Customer customer : customers) {
            System.out.println(customer);
        }

        // Step 2: Persist the data
        persistCustomerData();

        // Step 3: Clear the current data
        customers.clear();

        // Step 4: Load the data
        loadCustomerData();

        System.out.println("Loaded Customers:");
        for (Customer customer : customers) {
            System.out.println(customer);
        }

        // Step 5: Verify the data
        if (customers.size() == 2 &&
            customers.get(0).getCustomerName().equals("John Doe") &&
            customers.get(1).getCustomerName().equals("Jane Smith") &&
            customers.get(0).getAccount().getBalance() == 5000.0 &&
            customers.get(1).getAccount().getBalance() == 10000.0) {
            System.out.println("Persist and Load Customer Data test passed.");
        } else {
            System.out.println("Persist and Load Customer Data test failed.");
        }
    }



    // for option 6
    private static void showAllCustomers() {
        if (customers.isEmpty()) {
            System.out.println("No customers available");
            return;
        } else {
            System.out.println("All Customers: ");
            for (Customer customer : customers) {
                System.out.println(customer.toString());
            }
        }
    }

    // for option 7
    private static void searchCustomerByName(Scanner sc) {
        System.out.println("Enter customer name to search: ");
        String name = sc.nextLine();
        boolean found = false;
        for (Customer customer : customers) {
            if (customer.getCustomerName().equalsIgnoreCase(name)) {
                System.out.println(customer.toString());
                found = true;
            }
        }
        if (!found) {
            System.out.println("Customer not found.");
        }
    }
}

// Comparator classes used for sorting customer data
class CustomerNameComparator implements Comparator<Customer> {
    @Override
    public int compare(Customer o1, Customer o2) {
        return o1.getCustomerName().compareTo(o2.getCustomerName());
    }
}

class CustomerBalanceComparator implements Comparator<Customer> {
    @Override
    public int compare(Customer o1, Customer o2) {
        double balance1 = o1.getAccount() != null ? o1.getAccount().getBalance() : 0.0;
        double balance2 = o2.getAccount() != null ? o2.getAccount().getBalance() : 0.0;
        return Double.compare(balance1, balance2);
    }
}

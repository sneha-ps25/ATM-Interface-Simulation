import javax.swing.*;
import java.awt.*;

public class atm {

    // ==== Account Class ====
    static class Account {
        private String accountNumber;
        private String pin;
        private double balance;

        public Account(String accountNumber, String pin, double balance) {
            this.accountNumber = accountNumber;
            this.pin = pin;
            this.balance = balance;
        }

        public String getAccountNumber() {
            return accountNumber;
        }

        public String getPin() {
            return pin;
        }

        public double getBalance() {
            return balance;
        }

        public void deposit(double amount) {
            if (amount > 0) {
                balance += amount;
            }
        }

        public boolean withdraw(double amount) {
            if (amount > 0 && amount <= balance) {
                balance -= amount;
                return true;
            }
            return false;
        }
    }

    // ==== Authentication Class ====
    static class Authentication {
        public static Account login(Account[] accounts, String accNumber, String pin) {
            for (Account acc : accounts) {
                if (acc.getAccountNumber().equals(accNumber) && acc.getPin().equals(pin)) {
                    return acc;
                }
            }
            return null;
        }
    }

    // ==== ATM GUI Class ====
    static class ATM_GUI extends JFrame {
        private Account account;

        public ATM_GUI(Account account) {
            this.account = account;

            setTitle("ATM - Account: " + account.getAccountNumber());
            setSize(300, 200);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);

            JButton balanceBtn = new JButton("Check Balance");
            JButton depositBtn = new JButton("Deposit");
            JButton withdrawBtn = new JButton("Withdraw");
            JButton exitBtn = new JButton("Exit");

            balanceBtn.addActionListener(e -> {
                JOptionPane.showMessageDialog(this, "Current Balance: $" + account.getBalance());
            });

            depositBtn.addActionListener(e -> {
                String input = JOptionPane.showInputDialog(this, "Enter amount to deposit:");
                try {
                    double amount = Double.parseDouble(input);
                    account.deposit(amount);
                    JOptionPane.showMessageDialog(this, "Deposited successfully!");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid amount entered.");
                }
            });

            withdrawBtn.addActionListener(e -> {
                String input = JOptionPane.showInputDialog(this, "Enter amount to withdraw:");
                try {
                    double amount = Double.parseDouble(input);
                    if (account.withdraw(amount)) {
                        JOptionPane.showMessageDialog(this, "Withdrawn successfully!");
                    } else {
                        JOptionPane.showMessageDialog(this, "Insufficient balance or invalid amount.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid amount entered.");
                }
            });

            exitBtn.addActionListener(e -> System.exit(0));

            setLayout(new GridLayout(4, 1));
            add(balanceBtn);
            add(depositBtn);
            add(withdrawBtn);
            add(exitBtn);

            setVisible(true);
        }
    }

    // ==== Main Method ====
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Create accounts
            Account[] accounts = new Account[4];
            accounts[0] = new Account("1234567890", "1111", 1000);
            accounts[1] = new Account("0987654321", "2222", 1500);
            accounts[2] = new Account("1357902468", "3333", 2000);
            accounts[3] = new Account("0246813579", "4444", 500);

            // Login UI
            JTextField accountField = new JTextField();
            JPasswordField pinField = new JPasswordField();

            Object[] loginFields = {
                "Enter Account Number:", accountField,
                "Enter PIN:", pinField
            };

            int option = JOptionPane.showConfirmDialog(null, loginFields, "ATM Login", JOptionPane.OK_CANCEL_OPTION);

            if (option == JOptionPane.OK_OPTION) {
                String accountNumber = accountField.getText();
                String pin = new String(pinField.getPassword());
                Account acc = Authentication.login(accounts, accountNumber, pin);

                if (acc != null) {
                    new ATM_GUI(acc);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid login credentials.");
                }
            }
        });
    }
}

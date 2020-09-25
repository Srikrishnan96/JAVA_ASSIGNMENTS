package Assignment_1;

import java.io.IOException;

import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class CurrencyDispenser {
	
	private static Scanner inputReader = new Scanner(System.in);
	private static Random randomGenerator = new Random();
	private static int bankBalance = CurrencyDispenser.randomGenerator.nextInt(1000) + 11;
	private static ArrayList<String> transactionHistory = new ArrayList<String>(20);
	private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/DD/YYYY, HH:mm:ss");
	
	private static void TransactionHistoryOf(String HistorySize) {
		int transactionHistoryArrLength = transactionHistory.size();
		if(transactionHistoryArrLength <= 0) {
			System.out.println("NO TRANSACTION HISTORY");
			return;
		}
		switch(HistorySize) {
			case "three": {
				int index=0;
				for(String transaction: transactionHistory) {
					System.out.println("-> "+transaction);
					if(index == 2) break;
					index++;
				}
				break;
			}
			default: {
				for(String transaction: transactionHistory) {
					System.out.println("-> "+transaction);
				}
			}
		}
	}
	
	private static void AddTransactionRecord(String typeOfTransaction, int amount) {
		String transactionRecord;
		transactionRecord = typeOfTransaction+"ED $"+amount+" ON "+dtf.format(LocalDateTime.now());
		transactionHistory.add(0, transactionRecord);
	}
	
	private static void Deposit() {
		int amount;
		System.out.println("Enter the amount to Deposit");
		amount = inputReader.nextInt();
		bankBalance += amount;
		AddTransactionRecord("DEPOSIT", amount);
		System.out.println("\nDEPOSITED $"+amount);
		System.out.println("NEW BALANCE: $"+bankBalance+"\n");
	}
	
	private static void Withdraw() {
		int amount;
		System.out.println("Enter the amount to withdraw");
		amount = inputReader.nextInt();
		if(amount > bankBalance) {
			System.out.println("Amount exceeding your current balance: $"+bankBalance);
		} else {
			bankBalance -= amount;
			AddTransactionRecord("WITHDRAW", amount);
			while(amount > 0) {
				if(amount - 10 >= 0) {
					System.out.println(10);
					amount -= 10;
				}
				else if(amount - 5 >= 0) {
					System.out.println(5);
					amount -= 5;
				}
				else {
					System.out.println(1);
					amount -= 1;
				}
			}
			System.out.println("TRANSACTION COMPLETED");
		}
	}

	private static void Banking() {
		String enterBanking;
		boolean quitBanking = false;
		System.out.println("Want to enter banking service? YES or NO");
		enterBanking = inputReader.next();
		while(!quitBanking) {
			if(enterBanking.equalsIgnoreCase("NO")) {
				quitBanking = true;
				continue;
			} else if(!enterBanking.equalsIgnoreCase("YES")) {
				System.out.println("Please type a valid option");
				continue;
			} else {
				int transactionOption;
				System.out.println("Select from the options:"
						+ "\n1. WITHDRAW"
						+ "\n2. DEPOSIT"
						+ "\n3. PAST 3 Transaction History"
						+ "\n4. All Transaction History"
						+ "\n5. Current Balance"
						+ "\n6. Exit Banking");
				transactionOption = inputReader.nextInt();
				switch(transactionOption) {
					case 1: {
						Withdraw();
						break;
					}
					case 2: {
						Deposit();
						break;
					}
					case 3: {
						TransactionHistoryOf("three");
						break;
					}
					case 4: {
						TransactionHistoryOf("");
						break;
					}
					case 5: {
						System.out.println("CURRENT BALANCE: $"+bankBalance);
						break;
					}
					case 6: {
						quitBanking = true;
						System.out.println("EXITING BANKING");
						continue;
					}
					default: {
						System.out.println("Please select a valid option");
						continue;
					}
				}
			}
		}	
	}
	
	public static void main(String[] args) throws IOException {
		System.out.println("CURRENT BANK BALANCE: "+bankBalance);
		Banking();
	}
}
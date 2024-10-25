package tests;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import lombok.extern.slf4j.Slf4j;

/**
 * Classe principal para o aplicativo de conta bancária. Permite criar contas,
 * realizar depósitos, pagar contas e exibir a lista de contas.
 * 
 * @author Victor Almada
 */

@Slf4j
public class BankAccountApp {
	private static List<BankAccount> accountsList = new ArrayList<>();

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);

		while (true) {
			System.out.println("### TELA INICIAL ###");
			System.out.println("   1 : Create new account");
			System.out.println("   2 : Search account by number");
			System.out.println("   3 : Pay bill");
			System.out.println("   4 : Make deposit");
			System.out.println("   5 : View account list");
			System.out.println("   0 : Exit");
			System.out.print("Enter: ");

			try {
				int userChoose = sc.nextInt();
				String accNum;
				Double amount;
				switch (userChoose) {
				case 1:
					createAccount(sc);
					break;
				case 2:
					if (accountsList == null || accountsList.isEmpty()) {
						log.warn("A lista de contas está vazia ou não foi inicializada.");
						break;
					}
					System.out.print("Please, type the account number: ");
					accNum = sc.next();
					BankAccount account = findAccountByNumber(accNum);
					if (account != null) {
						log.info("Account found: " + account);
					} else {
						log.warn("Account not found.");
					}
					break;
				case 3:
					System.out.print("Please, type the account number: ");
					accNum = sc.next();
					System.out.print("Amount: ");
					amount = sc.nextDouble();
					for (BankAccount x : accountsList) {
						if (x.getAccountNumber().equals(accNum)) {
							x.payBill(amount);
						}  else {
							log.warn("Conta digitada não existe");
						}
					}
					break;

				case 4:
					System.out.print("Please, type the account number: ");
					accNum = sc.next();
					System.out.print("Amount: ");
					amount = sc.nextDouble();
					for (BankAccount x : accountsList) {
						if (x.getAccountNumber().equals(accNum)) {
							x.makeDeposit(amount);
						} else {
							log.warn("Conta digitada não existe");
						}
					}
					break;
				case 5:
					accountsList.forEach(System.out::println);
					if (accountsList == null || accountsList.isEmpty()) {
						log.warn("A lista de contas está vazia ou não foi inicializada.");
					}
					break;
				case 0:
					System.out.println("Exiting...");
					sc.close();
					return;
				}
			} catch (InputMismatchException e) {
				log.warn("Invalid input. Please enter a valid number.");
				sc.nextLine();
			}
		}
	}

	/**
	 * Encontra uma conta bancária pelo número da conta.
	 * 
	 * @param accountNumber o número da conta a ser pesquisada.
	 * @return a conta bancária correspondente ou null se não for encontrada.
	 * @author Victor Almada
	 */

	public static BankAccount findAccountByNumber(String accountNumber) {
			return accountsList.stream().filter(account -> account.getAccountNumber().equals(accountNumber)).findFirst()
					.orElse(null);
	}

	/**
	 * Cria uma nova conta bancária com o nome e SSN do cliente. Realiza validações
	 * para garantir que os dados inseridos são válidos.
	 * 
	 * @param sc o objeto Scanner usado para capturar as entradas do usuário.
	 * @author Victor Almada
	 */

	public static void createAccount(Scanner sc) {
		System.out.println("\n### Create Account ###");
		sc.nextLine();
		String clientName;
		while (true) {
			System.out.print("Enter your name: ");
			clientName = sc.nextLine();
			if (clientName.matches("[a-zA-Z ]+")) {
				break;
			} else {
				log.warn("Invalid name. Please enter only letters.");
			}
		}
		String clientSsn;
		while (true) {
			System.out.print("Enter your SSN (only digits): ");
			clientSsn = sc.nextLine();
			if (clientSsn.matches("\\d{3,11}")) {
				break;
			} else {
				log.warn("Invalid SSN. Please enter only digits (3-11 characters).");
			}
		}
		BankAccount account = new BankAccount(clientName, clientSsn, 0);
		accountsList.add(account);
		log.info("Account successfully created!");
	}
}

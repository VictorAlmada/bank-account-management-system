package tests;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * Representa uma conta bancária.
 * 
 * Esta classe fornece métodos para gerenciar uma conta bancária, incluindo
 * criação de conta, depósitos, pagamentos de contas e exibição de saldo.
 */

@Slf4j
class BankAccount implements IInterest {

	// attributes
	private static final int RANDOM_NUM_MIN = 10;
	private static final int RANDOM_NUM_MAX = 90;
	@Getter
	private static int id = 1000;
	@Getter
	private String accountNumber;
	private static final String routingNumber = "005400657";
	@Getter
	@Setter
	private String name;
	private String SSN;
	private double balance;

	/**
	 * Construtor para criar uma nova conta bancária.
	 * 
	 * @param name        O nome do titular da conta.
	 * @param SSN         O número de seguridade social do titular da conta.
	 * @param initDeposit O depósito inicial da conta.
	 * 
	 * @throws IllegalArgumentException Se o SSN for nulo ou vazio, ou se o depósito
	 *                                  inicial for negativo.
	 * @throws NullPointerException     Se o nome for nulo ou vazio.
	 */

	// constructor
	public BankAccount(String name, String SSN, double initDeposit) {
		try {
			if (SSN == null || SSN.isEmpty()) {
				throw new IllegalArgumentException("SSN cannot be null or empty");
			}
			if (initDeposit < 0) {
				throw new IllegalArgumentException("Initial deposit cannot be negative");
			}
			if (name.isBlank()) {
				throw new NullPointerException("Name cannot be null or empty");
			}
			this.name = getFormattedName(name);
			this.SSN = SSN;
			this.balance = initDeposit;
			id++;
			log.info("Name: " + this.name + ", ID: " + id);
			setAccountNumber();
		} catch (Exception e) {
			log.error("Erro: " + e.getMessage());
		}

	}

	// methods

	/**
	 * Define o número da conta com base em um ID gerado e um número aleatório.
	 */
	private void setAccountNumber() {
		int randomNum = (int) ((Math.random() * RANDOM_NUM_MAX) + RANDOM_NUM_MIN);
		accountNumber = id + "" + randomNum + SSN.substring(0, 2);
		log.info("Your account number is: " + accountNumber);
	}

	/**
	 * Paga uma conta utilizando o saldo da conta.
	 * 
	 * @param amount O valor a ser pago.
	 */
	public void payBill(double amount) {
		if (amount > this.balance) {
			log.error("Non-sufficient funds");
		} else {
			balance -= amount;
			log.info("(" + this.name + ") Paying bill: $" + String.format("%.2f", amount));
			showBalance();
		}
	}

	/**
	 * Faz um depósito na conta.
	 * 
	 * @param amount O valor a ser depositado.
	 */
	public void makeDeposit(double amount) {
		balance += amount;
		log.info("(" + this.name + ") Making deposit: $" + String.format("%.2f", amount));
		showBalance();
	}

	/**
	 * Exibe o saldo atual da conta.
	 */
	public void showBalance() {
		log.info("(" + this.name + ") Actual balance: $" + String.format("%.2f", balance) + "\n");
	}

	/**
	 * Acumula juros no saldo da conta.
	 */
	@Override
	public void accrue() {
		balance *= (1 + rate / 100);
		showBalance();
	}

	/**
	 * Formata o nome do titular da conta para que a primeira letra seja maiúscula e
	 * as demais sejam minúsculas.
	 * 
	 * @param name O nome a ser formatado.
	 * @return O nome formatado.
	 */
	public String getFormattedName(String name) {
		String firstLetter = name.substring(0, 1).toUpperCase();
		String restOfName = name.substring(1).toLowerCase();
		return firstLetter + restOfName;
	}

	/**
	 * Retorna uma representação em String da conta bancária.
	 * 
	 * @return A representação em String da conta bancária.
	 */
	@Override
	public String toString() {
		return "{name: " + this.name + ", account number: " + this.accountNumber + ", routing number: " + routingNumber
				+ ", balance: $" + String.format("%.2f", balance) + "}";
	}
}

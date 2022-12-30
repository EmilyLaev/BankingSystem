package banking;

import java.util.*;

class Main extends Display{
    public static void main(String[] args) {
        launchMainMenu();
    }
}

class Display {

    static Scanner scanner = new Scanner(System.in);
    static Database database = new Database();
    static Card customerCard;

    static void launchMainMenu() {
        while (true) {
            System.out.println("\n1. Create an account\n2. Log into account\n0. Exit");
            switch (scanner.nextInt()) {
                case 1:
                    int i = database.getCustomerCounter();
                    database.createAccount();
                    System.out.printf("\nYour card has been created\nYour card number:\n%s\nYour card PIN:\n%s\n",
                            database.getCARDS()[i].getNUMBER(), database.getCARDS()[i].getPIN());
                    break;
                case 2:
                    launchLoginMenu();
                    break;
                case 0:
                    System.out.println("Bye!");
                    return;
            }
        }
    }

    static void launchLoginMenu() {
        if (customerCard == null) {
            System.out.println("\nEnter your card number:");
            String enteredNumber = scanner.next();
            System.out.println("Enter your PIN:");
            String enteredPIN = scanner.next();
            customerCard = database.verifyAccount(enteredNumber, enteredPIN);
        }
        if (customerCard != null) {
            System.out.println("You have successfully logged in!");
            launchCustomerMenu();
        } else {
            System.out.println("Wrong card number or PIN!");
        }
    }

    static void launchCustomerMenu() {
        while (true) {
            System.out.println("1. Balance\n2. Log out\n0. Exit");
            switch (scanner.nextInt()) {
                case 1:
                    System.out.printf("Balance: %d\n", customerCard.getBalance());
                    break;
                case 2:
                    System.out.println("You have successfully logged out!");
                    customerCard = new Card(0);
                    customerCard = null;
                    return;
                case 0:
                    return;
            }
        }
    }
}

class Database {

    private int customerCounter = 0;
    private final Card[] CARDS = new Card[1000000];

    public int getCustomerCounter() {
        return customerCounter;
    }

    public Card[] getCARDS() {
        return CARDS;
    }

    void createAccount() {
        CARDS[customerCounter] = new Card(0);
        CARDS[customerCounter].generateNUMBER();
        CARDS[customerCounter].generatePIN();

        for (int i = 0; i < CARDS.length; i++) {
            if (CARDS[customerCounter].getNUMBER().equals(CARDS[i].getNUMBER()) && i != customerCounter && CARDS[i] != null) {
                CARDS[customerCounter].generateNUMBER();
                i = 0;
            } else {
                customerCounter++;
                break;
            }
        }
    }

    Card verifyAccount(String enteredNumber, String enteredPIN) {
        for (Card card: CARDS) {
            if (card != null && card.getNUMBER().equals(enteredNumber) && card.getPIN().equals(enteredPIN)){
                return card;
            }
        }
        return null;
    }
}

class Card {
    static Random random = new Random();

    private final int[] NUMBER = new int[16];
    private final int[] PIN = new int[4];
    private long balance;

    Card(long balance) {
        this.balance = balance;
    }


    //These are the get and generate functions for numbers and pins
    public String getNUMBER() {
        return Arrays.toString(NUMBER).replace("[", "").replace(", ", "").replace("]", "");
    }
    void generateNUMBER() {
        NUMBER[0] = 4;
        for (int i = 1; i < 16; i++) {
            NUMBER[i] = i < 6 ? 0 : random.nextInt(10);
        }
    }
    public String getPIN() {
        return Arrays.toString(PIN).replace("[", "").replace(", ", "").replace("]", "");
    }
    void generatePIN() {
        for (int i = 0; i < 4; i++) {
            PIN[i] = random.nextInt(10);
        }
    }
    public long getBalance() {
        return balance;
    }
}
package banking;

import java.util.*;

//The main running of the project (written in terms of an extended class)
class Main extends CustomerMenu {
    public static void main(String[] args) {
        launchMainMenu();
    }
}

class CustomerMenu extends CardsManagementSystem {

    private static Scanner scanner = new Scanner(System.in);
    private static Card loginCard;

    //a method to launch the main menu seen by the user
    //when user inputs a value the appropriate responses are enacted
    static void launchMainMenu() {
        while (true) {
            System.out.println("\n1. Create an account\n2. Log into account\n0. Exit");
            switch (scanner.nextInt()) {
                case 1:
                    addCard(new Card());
                    System.out.print(getLastAddCard().toString());
                    break;
                case 2:
                    launchLoginMenu();
                    break;
                case 0:
                    System.out.println("\nBye!");
                    return;
            }
        }
    }

    // A method for the login menu where the user can enter their card number and PIN
    static void launchLoginMenu() {
        if (loginCard == null) {
            System.out.println("\nEnter your card number:");
            String enteredNumber = scanner.next();
            System.out.println("Enter your PIN:");
            String enteredPin = scanner.next();

            loginCard = getCard(enteredNumber, enteredPin);
        }
        System.out.print(loginCard == null ? "\nWrong card number or PIN!\n" : "\nYou have successfully logged in!\n");
        launchAccountMenu();
    }

    // A method for the main menu seen by the user when they are logged in
    static void launchAccountMenu() {
        while (loginCard != null) {
            System.out.println("\n1. Balance\n2. Log out\n0. Exit");
            switch (scanner.nextInt()) {
                case 1:
                    System.out.printf("\nBalance: %d\n", loginCard.getBalance());
                    break;
                case 2:
                    System.out.println("\nYou have successfully logged out!");
                    loginCard = null;
                    return;
                case 0:
                    return;
            }
        }
    }
}

class Card extends CardsManagementSystem {

    private String number;
    private String pin;
    private long balance;

    // Constructor for creating a new card with a random PIN and a balance of 0
    Card() {
        this.pin = String.valueOf(nextInt(9000) + 1000);
        this.balance = 0;
    }
    
    //The main get and set methods for the numbers and pins
    String getNumber() {
        return this.number;
    }
    void setNumber() {
        this.number = addChecksum(new StringBuilder("400000").append(nextInt(899999999) + 100000000));
    }
    String getPin() {
        return this.pin;
    }
    long getBalance() {
        return balance;
    }

    //A method to override the toString method with no arguments
    @Override
    public String toString() {
        return String.format("\nYour card has been created\nYour card number:\n%s\nYour card PIN:\n%s\n",
                this.number, this.pin);
    }
}

class CardsManagementSystem extends Random {

    private static final List<Card> CARDS = new ArrayList<>();

    // A method for adding a checksum to a card number
    String addChecksum(StringBuilder numberWithoutChecksum) {
        int checksum = 0;
        for (int i = 0; i < numberWithoutChecksum.length(); i++) {
            int digit = Integer.parseInt(String.valueOf(numberWithoutChecksum.charAt(i)));
            digit = i % 2 == 0 ? digit * 2 : digit;
            checksum += digit > 9 ? digit - 9 : digit;
        }
        return String.valueOf(numberWithoutChecksum.append((checksum * 10 - checksum % 10) % 10));
    }

    // A method for getting a card with a given card number and PIN
    static Card getCard(String enteredNumber, String enteredPin) {
        for (Card card: CARDS) {
            if (card.getNumber().equals(enteredNumber) && card.getPin().equals(enteredPin)) {
                return card;
            }
        }
        return null;
    }

    // A method for getting the last added card
    static Card getLastAddCard() {
        return CARDS.get(CARDS.toArray().length - 1);
    }

    // A method for adding a new card to the system
    static void addCard(Card newCard) {
        do {
            newCard.setNumber();
        } while (CARDS.stream().anyMatch(card -> card.getNumber().equals(newCard.getNumber())));
        CARDS.add(newCard);
    }
}

import java.util.Scanner;
public class Main {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("What is your name?");
        String Mess = scan.next();
        System.out.println("Hello " + Mess);
        System.out.println("What is your favourite colour?");
        String cAnswer = scan.next();
        System.out.println(cAnswer + " is an awesome colour!");

    }
}
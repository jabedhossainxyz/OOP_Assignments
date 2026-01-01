package com.npc;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Fairy fairy = new Fairy();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nPlease select the interaction with the fairy:");
            System.out.println("1. Talk to her");
            System.out.println("2. Flattery her");
            System.out.println("3. Curse her");
            System.out.println("4. Exchange equipment with her");
            System.out.println("5. Give her a gift");
            System.out.print("Your input is: ");

            if (!scanner.hasNextLine()) {
                break;
            }
            
            String input = scanner.nextLine();
            
            // Basic input validation
            int choice = -1;
            try {
                choice = Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    fairy.talk();
                    break;
                case 2:
                    fairy.flattery();
                    break;
                case 3:
                    fairy.curse();
                    break;
                case 4:
                    fairy.exchange();
                    break;
                case 5:
                    fairy.giveGift();
                    break;
                default:
                    System.out.println("Invalid selection. Please choose 1-5.");
            }
        }
        scanner.close();
    }
}

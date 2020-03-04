package main.java.webcrawler;

import javafx.application.Application;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a starting URL : ");
        String startUrl = scanner.nextLine().trim();
        System.out.print("Enter a word to search for : ");
        String word = scanner.nextLine().trim();
        System.out.print("Enter the maximum amount of pages the crawler should visit : ");
        int amount = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Should the crawler save the links with hits? (Y/N) : ");
        boolean save = getChoice(scanner.nextLine());
        System.out.print("Do you want to enable debug mode? (Y/N) : ");
        boolean debug = getChoice(scanner.nextLine());
        if (debug) System.out.println("[INFO] - Debug mode enabled");
        WebCrawler crawler = new WebCrawler(amount,save,debug);
        Application.launch(Visualiser.class);
        crawler.search(startUrl,word);
    }

    private static boolean getChoice(String choice) {
        if (choice.trim().toLowerCase().equals("y")) return true;
        else return false;
    }
}

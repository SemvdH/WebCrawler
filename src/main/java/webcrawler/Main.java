package main.java.webcrawler;

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
        String choice = scanner.nextLine().toLowerCase().trim();
        boolean save = getChoice(choice);

        System.out.print("Do you want to enable debug mode? (Y/N) : ");
        WebCrawler crawler = new WebCrawler(amount,save);
        crawler.search(startUrl,word);
    }

    private static boolean getChoice(String choice) {
        if (choice.trim().toLowerCase().equals("y")) return true;
        else return false;
    }
}

package main.java.webcrawler.crawler;

import main.java.webcrawler.visualiser.Visualiser;

import java.util.LinkedList;

public class CrawlThread extends Thread {

    private final int amount;
    private boolean debug;
    private final String startUrl;
    private final String word;
    private WebCrawler crawler;
    private Visualiser visualiser;

    public CrawlThread(int amount, boolean debug, String startUrl, String word, Visualiser visualiser) {
        this.amount = amount;
        this.debug = debug;
        this.startUrl = startUrl;
        this.word = word;
        this.visualiser = visualiser;
        this.crawler = new WebCrawler(amount, true, debug, visualiser);

    }

    public void run() {
//        this.debug = false;
        System.out.println("starting thread");
        this.crawler.search(startUrl, word);
    }

    public WebCrawler getCrawler() {
        return crawler;
    }

    public int getAmount() {
        return amount;
    }

    public boolean isDebug() {
        return debug;
    }

    public String getStartUrl() {
        return startUrl;
    }

    public String getWord() {
        return word;
    }

    public LinkedList<String> retrieveLog() {
        return this.crawler.getMessages();
    }


}

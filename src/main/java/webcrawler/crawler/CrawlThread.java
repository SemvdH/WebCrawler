package main.java.webcrawler.crawler;

import main.java.webcrawler.visualiser.Visualiser;

public class CrawlThread extends Thread {

    private final int amount;
    private final boolean debug;
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

    }

    public void run() {
        this.crawler = new WebCrawler(amount, true, debug,visualiser);
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
}

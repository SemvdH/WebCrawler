package main.java.webcrawler.crawler;

public class CrawlThread extends Thread {

    private final int amount;
    private final boolean save;
    private final boolean debug;
    private final String startUrl;
    private final String word;
    private WebCrawler crawler;

    public CrawlThread(int amount, boolean save, boolean debug, String startUrl, String word) {
        this.amount = amount;
        this.save = save;
        this.debug = debug;
        this.startUrl = startUrl;
        this.word = word;

    }

    public void run() {
        this.crawler = new WebCrawler(amount, save, debug);
        this.crawler.search(startUrl, word);
    }

    public WebCrawler getCrawler() {
        return crawler;
    }

    public int getAmount() {
        return amount;
    }

    public boolean isSave() {
        return save;
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

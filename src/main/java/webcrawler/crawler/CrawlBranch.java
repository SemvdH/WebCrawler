package main.java.webcrawler.crawler;

import main.java.webcrawler.visualiser.Visualiser;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class CrawlBranch {
    private final Visualiser logger;
    private List<String> links = new LinkedList<>();
    private Document htmlDocument;
    private boolean debug;
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";

    private WebCrawler parent;

    public CrawlBranch() {
        this(false,null,null);
    }

    public CrawlBranch(boolean debug, WebCrawler parent,Visualiser logger) {
        this.debug = debug;
        this.logger = logger;
        this.parent = parent;
    }

    /**
     * crawls the links in it's current arrayList of links
     *
     * @param url the url to start from
     * @return <code>true</code> if the search was successful, <code>false otherwise</code>
     */
    public boolean crawl(String url) {
        try {
            Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
            this.htmlDocument = connection.get();

            if (connection.response().statusCode() == 200) {
//                print("VISITING -- Recieved web page at " + url);
                sendMessage("VISITING -- Recieved web page at " + url);
            } else {
//                print("FAIL -- recieved something else than a web page");
                sendMessage("FAIL -- recieved something else than a web page");
                return false;
            }

            Elements linksOnPage = htmlDocument.select("a[href]");
//            print("FOUND (" + linksOnPage.size() + ") links");
            sendMessage("FOUND (" + linksOnPage.size() + ") links");
            for (Element link : linksOnPage) {
                this.links.add(link.absUrl("href"));
            }
            return true;
        } catch (Exception e) {
            //System.out.println("ERROR -- error in out http request : " + e);
            return false;
        }
    }

    /**
     * searches how many times a word occurs in a page
     *
     * @param word the word to look for
     * @return the amount of occurrences in the web page, -1 if the word is not found
     */
    public int searchForWord(String word) {
        if (this.htmlDocument == null) {
            //System.out.println("ERROR -- call crawl before searhing");
            return -1;
        }
//        print(String.format("Searching for %s...", word));
        sendMessage(String.format("Searching for %s...", word));
        String bodyText = this.htmlDocument.body().text();
        return count(bodyText.toLowerCase(), word.toLowerCase());
    }

    /**
     * counts how many times a word occurs in a string
     *
     * @param text the string to search in for the word
     * @param word the word to search for
     * @return the amount of times the given word was found in the string
     */
    private int count(String text, String word) {
        int amount = 0;
        String[] words = text.split(" ");
        for (int i = 0; i < words.length; i++) {
            if (words[i].contains(word)) amount++;
        }
        return amount;
    }

    /**
     * gets the links
     *
     * @return the links
     */
    public List<String> getLinks() {
        return this.links;
    }

    private void print(String text) {
        if (debug) logger.log(text);
    }

    private void sendMessage(String message) {
        this.parent.addMessage(message);
    }
}

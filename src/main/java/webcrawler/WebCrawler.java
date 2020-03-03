package main.java.webcrawler;

import java.util.*;

public class WebCrawler {
    private int amountOfPages;
    private Set<String> pagesVisited;
    private List<String> pagesPending;
    private ArrayList<String> resultPages;
    private Map<String,Integer> urlHits;
    private int amountFound = 0;
    private int successPages = 0;
    private boolean shouldSaveHitLinks;

    /**
     * creates a new WebCrawler object with standard values
     */
    public WebCrawler() {
        this(50, true);
    }

    /**
     * creates a new WebCrawler object with the given amount of max pages
     * @param maxPages the max amount of pages the crawler should visit
     */
    public WebCrawler(int maxPages) {
        this(maxPages, true);
    }

    /**
     * creates a new WebCrawler object with the given amount of max pages, and if it should save the hit URLs
     * @param maxPages the max amount of pages the crawler should visit
     * @param shouldSaveHitLinks if the crawler should save the links that have one or more hits
     */
    public WebCrawler(int maxPages, boolean shouldSaveHitLinks) {
        this.amountOfPages = maxPages;
        this.shouldSaveHitLinks = shouldSaveHitLinks;
        this.pagesVisited = new HashSet<>();
        this.pagesPending = new LinkedList<>();
        this.resultPages = new ArrayList<>();
        this.urlHits = new HashMap<>();
    }


    /**
     * gets the next url in the list
     * @return the next url in the list
     */
    private String nextUrl() {
        String next;
        do {
            next = this.pagesPending.remove(0);
        } while (this.pagesVisited.contains(next));
        this.pagesVisited.add(next);
        return next;
    }

    /**
     * searches for a word by crawling the web through hyperlinks
     * @param url the url to start searching from
     * @param searchWord the word to search for
     */
    public void search(String url, String searchWord) {
        while (this.pagesVisited.size() < amountOfPages) {
            String curUrl;
            CrawlBranch branch = new CrawlBranch();
            if (this.pagesPending.isEmpty()) {
                curUrl = url;
                this.pagesVisited.add(url);
            } else {
                curUrl = this.nextUrl();
            }
            branch.crawl(curUrl);

            int amount = branch.searchForWord(searchWord);
            if (amount > 0) {
                System.out.printf("SUCCESS -- word %s found at %s %s times", searchWord, curUrl, amount);
                System.out.println();
                successPages++;
                amountFound += amount;
                if (shouldSaveHitLinks)
                resultPages.add(curUrl);
                urlHits.put(curUrl,amount);
            }
            this.pagesPending.addAll(branch.getLinks());
        }
        System.out.println("========================");
        System.out.printf("DONE -- Visited %s webpages\nHits: %s\nAmount of pages with hits: %s\n", this.pagesVisited.size(), amountFound, successPages);
        if (shouldSaveHitLinks) {
            System.out.printf("Successful pages: \n%s", showCombinations(urlHits));
        }
    }

    private String display(List<String> list) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            res.append(list.get(i)).append("\n");
        }
        return res.toString();
    }

    private String showCombinations(Map<String, Integer> urls) {
        StringBuilder res = new StringBuilder();
        Set<String> keys = urls.keySet();
        for (String url : keys) {
            res.append(url).append(" (").append(urls.get(url)).append(" hits)\n");
        }
        return res.toString();
    }

    /**
     * sets the amount of max pages
     * @param amount the amount of pages
     */
    public void setAmountOfPages(int amount) {
        this.amountOfPages = amount;
    }

    public void setShouldSaveHitLinks(boolean shouldSaveHitLinks) {
        this.shouldSaveHitLinks = shouldSaveHitLinks;
    }

    public ArrayList<String> getResultPages() {
        return this.resultPages;
    }

    public Map<String, Integer> getUrlHits() {
        return urlHits;
    }

    public int getAmountFound() {
        return amountFound;
    }

    /**
     * clears the crawler
     */
    public void clear() {
        this.urlHits.clear();
        this.resultPages.clear();
        this.pagesPending.clear();
        this.pagesVisited.clear();
        this.successPages = 0;
        this.amountFound = 0;
    }
}

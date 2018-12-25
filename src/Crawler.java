import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Crawler {

  private static final int MAX_PAGES = 100;
  private Set<String> visited = new HashSet<>();
  private List<String> toVisit = new ArrayList<>();

  public static void main(String[] args) {
    String url = args[0];
    try {
      new Crawler().followLinks(url);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /*  Loops through all strings in the list of strings yet to be visited
   *  If it is unique and followable, e.g. an explicit link and not a relative
   *  link then it will be return as the next link to be followed.
   */
  private String getNextToVisit() {
    String nextURL;
    do {
      nextURL = toVisit.remove(0);
    } while (visited.contains(nextURL) || !nextURL.contains("http"));

    visited.add(nextURL);
    System.out.println(nextURL);

    return nextURL;
  }

  /*  This is the function that sets out the method for the crawler
   *  If there are no URLs to visit then it will use the url that's been passed
   *  in, if not it will then call getNextToVisit() and crawl on that
   */
  public void followLinks(String url) throws Exception {
    while (visited.size() <= MAX_PAGES) {
      String currentURL;
      CrawlerUtil crawlerUtil = new CrawlerUtil(url);

      if (this.toVisit.isEmpty()) {
        currentURL = url;
        visited.add(currentURL);
      } else {
        currentURL = this.getNextToVisit();
      }

      crawlerUtil.crawl(currentURL);
      toVisit.addAll(crawlerUtil.getLinks());
    }
  }
}

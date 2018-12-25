import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CrawlerUtil {

  private List<String> links = new LinkedList<>();
  private String html;
  private String url;

  public CrawlerUtil(String url) {
    this.url = url;
  }

  /* This function downloads the website and then does a basic
   * extraction of all the <a> tags.
   */
  public void download() throws Exception {
    URL webpage = new URL(url);

    BufferedReader in = new BufferedReader(new InputStreamReader(webpage.openStream()));
    String line;
    String allLinks = "";
    StringBuilder stringBuilder = new StringBuilder(allLinks);

    while ((line = in.readLine()) != null)
      if (line.contains("<a")) {
        stringBuilder.append(line);
      }
    in.close();
    this.html = stringBuilder.toString();
  }

  /* This uses a regular expression to fully extract all the HTML links which
   * are then added to the list of links
   */
  public void extractLinks() {
    String regex = "<a href\\s?=\\s?\"([^\"]+)\">";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(html);

    int i = 0;
    while (matcher.find(i)) {
      String link = matcher.group(1);
      this.links.add(link);
      i = matcher.end();
    }
  }

  public List<String> getLinks() {
    return this.links;
  }

  /* Creates a crawler util that will crawl on the given url, the links it
   * finds are then added to the list which is then retrieved by the caller
   * function. Currently if a link can't be opened "Page not found" is printed
   * to the terminal
   */
  public void crawl(String url) {
    CrawlerUtil crawlerUtil = new CrawlerUtil(url);
    
    try {
      crawlerUtil.download();

      crawlerUtil.extractLinks();

      for (String link : crawlerUtil.getLinks()) {
        this.links.add(link);
      }

    } catch (Exception e) {
      System.out.println("Page Not found");
    }
  }
}

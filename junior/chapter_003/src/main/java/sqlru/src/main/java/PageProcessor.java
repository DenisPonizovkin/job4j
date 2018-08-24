import model.Ad;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

public class PageProcessor {

    private final static Logger LOGGER = Logger.getLogger(PageProcessor.class);
    private final Map<String, String> mapMonthToNum;

    public PageProcessor() {
        mapMonthToNum = new HashMap<String, String>();
        mapMonthToNum.put("янв", "01");
        mapMonthToNum.put("фев", "02");
        mapMonthToNum.put("мар", "03");
        mapMonthToNum.put("апр", "04");
        mapMonthToNum.put("май", "05");
        mapMonthToNum.put("июн", "06");
        mapMonthToNum.put("июл", "07");
        mapMonthToNum.put("авг", "08");
        mapMonthToNum.put("сен", "09");
        mapMonthToNum.put("окт", "10");
        mapMonthToNum.put("ноя", "11");
        mapMonthToNum.put("дек", "12");
    }

    public Document run(String url) throws IOException {
        return Jsoup.connect(url).get();
    }

    public List<Ad> getJavaAdsFromPage(String url) throws IOException {
        Document page = run(url);
        List<Ad> ads = new ArrayList<Ad>();
        for (Element e : page.getElementsByTag("tr")) {
            Elements tds = e.getElementsByTag("td");
            if (tds.size() != 6) {
                continue;
            }
            LOGGER.debug("Element: " + e);
            Ad ad = new Ad();
            String theme = tds.get(1).toString().split(">")[2].replace("</a", "");
            if ((!theme.toLowerCase().contains("java")) || (theme.toLowerCase().contains("script"))) {
                continue;
            }
            ad.setTheme(theme);
            ad.setAuthor(tds.get(2).toString().split(">")[2].replace("</a", ""));
            ad.setAnswersNumber(Integer.parseInt(
                    tds.get(3).toString().split(">")[1].replace("</td", ""))
            );
            ad.setViewsNumber(Integer.parseInt(
                    tds.get(4).toString().split(">")[1].replace("</td", ""))
            );
            String dateTime = tds.get(5).toString().split(">")[1].replace("</td", "");
            if (!(dateTime.toLowerCase().contains("вчера") || dateTime.toLowerCase().contains("сегодня"))) {
                ad.setDateTime(strMonthToNum(dateTime));
            } else {
                ad.setDateTime(createDateTime(dateTime));
            }
            ads.add(ad);
        }
        LOGGER.debug("Find " + ads.size() + " new ads");
        return ads;
    }

    private String strMonthToNum(String dateTime) {
        for (Map.Entry<String, String> e: mapMonthToNum.entrySet()) {
           dateTime = dateTime.replace(e.getKey(), e.getValue());
        }
        return dateTime;
    }

    private String createDateTime(String dateTime) {
        int minusDay = 0;
        if (dateTime.toLowerCase().contains("вчера")) {
            minusDay = -1;
        }
        List<String> dateTimeParts = Arrays.asList(dateTime.split(" "));
        int day = DateTime.now().getDayOfMonth() + minusDay;
        int month = DateTime.now().getMonthOfYear();
        String dayStringPrefix = (day < 10) ? "0" : "";
        String monthStringPrefix = (month < 10) ? "0" : "";
        return dayStringPrefix + day + " "
                + monthStringPrefix + month
                + " " + DateTime.now().getYear() + ","
                + dateTime.split(",")[1];
    }
}

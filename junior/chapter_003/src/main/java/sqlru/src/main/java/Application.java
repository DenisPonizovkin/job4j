import model.Ad;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.List;

public class Application implements Job {

    private final Logger logger = Logger.getLogger(Application.class);
    private final PageProcessor pager = new PageProcessor();

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        logger.debug("Start SQLRU Application");

        try {
            if (firstTime()) {
                logger.debug("First time run");
                getAllPagesForThisYear();
            } else {
                getNewAds();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getNewAds() throws Exception {
        String url = "http://www.sql.ru/forum/job-offers";
        logger.debug("Get page by url: " + url);
        List<Ad> ads = pager.getJavaAdsFromPage(url);
        for (Ad ad : ads) {
            insertAd(ad);
        }
    }

    private void insertAd(Ad ad) throws Exception {
        try (DbDriver db = new DbDriver()) {
            db.init();
            db.insert(ad);
        }
    }

    private void getAllPagesForThisYear() throws Exception {
        int i = 0;
        boolean next = true;
        while (next) {
            String url = "http://www.sql.ru/forum/job-offers";
            logger.debug("Get page by url: " + url);
            if (++i > 1) {
                url += "/" + i;
            }
            List<Ad> ads = pager.getJavaAdsFromPage(url);
            for (Ad ad : ads) {
                if (ad.isNotFromThisYear()) {
                    next = false;
                    break;
                }
                insertAd(ad);
            }
        }
    }

    private boolean firstTime() throws Exception {
        try (DbDriver db = new DbDriver()) {
            db.init();
            return db.vacanciesNumber() == 0;
        }
    }
}

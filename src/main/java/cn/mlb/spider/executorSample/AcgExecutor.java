package cn.mlb.spider.executorSample;

import cn.mlb.spider.entity.AcgItem;
import cn.mlb.spider.entity.Category;
import cn.mlb.spider.mapper.AcgItemMapper;
import cn.mlb.spider.mapper.MediaMapper;
import cn.mlb.spider.sourceGraber.HtmlGrabber;
import cn.mlb.spider.sourceGraber.HtmlParser;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Classname AcgExecutor
 * @Description TODO
 * @Date 2020/11/9 17:50
 * @Created by zhaochen
 */
public class AcgExecutor {
    private static Logger logger = Logger.getLogger(AcgExecutor.class);

    private AcgItemMapper acgItemMapper;
    private SqlSessionFactory factory;

    public static void main(String[] args) throws ParseException, IOException,
            InterruptedException {
        AcgExecutor executor = new AcgExecutor();
        executor.init();
        executor.go();

    }

    private void init() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        factory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = factory.openSession(true);
        acgItemMapper = session.getMapper(AcgItemMapper.class);
    }

    private void go() throws ParseException, InterruptedException {
        // 创建网页抓取器
        HtmlGrabber grabber = new HtmlGrabber();
        HtmlParser parser = new HtmlParser();


        ArrayList<Category> categories = new ArrayList<>();
        categories.add(new Category("高达", 285, "4_1_0_0_0"));
        categories.add(new Category("变形金刚", 385, "3_1_0_0_0"));
        categories.add(new Category("海贼王", 74, "8_1_0_0_0"));
        categories.add(new Category("圣斗士", 26, "2_1_0_0_0"));
        categories.add(new Category("星战", 196, "13_1_0_0_0"));
        categories.add(new Category("手办", 646, "5_1_0_0_0"));
        categories.add(new Category("成品机甲", 92, "6_1_0_0_0"));
        categories.add(new Category("科幻拼装", 155, "7_1_0_0_0"));
        categories.add(new Category("特摄", 167, "11_1_0_0_0"));
        categories.add(new Category("美系", 955, "10_1_0_0_0"));
        categories.add(new Category("扭蛋", 65, "12_1_0_0_0"));
        categories.add(new Category("GK", 24, "9_1_0_0_0"));
        categories.add(new Category("军模", 382, "14_1_0_0_0"));
        categories.add(new Category("潮玩", 77, "15_1_0_0_0"));
        categories.add(new Category("其他", 0, "1_1_0_0_0"));


        for (Category category : categories) {
            for (int i = 1; i <= category.getPageCount(); i++) {
                StringBuffer sb = new StringBuffer();
                sb.append("http://acg.78dm.net/p/").append(category.getSubUrl()).append("/").append(i).append(".html");
                String listUrl = sb.toString();
                List<String> htmls = grabber.getHtmls(new String[]{listUrl}, 1);
                if (htmls.size() == 0) {
                    logger.warn(String.format("No content category=%s, pageNum=%s", category.getName(), i));
                }
                String html = htmls.get(0);
                List<String> titleList = parser.getTextList(html, ".inner-title p");
                List<String> urlList = parser.getAttrList(html, ".is-2 .inner a", "href");
                List<String> iconImageUrlList = parser.getAttrList(html, ".is-2 .inner img", "data-src");
                for(int j = 0; j < titleList.size();j++){
                    AcgItem acgItem = new AcgItem();
                    acgItem.setStatus("0");
                    acgItem.setCategory(category.getName());
                    acgItem.setTitle(titleList.get(j));
                    acgItem.setUrl(urlList.get(j));
                    acgItem.setPageNumber(i);
                    acgItem.setIconImageUrl(iconImageUrlList.get(j));
                    acgItemMapper.save(acgItem);
                }
            }
        }
        // 创建url数组
        /*String[] urls = new String[5];
        // 以时间作为封面图片文件夹名
        String folderName = new SimpleDateFormat("yyyyMMdd").format(new Date());
        for (int i = 0; i < urls.length; i++) {
            urls[i] = "http://www.fcw30.com/most-popular/" + (i + 1 + 322)
                    + "/";
        }
        List<String> htmls = grabber.getHtmls(urls, 1);
        HtmlParser parser = new HtmlParser();
        int page = 0;
        for (String html : htmls) {
            logger.info("开始第" + page + "页");
            List<String> hrefs = parser.getAttrList(html,
                    "#list_videos_common_videos_list_items .item a", "href");
            List<String> imgs = parser.getAttrList(html,
                    "#list_videos_common_videos_list_items .item .img img",
                    "data-original");
            List<String> titles = parser.getTextList(html,
                    "#list_videos_common_videos_list_items .item a .title");
            List<String> durations = parser.getTextList(html,
                    "#list_videos_common_videos_list_items .duration");
            List<String> ratings = parser.getTextList(html,
                    "#list_videos_common_videos_list_items .rating");
            ArrayList<String> videoUrls = new ArrayList<String>();
            ArrayList<String> sizes = new ArrayList<String>();
            String[] hrefArr = hrefs.toArray(new String[1]);
            List<String> vedioHtmls = grabber.getHtmls(hrefArr, 1);
            for (String vedioHtml : vedioHtmls) {
                String vedilUrl = parser.getAttrList(vedioHtml,
                        "div.item[style] a", "href").get(0);
                String size = parser
                        .getTextList(vedioHtml, "div.item[style] a").get(0);
                videoUrls.add(vedilUrl);
                sizes.add(size);
            }
            for (int i = 0; i < videoUrls.size(); i++) {
                Media media = new Media();
                media.setTitle(titles.get(i));
                media.setUrl(videoUrls.get(i));
                media.setCategory("最受欢迎");
                media.setImg(imgs.get(i));
                String durationStr = durations.get(i);
                Date duration = new SimpleDateFormat("m分:s秒")
                        .parse(durationStr);// 58分:27秒
                media.setDuration(new Time(duration.getTime()));
                String sizeStr = sizes.get(i);
                String[] split = sizeStr.split(" ");
                media.setFileSize(Double.valueOf(split[1]));
                String ratingStr = ratings.get(i);
                ratingStr = ratingStr.substring(0, ratingStr.length() - 1);
                Double rating = Double.valueOf(ratingStr);
                media.setRating(rating / 100);
                String imgLoc = folderName + "/" + titles.get(i) + ".jpg";
                media.setImgLoc(imgLoc);
                this.mediaMapper.save(media);
                logger.info("存入数据" + media.getId() + ":" + media.getTitle());
            }

            // 下载封面图片
            logger.info("开始下载图片");
            for (int j = 0; j < imgs.size(); j++) {
                new Thread(new ImgDownloader(imgs.get(j), folderName,
                        "D:/fcw30", null, titles.get(j) + ".jpg")).start();
            }
            logger.info("图片下载完毕");
            page++;
            Thread.sleep(600);

        }*/
    }
}

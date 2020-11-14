package cn.mlb.spider.executorSample;

import cn.mlb.spider.entity.AcgFactory;
import cn.mlb.spider.mapper.AcgFactoryMapper;
import cn.mlb.spider.sourceGraber.HtmlGrabber;
import cn.mlb.spider.sourceGraber.HtmlParser;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.List;

/**
 * @Classname AcgExecutor
 * @Description TODO
 * @Date 2020/11/9 17:50
 * @Created by zhaochen
 */
public class AcgFactoryExecutor {
    private static Logger logger = Logger.getLogger(AcgFactoryExecutor.class);

    private AcgFactoryMapper acgFactoryMapper;
    private SqlSessionFactory factory;

    public static void main(String[] args) throws ParseException, IOException {
        AcgFactoryExecutor executor = new AcgFactoryExecutor();
        executor.init();
        //executor.go();
        executor.completion();
    }

    private void init() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        factory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = factory.openSession(true);
        acgFactoryMapper = session.getMapper(AcgFactoryMapper.class);
    }

    private void completion() {
        HtmlGrabber grabber = new HtmlGrabber();
        HtmlParser parser = new HtmlParser();
        try {
            while (true) {
                AcgFactory factory = acgFactoryMapper.getFirstIncompleteFactory();
                if (factory == null) {
                    logger.info("spider finished");
                    return;
                }
                List<String> htmls = grabber.getHtmls(new String[]{"http://acg.78dm.net" + factory.getUrl()}, 1);
                if (htmls.size() == 0) {
                    logger.warn(String.format("No content title=%s, url=%s", factory.getTitle(), factory.getUrl()));
                }
                String html = htmls.get(0);
                String introduction = parser.getTextList(html, "#introduction p").get(0);
                List<String> fieldList = parser.getTextList(html, ".is-fullwidth .table-header");
                List<String> contentList = parser.getTextList(html, ".is-fullwidth .table-header+td");
                String bannerImageUrl = parser.getAttrList(html, ".image .lazy", "data-src").get(0);

                for (int i = 0; i < fieldList.size(); i++) {
                    switch (fieldList.get(i)) {
                        case "类型":
                            factory.setType(contentList.get(i));
                            break;
                        case "参与创建":
                            factory.setCreator(contentList.get(i));
                            break;
                    }
                }
                String series = parseSeries(html);
                logger.info("series length="+series.length());
                factory.setSeries(series);
                factory.setIntroduce(introduction);
                factory.setBannerImageUrl(bannerImageUrl);
                factory.setStatus("1");
                acgFactoryMapper.update(factory);
            }
        } catch (Exception e) {
            logger.error("ERROR", e);
            throw e;
        }

    }

    private String parseSeries(String html) {
        StringBuilder sb = new StringBuilder();
        Document document = Jsoup.parse(html);
        // 通过选择器获取元素
        Elements elements = document.select("#toys .show-list .list-title");
        for (Element title : elements) {
            sb.append(title.text()).append("\r\n");
            Element content = title.nextElementSibling();
            Elements children = content.children();
            for (Element child : children) {
                sb.append(child.text()).append("\r\n");
            }
            sb.append("\r\n");
        }
        if (sb.length() > 2) sb.delete(sb.length() - 2, sb.length());
        return sb.toString();
    }

    private void go() throws ParseException, InterruptedException {
        // 创建网页抓取器
        HtmlGrabber grabber = new HtmlGrabber();
        HtmlParser parser = new HtmlParser();

        String homeUrl = "http://acg.78dm.net/factory.html";
        List<String> htmls = grabber.getHtmls(new String[]{homeUrl}, 1);
        if (htmls.size() == 0) {
            logger.warn("No content for home url");
        }
        String html = htmls.get(0);
        List<String> titleList = parser.getTextList(html, ".databank-initials-columns .item-title");
        List<String> urlList = parser.getAttrList(html, ".databank-initials-columns .item-title", "href");
        for (int j = 0; j < titleList.size(); j++) {
            AcgFactory acgFactory = new AcgFactory();
            acgFactory.setStatus("0");
            acgFactory.setTitle(titleList.get(j));
            acgFactory.setUrl(urlList.get(j));
            acgFactoryMapper.save(acgFactory);
        }
    }
}

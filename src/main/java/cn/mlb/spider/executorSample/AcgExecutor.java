package cn.mlb.spider.executorSample;

import cn.mlb.spider.entity.AcgItem;
import cn.mlb.spider.mapper.AcgItemMapper;
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
public class AcgExecutor {
    private static Logger logger = Logger.getLogger(AcgExecutor.class);

    private AcgItemMapper acgItemMapper;
    private SqlSessionFactory factory;

    public static void main(String[] args) throws ParseException, IOException,
            InterruptedException {
        AcgExecutor executor = new AcgExecutor();
        executor.init();
        //executor.go();
        executor.completion();


    }

    private void init() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        factory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = factory.openSession(true);
        acgItemMapper = session.getMapper(AcgItemMapper.class);
    }

    private void completion() {
        HtmlGrabber grabber = new HtmlGrabber();
        HtmlParser parser = new HtmlParser();
        try {
            while (true) {
                AcgItem item = acgItemMapper.getFirstIncompleteItem();
                if (item == null) {
                    logger.info("spider finished");
                    return;
                }
                List<String> htmls = grabber.getHtmls(new String[]{"http://acg.78dm.net" + item.getUrl()}, 1);
                if (htmls.size() == 0) {
                    logger.warn(String.format("No content category=%s, pageNum=%s, title=%s", item.getCategory(), item.getPageNumber(), item.getTitle()));
                }
                String html = htmls.get(0);
                List<String> fieldList = parser.getTextList(html, ".is-fullwidth .table-header");
                List<String> contentList = parser.getTextList(html, ".is-fullwidth .table-header+td");
                String bannerImageUrl = parser.getAttrList(html, ".large-image .lazy", "data-src").get(0);


                for (int i = 0; i < fieldList.size(); i++) {
                    switch (fieldList.get(i)) {
                        case "厂商":
                            item.setProducer(contentList.get(i));
                            break;
                        case "系列":
                            item.setSeries(contentList.get(i));
                            break;
                        case "级别或分类":
                            item.setLevel(contentList.get(i));
                            break;
                        case "名称":
                            item.setName(contentList.get(i));
                            break;
                        case "版本":
                            item.setVersion(contentList.get(i));
                            break;
                        case "材料种类":
                            item.setMaterialType(contentList.get(i));
                            break;
                        case "玩具分类":
                            item.setToyClass(contentList.get(i));
                            break;
                        case "参与创建":
                            item.setParticipator(contentList.get(i));
                            break;
                        case "发售时间":
                            item.setReleaseDate(contentList.get(i));
                            break;
                        case "比例":
                            item.setRate(contentList.get(i));
                            break;
                        case "规格":
                            item.setSpec(contentList.get(i));
                            break;
                        case "价格":
                            item.setPrice(contentList.get(i));
                            break;
                        case "人物名":
                            item.setPersonName(contentList.get(i));
                            break;
                        case "原型师":
                            item.setProtoPerson(contentList.get(i));
                            break;
                        case "编号":
                            item.setNumber(contentList.get(i));
                            break;
                    }
                }
                item.setBannerImageUrl(bannerImageUrl);
                item.setStatus("1");
                acgItemMapper.update(item);
            }
        } catch (Exception e) {
            logger.error("ERROR", e);
            throw e;
        }

    }

   /* private void go() throws ParseException, InterruptedException {
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
    }*/
}

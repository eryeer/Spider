package cn.mlb.spider.output;

import cn.mlb.spider.entity.AcgItem;
import cn.mlb.spider.entity.Category;
import cn.mlb.spider.executorSample.AcgExecutor;
import cn.mlb.spider.mapper.AcgItemMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Classname AcgOutput
 * @Description TODO
 * @Date 2020/11/10 17:58
 * @Created by zhaochen
 */
public class AcgOutput {
    private static Logger logger = Logger.getLogger(AcgExecutor.class);

    private AcgItemMapper acgItemMapper;
    private SqlSessionFactory factory;

    public static void main(String[] args) throws ParseException, IOException,
            InterruptedException {
        AcgOutput output = new AcgOutput();
        output.init();
        output.outputCsv();
    }

    private void init() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        factory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = factory.openSession(true);
        acgItemMapper = session.getMapper(AcgItemMapper.class);
    }

    private void outputCsv() throws IOException {
        List<Category> categories = setCategory();
        for (Category category : categories) {
            outputCsvByCategory(category.getName());
            logger.info(String.format("category %s output csv finished", category.getName()));
        }
    }

    private void outputCsvByCategory(String category) throws IOException {
        try (CSVPrinter printer = new CSVPrinter(new FileWriter("D:/acgFile/" + category + ".csv"), CSVFormat.EXCEL)) {
            printer.printRecord("ID", "标题", "类别", "所在页码", "厂商", "系列", "级别或分类", "名称", "版本", "材料种类", "玩具分类", "参与创建", "发售时间", "比例", "规格", "价格", "人物名", "原型师", "编号", "首图地址");
            int pageNo = 1;
            int pageSize = 100;
            while (true) {
                List<AcgItem> acgItemList = queryAcgItemByCategory(pageNo, pageSize, category);
                if (acgItemList == null || acgItemList.size() == 0) break;
                for (AcgItem item : acgItemList) {
                    printer.printRecord(item.getId(), item.getTitle(), item.getCategory(), item.getPageNumber(), item.getProducer(), item.getSeries(), item.getLevel(), item.getName(), item.getVersion(), item.getMaterialType(),
                            item.getToyClass(), item.getParticipator(), item.getReleaseDate(), item.getRate(), item.getSpec(), item.getPrice(), item.getPersonName(), item.getProtoPerson(), item.getNumber(),
                            StringUtils.startsWith(item.getBannerImageUrl(), "http") ? item.getBannerImageUrl() : "");
                }
                printer.flush();
                pageNo++;
            }
        } catch (IOException ex) {
            logger.error("error", ex);
            throw ex;
        }
    }

    private List<AcgItem> queryAcgItemByCategory(Integer pageNo, Integer pageSize, String Category) {
        PageHelper.startPage(pageNo, pageSize);
        List<AcgItem> acgItemList = this.acgItemMapper.queryAcgItemByCategory(Category);
        PageInfo<AcgItem> pageInfo = new PageInfo<>(acgItemList);
        return pageInfo.getList();
    }

    private List<Category> setCategory() {
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
        return categories;
    }
}

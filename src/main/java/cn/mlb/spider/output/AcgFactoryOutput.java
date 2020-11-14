package cn.mlb.spider.output;

import cn.mlb.spider.entity.AcgFactory;
import cn.mlb.spider.entity.Category;
import cn.mlb.spider.executorSample.AcgExecutor;
import cn.mlb.spider.mapper.AcgFactoryMapper;
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
public class AcgFactoryOutput {
    private static Logger logger = Logger.getLogger(AcgExecutor.class);

    private AcgFactoryMapper acgFactoryMapper;
    private SqlSessionFactory factory;

    public static void main(String[] args) throws ParseException, IOException,
            InterruptedException {
        AcgFactoryOutput output = new AcgFactoryOutput();
        output.init();
        output.outputCsv();
    }

    private void init() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        factory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = factory.openSession(true);
        acgFactoryMapper = session.getMapper(AcgFactoryMapper.class);
    }

    private void outputCsv() throws IOException {
        try (CSVPrinter printer = new CSVPrinter(new FileWriter("D:/acgFile/厂商.csv"), CSVFormat.EXCEL)) {
            printer.printRecord("ID", "标题", "简介", "类型", "参与创建", "玩具系列", "首图地址");
            int pageNo = 1;
            int pageSize = 100;
            while (true) {
                List<AcgFactory> acgFactoryList = queryAcgFactory(pageNo, pageSize);
                if (acgFactoryList == null || acgFactoryList.size() == 0) break;
                for (AcgFactory factory : acgFactoryList) {
                    printer.printRecord(factory.getId(), factory.getTitle(), factory.getIntroduce(), factory.getType(), factory.getCreator(), factory.getSeries(),
                            StringUtils.startsWith(factory.getBannerImageUrl(), "http") ? factory.getBannerImageUrl() : "");
                }
                printer.flush();
                pageNo++;
            }
        } catch (IOException ex) {
            logger.error("error", ex);
            throw ex;
        }
    }

    private List<AcgFactory> queryAcgFactory(Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        List<AcgFactory> acgFactoryList = this.acgFactoryMapper.queryAcgFactory();
        PageInfo<AcgFactory> pageInfo = new PageInfo<>(acgFactoryList);
        return pageInfo.getList();
    }
}

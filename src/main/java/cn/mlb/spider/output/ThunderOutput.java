package cn.mlb.spider.output;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;

import cn.mlb.spider.entity.Media;
import cn.mlb.spider.executorSample.Fcw30Executor;
import cn.mlb.spider.mapper.MediaMapper;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

public class ThunderOutput {
	private static Logger logger = Logger.getLogger(Fcw30Executor.class);

	private MediaMapper mediaMapper;
	private SqlSessionFactory factory;

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		ThunderOutput output = new ThunderOutput();
		output.init();
		for (int i = 1; i <= 74; i++) {
			List<Media> result = output.queryResult(i, 100);
			logger.info("第" + i + "页, 每页条数" + 100 + ", 开始打印...");
			output.print(result, "page" + i);
			logger.info("第" + i + "页, 每页条数" + 100 + ", 开始完毕");
		}
	}

	public void init() throws IOException {
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		factory = new SqlSessionFactoryBuilder().build(inputStream);
		SqlSession session = factory.openSession(true);
		mediaMapper = session.getMapper(MediaMapper.class);
	}

	public void print(List<Media> result, String fileName) throws IOException {
		String folder = "D:/downloadlist";
		File folderFile = new File(folder);
		if (!folderFile.exists()) {
			folderFile.mkdirs();
		}
		String path = folder + "/" + fileName + ".downlist";
		File file = new File(path);
		BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		for (Media media : result) {
			String url = media.getUrl();
			String title = media.getTitle();
			int lastIndexOf = url.lastIndexOf("&");
			url = url.substring(0, lastIndexOf);
			url += "&download_filename=" + title + ".mp4";
			bw.write(url);
			bw.newLine();
		}
		bw.flush();
		bw.close();
	}

	public List<Media> queryResult(Integer pageNo, Integer pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		List<Media> mediaList = this.mediaMapper.queryAllOrderByRatingDesc();
		PageInfo<Media> pageInfo = new PageInfo<Media>(mediaList);
		return pageInfo.getList();
	}
}

package cn.mlb.spider.executorSample;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;

import cn.mlb.spider.entity.Media;
import cn.mlb.spider.mapper.MediaMapper;
import cn.mlb.spider.sourceGraber.HtmlGrabber;
import cn.mlb.spider.sourceGraber.HtmlParser;
import cn.mlb.spider.sourceGraber.ImgDownloader;

public class Fcw30Executor {
	private static Logger logger = Logger.getLogger(Fcw30Executor.class);

	private MediaMapper mediaMapper;
	private SqlSessionFactory factory;

	public static void main(String[] args) throws ParseException, IOException,
			InterruptedException {
		Fcw30Executor executor = new Fcw30Executor();
		executor.init();
		executor.go();

	}

	private void init() throws IOException {
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		factory = new SqlSessionFactoryBuilder().build(inputStream);
		SqlSession session = factory.openSession(true);
		mediaMapper = session.getMapper(MediaMapper.class);
	}

	private void go() throws ParseException, InterruptedException {
		// 创建网页抓取器
		HtmlGrabber grabber = new HtmlGrabber();
		// 创建url数组
		String[] urls = new String[5];
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

		}
	}
}

package cn.mlb.spider.executorSample;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import cn.mlb.spider.Exception.NoContentException;
import cn.mlb.spider.entity.ParsedDocument;
import cn.mlb.spider.sourceGraber.HtmlGrabber;
import cn.mlb.spider.sourceGraber.HtmlParser;
import cn.mlb.spider.sourceGraber.ImgDownloader;

public class MmjpgExecutor {
	private static Logger logger = Logger.getLogger(MeizituExecutor.class);

	public static void main(String[] args) throws InterruptedException {
		HtmlGrabber grabber = new HtmlGrabber();
		// 创建图片页链接集合
		List<String> urlTotalList = new ArrayList<String>();
		String[] urls = new String[14];
		for (int i = 0; i < urls.length; i++) {
			urls[i] = "http://www.mmjpg.com/home/" + (i + 1 + 60);
		}
		logger.info("正在获取每个索引页");
		List<String> htmls = grabber.getHtmls(urls, 100);
		HtmlParser parser = new HtmlParser();
		for (String html : htmls) {
			List<String> attrList = parser.getAttrList(html, ".main .pic li>a",
					"href");
			urlTotalList.addAll(attrList);
		}
		String[] totalUrls = urlTotalList.toArray(new String[1]);
		logger.info("正在获取每个图片页");
		// 抓取所有图片页
		List<String> totalHtmls = grabber.getHtmls(totalUrls, 100);
		logger.info("获取完毕");
		for (int i = 0; i < totalHtmls.size(); i++) {
			String html = totalHtmls.get(i);
			String title = parser.getTextList(html, ".main .article h2").get(0);
			ParsedDocument ad = parser.getAttrDocument(html,
					".main #content img", "src", title);
			if (ad.getContents() == null || ad.getContents().size() == 0) {
				try {
					throw new NoContentException("解析不到图片链接!");
				} catch (NoContentException e) {
					logger.error("解析不到图片链接!");
				}
			}
			String firstLink = ad.getContents().get(0);
			int indexOf = firstLink.lastIndexOf("/");
			String parentLink = firstLink.substring(0, indexOf);
			int maxPicNum = getMaxPicNum(html);
			for (int j = 2; j <= maxPicNum; j++) {
				String link = parentLink + "/" + j + ".jpg";
				ad.getContents().add(link);
			}
			logger.info("标题:" + ad.getTitle() + "-第" + (i + 1) + "图片页链接解析完毕");
			// 开启多线程下载图片
			for (String src : ad.getContents()) {
				Thread.sleep(480);
				new Thread(new ImgDownloader(src, ad.getTitle(),
						"D:/mmjpg/61-74", "http://www.mmjpg.com/mm/1101"))
						.start();
			}
			// 间隔时间要设长一些,否则会被服务器封IP!
			Thread.sleep(500);
		}
	}

	private static int getMaxPicNum(String html) {
		Document document = Jsoup.parse(html);
		Elements prev = document.select("#page #opic").prev();
		String text = prev.get(0).text();
		return Integer.valueOf(text);
	}
}

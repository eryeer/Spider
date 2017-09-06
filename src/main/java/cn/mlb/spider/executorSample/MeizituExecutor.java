package cn.mlb.spider.executorSample;

import java.util.ArrayList;
import java.util.List;

import cn.mlb.spider.sourceGraber.HtmlGrabber;
import cn.mlb.spider.sourceGraber.HtmlParser;
import cn.mlb.spider.sourceGraber.ImgDownloader;

/**
 * 这是一个爬取网站批量下载图片的例子
 * 
 * @author eryeer
 * 
 */
public class MeizituExecutor {

	/**
	 * 这个是执行下载图片的模板类
	 * 
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		// 创建网页抓取器
		HtmlGrabber grabber = new HtmlGrabber();
		// 创建url数组
		String[] urls = new String[30];
		// 创建图片页链接集合
		List<String> urlTotalList = new ArrayList<String>();
		// 为url数组赋值
		for (int i = 0; i < urls.length; i++) {
			urls[i] = "http://www.meizitu.com/a/more_" + (i + 1 + 30) + ".html";
		}
		System.out.println("正在获取每个索引页");
		// 抓取索引页
		List<String> htmls = grabber.getHtmls(urls, 100);
		// 创建网页解析器
		HtmlParser parser = new HtmlParser();
		System.out.println("正在获取索引页上的所有图片页链接");
		// 解析索引页,获取所有图片页链接
		for (int i = 0; i < htmls.size(); i++) {
			List<String> urlList = parser.getAttrList(htmls.get(i),
					".wp-list .pic a", "href");
			urlTotalList.addAll(urlList);
		}
		String[] totalUrls = urlTotalList.toArray(new String[1]);
		System.out.println("正在获取每个图片页");
		// 抓取所有图片页
		List<String> totalHtmls = grabber.getHtmls(totalUrls, 50);
		System.out.println("获取完毕");

		for (int i = 0; i < totalHtmls.size(); i++) {
			// 解析图片页,获取图片链接集合
			List<String> srcList = parser.getAttrList(totalHtmls.get(i),
					".postContent #picture img", "src");
			System.out.println("第" + (i + 1) + "图片页链接解析完毕");
			// 开启多线程下载图片
			for (String src : srcList) {
				Thread.sleep(1500);
				new Thread(new ImgDownloader(src, i / 10, "D:/meizitu"))
						.start();
			}
			// 间隔时间要设长一些,否则会被服务器封IP!
			Thread.sleep(14000);
		}
	}
}

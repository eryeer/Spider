package cn.mlb.test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

public class JsoupTest {
	@Test
	public void test1() {
		String html = "<html><head><title> 这里是字符串内容</title></head" + ">"
				+ "<body><p class='p1'> 这里是 jsoup 作用的相关演示</p></body></html>";
		Document document = Jsoup.parse(html);
		Elements elements = document.select("p[class=p1]");
		for (Element element : elements) {
			String text = element.text();
			String className = element.className();
			System.out.println(className);
			System.out.println(text);
		}
	}

	@Test
	public void test2() {
		File input = new File("D:\\111.html");
		Document doc = null;
		try {
			doc = Jsoup.parse(input, "UTF-8", "http://www.oschina.net/"); // 这里后面加了网址是为了解决后面绝对路径和相对路径的问题
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Elements links = doc.select("p[class=p1]");
		for (Element link : links) {
			String linkHref = link.attr("class");
			String linkText = link.text();
			System.out.println(linkText + ":" + linkHref);
		}

	}

	@Test
	public void test3() {
		HashMap<String, String> hm = new HashMap<String, String>();
		String href = null;
		try {
			// 这是get方式得到的
			Document doc = Jsoup
					.connect(
							"http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2015/index.html")
					.get();
			String title = doc.title();
			Elements links = doc.select("a[href]");

			for (Element link : links) {

				String linkHref = link.attr("abs:href");
				String linkText = link.text();
				// System.out.println(linkText+":"+linkHref);
				hm.put(linkText, linkHref);
				href = linkText;
			}
			// System.out.println("***************");
			// 另外一种是post方式
			/*
			 * @SuppressWarnings("unused") Document doc_Post =
			 * Jsoup.connect(url) .data("query","Java") .userAgent("I am jsoup")
			 * .cookie("auth","token") .timeout(10000) .post(); Elements
			 * links_Post = doc.select("a[href]"); for(Element link:links_Post){
			 * String linkHref = link.attr("abs:href"); String linkText =
			 * link.text(); //System.out.println(linkText+":"+linkHref);
			 * 
			 * //map.put(linkText, linkHref); }
			 */

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			hm.put("加载失败", "error");
		}
		for (String key : hm.keySet()) {
			System.out.println(key + ":" + hm.get(key));
		}
	}

	@Test
	public void test4() {
		String url = "com/home/2";
		int lastIndexOf = url.lastIndexOf("/");
		System.out.println(lastIndexOf);
		String substring = url.substring(0, 8);
		System.out.println(substring);
	}

	@Test
	public void test5() throws IOException {

		Document doc = Jsoup.connect("http://www.fcw30.com/most-popular/1/")
				.get();
		Elements elements = doc
				.select("#list_videos_common_videos_list_items .rating");
		for (Element element : elements) {
			String text = elements.text();
			System.out.println(text);
		}
	}
}

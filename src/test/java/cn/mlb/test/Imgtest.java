package cn.mlb.test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class Imgtest {
	public static void main(String[] args) {
		// simpleSpider("D:/meizitu/test",
		// "http://i.meizitu.net/2017/09/05a01.jpg");
		httpClientSpider(
				"D:/meizitu/test",
				"http://s.cimg.163.com/i/bd3img.heibaimanhua.com/wp-content/uploads/2017/09/02/20170902_59aa590a59206.jpg_s.0x0.auto.jpg");
	}

	public static void simpleSpider(String path, String urlStr) {
		File folder = new File(path);
		if (!folder.exists()) {
			folder.mkdirs();
		}
		String imgName = "test.jpg";
		File file = new File(path + "/" + imgName);
		try {
			System.out.println("开始爬取");
			URL url = new URL(urlStr);
			URLConnection conn = url.openConnection();
			conn.setConnectTimeout(10000);
			conn.setRequestProperty("cache-control", "max-age=0");
			conn.setRequestProperty("upgrade-insecure-requests", "1");
			conn.setRequestProperty("accept-encoding", "gzip, deflate");
			conn.setRequestProperty("accept-language", "zh-CN,zh;q=0.8");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty(
					"user-agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36");
			// conn.setRequestProperty("referer",
			// "http://www.mzitu.com/102138");
			conn.connect();
			BufferedInputStream bis = new BufferedInputStream(
					conn.getInputStream());
			BufferedOutputStream bos = new BufferedOutputStream(
					new FileOutputStream(file));
			int b;
			while ((b = bis.read()) != -1) {
				bos.write(b);
			}
			System.out.println("爬取完毕");
			bis.close();
			bos.close();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public static void httpClientSpider(String path, String urlStr) {
		File folder = new File(path);
		if (!folder.exists()) {
			folder.mkdirs();
		}
		String imgName = "httpClientTest.jpg";
		File file = new File(path + "/" + imgName);
		try {
			System.out.println("开始爬取");
			// 配置请求
			RequestConfig config = RequestConfig.custom()
					.setConnectionRequestTimeout(10000)
					.setConnectTimeout(10000)
					.setCookieSpec(CookieSpecs.STANDARD).build();
			// 创建httpClient对象
			CloseableHttpClient httpClient = HttpClients.custom()
					.setDefaultRequestConfig(config).build();

			// 创建一次get请求
			HttpGet httpGet = new HttpGet(urlStr);
			// 伪装真实浏览器设置请求头
			httpGet.addHeader(
					"User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36");
			httpGet.addHeader("cache-control", "max-age=0");
			httpGet.addHeader("upgrade-insecure-requests", "1");
			httpGet.addHeader("accept-language", "zh-CN,zh;q=0.8");
			httpGet.addHeader("accept-encoding", "gzip, deflate");
			// httpGet.addHeader("referer", "http://www.mzitu.com/102138");
			CloseableHttpResponse response = httpClient.execute(httpGet);
			System.out.println(response.getStatusLine());
			HttpEntity entity = response.getEntity();
			InputStream is = entity.getContent();
			BufferedInputStream bis = new BufferedInputStream(is);
			BufferedOutputStream bos = new BufferedOutputStream(
					new FileOutputStream(file));
			int b;
			while ((b = bis.read()) != -1) {
				bos.write(b);
			}
			System.out.println("爬取完毕");
			bis.close();
			bos.close();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
}

package cn.mlb.test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;

public class Imgtest {
	public static void main(String[] args) {
		simpleSpider("D:/meizitu/test",
				"http://i.meizitu.net/2017/09/05a01.jpg");
	}

	public static void simpleSpider(String path, String urlStr) {
		File folder = new File(path);
		if (!folder.exists()) {
			folder.mkdirs();
		}
		String imgName = "test.jpg";
		File file = new File(path + "/" + imgName);
		try {
			System.out.println("开始爬去");
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
}

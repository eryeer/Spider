package cn.mlb.test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;

public class Videotest {
	private static Logger logger = Logger.getLogger(Videotest.class);

	public static void main(String[] args) {
		// simpleSpider("D:/meizitu/test",
		// "http://i.meizitu.net/2017/09/05a01.jpg");
		httpClientSpider(
				"D:/mvideo/test",
				"http://www.fcw30.com/get_file/1/1be32252a988f6024780abaa5affc9bd35042f29ca/12000/12582/12582.mp4/?download=true&amp;download_filename=6-2-2.mp4");
	}

	public static void httpClientSpider(String path, String urlStr) {
		File folder = new File(path);
		if (!folder.exists()) {
			folder.mkdirs();
		}
		String vedioName = "123.mp4";
		File file = new File(path + "/" + vedioName);
		try {
			logger.info("开始爬取");
			// 配置请求
			RequestConfig config = RequestConfig.custom()
					.setCookieSpec(CookieSpecs.STANDARD).build();
			// 创建httpClient对象
			CloseableHttpClient httpClient = HttpClients.custom()
					.setDefaultRequestConfig(config).build();

			// 创建一次get请求
			HttpGet httpGet = new HttpGet(urlStr);
			// 伪装真实浏览器设置请求头
			httpGet.addHeader(
					"User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.75 Safari/537.36 LBBROWSER");
			httpGet.addHeader("cache-control", "max-age=0");
			httpGet.addHeader("upgrade-insecure-requests", "1");
			httpGet.addHeader("accept-language", "zh-CN,zh;q=0.8");
			httpGet.addHeader("accept-encoding", "gzip, deflate");
			httpGet.addHeader("host", "www.fcw30.com");
			httpGet.addHeader("accept",
					"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			httpGet.addHeader(
					"cookie",
					"PHPSESSID=cvjsnhpd0iuft4qqsd99u1vbs6; kt_tcookie=1; SC_unique_343517=1; SC_unique_343525=1; kt_tcookie=1; _ga=GA1.2.1346891359.1505116928; _gid=GA1.2.1921163310.1505116928; HstCfa3870843=1505116929273; HstCla3870843=1505116929273; HstCmu3870843=1505116929273; HstPn3870843=1; HstPt3870843=1; HstCnv3870843=1; HstCns3870843=1; kt_is_visited=1; kt_qparams=id%3D12582%26dir%3D6-2-2; __dtsu=1FE704450343B659655FD153021435A2");
			CloseableHttpResponse response = httpClient.execute(httpGet);
			logger.info(response.getStatusLine());
			HttpEntity entity = response.getEntity();
			// if (entity != null) {
			// BufferedHttpEntity bEntity = new BufferedHttpEntity(entity);
			logger.info("获取entity完毕");
			InputStream is = entity.getContent();
			logger.info("文件共" + entity.getContentLength() + "字节");
			FileOutputStream os = new FileOutputStream(file);
			saveTo(is, os, entity.getContentLength());
			logger.info("爬取完毕");

			// }
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public static void saveTo(InputStream in, OutputStream out, Long size)
			throws Exception {
		long len = 0l;
		byte[] data = new byte[1024 * 1024];
		int index = 0;
		while ((index = in.read(data)) != -1) {
			out.write(data, 0, index);
			len += (long) index;
			logger.info("下载了" + len / size * 100 + "%");
		}
		in.close();
		out.close();
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
			conn.setRequestProperty("host", "www.fcw30.com");
			conn.setRequestProperty(
					"user-agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.75 Safari/537.36 LBBROWSER");

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

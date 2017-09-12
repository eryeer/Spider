package cn.mlb.spider.sourceGraber;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

/**
 * 这个类用来获取网页的html全部内容
 * 
 * @author eryeer
 * 
 */
public class HtmlGrabber {
	private Logger logger = Logger.getLogger(HtmlGrabber.class);

	/**
	 * 
	 * @param urls
	 *            传入url网址的数组
	 * @param sleepTime
	 *            抓取每一个url之间的时间间隔,单位ms
	 * @return html文档的集合
	 */
	public List<String> getHtmls(String[] urls, int sleepTime) {
		List<String> htmls = new ArrayList<String>();
		HttpClient httpClient = SingleHttpClient.getHttpClient();
		logger.info("开始抓取网页,共" + urls.length + "页");
		for (int i = 0; i < urls.length; i++) {
			logger.info("开始抓取第" + (i + 1) + "页");
			// 创建一次get请求
			HttpGet httpGet = new HttpGet(urls[i]);
			// 伪装真实浏览器设置请求头
			httpGet.addHeader(
					"User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36");
			httpGet.addHeader("cache-control", "max-age=0");
			httpGet.addHeader("upgrade-insecure-requests", "1");
			httpGet.addHeader("accept-language", "zh-CN,zh;q=0.8");
			httpGet.addHeader("accept-encoding", "gzip, deflate");
			httpGet.addHeader("connection", "Keep-Alive");
			try {
				Thread.sleep(sleepTime);
				// 执行抓取
				HttpResponse response = httpClient.execute(httpGet);
				logger.info(response.getStatusLine());
				// 获得response响应实体
				HttpEntity entity = response.getEntity();
				// 将实体转为html文本
				String html = EntityUtils.toString(entity, "UTF-8");
				htmls.add(html);
			} catch (Exception e) {

				e.printStackTrace();
			} finally {
				httpGet.releaseConnection();
				httpGet.abort();
			}
		}
		return htmls;
	}
}

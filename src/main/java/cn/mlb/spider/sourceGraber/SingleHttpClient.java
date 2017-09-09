package cn.mlb.spider.sourceGraber;

import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClients;

/**
 * 这是一个HttpClient的单例工厂类,用来获得全局单例的HttpClient
 * 对于RequestConfig参数的配置,姜葱src根目录下的httpClient.properties文件中读取
 * 
 * @author eryeer
 * 
 */
public class SingleHttpClient {
	private static HttpClient httpClient;
	static {
		ResourceBundle bundle = ResourceBundle.getBundle("httpClient");
		String crt = bundle.getString("connectionRequestTimeOut");
		String ct = bundle.getString("connectionTimeOut");
		String st = bundle.getString("socketTimeout");
		if (StringUtils.isBlank(crt)) {
			crt = "10000";
		}
		if (StringUtils.isBlank(ct)) {
			ct = "10000";
		}
		if (StringUtils.isBlank(st)) {
			st = "10000";
		}
		RequestConfig config = RequestConfig.custom()
				.setConnectionRequestTimeout(Integer.valueOf(crt))
				.setConnectTimeout(Integer.valueOf(ct))
				.setSocketTimeout(Integer.valueOf(st))
				.setCookieSpec(CookieSpecs.STANDARD).build();
		// 返回创建httpClient对象
		httpClient = HttpClients.custom().setDefaultRequestConfig(config)
				.build();
	}

	public static HttpClient getHttpClient() {
		return httpClient;
	}

}

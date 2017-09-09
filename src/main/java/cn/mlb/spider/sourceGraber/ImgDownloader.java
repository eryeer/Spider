package cn.mlb.spider.sourceGraber;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.log4j.Logger;

/**
 * 这是一个用来下载图片的类,由于下载图片比较耗时,所以支持了多线程下载.
 * 
 * @author eryeer
 * 
 */
public class ImgDownloader implements Runnable {
	// 用于记录图片数量
	private static int count = 1;
	// 图片网络资源路径
	private String imgSrc;
	// 图片本地保存路径
	private StringBuffer basePath;
	// 图片防盗链
	private String referer;
	private Logger logger = Logger.getLogger(ImgDownloader.class);

	public ImgDownloader() {
	}

	/**
	 * @param imgSrc
	 *            图片网络资源路径
	 * @param folderNo
	 *            存放文件夹编号
	 * @param path
	 *            图片本地保存路径
	 */
	public ImgDownloader(String imgSrc, Serializable folderName, String path) {
		super();
		this.imgSrc = imgSrc;
		this.basePath = new StringBuffer(path + "/" + folderName);
	}

	public ImgDownloader(String imgSrc, int folderNo, String path,
			String referer) {
		this(imgSrc, folderNo, path);
		this.referer = referer;
	}

	public ImgDownloader(String imgSrc, String folderName, String path,
			String referer) {
		this(imgSrc, folderName, path);
		this.referer = referer;
	}

	/**
	 * 支持多线程执行下载
	 */
	public void run() {
		// 创建本地保存文件夹
		File folder = new File(basePath.toString());
		if (!folder.exists()) {
			folder.mkdirs();
			logger.info("图片存放于" + basePath + "目录下");
		}
		// UUID图片名
		// String imgName = UUID.randomUUID().toString().replaceAll("-", "")
		// + ".jpg";
		String imgName = System.currentTimeMillis() + ".jpg";// 以系统时间设置图片名
		File file = new File(basePath + "/" + imgName);
		try {
			HttpClient httpClient = SingleHttpClient.getHttpClient();
			// 创建一次get请求
			HttpGet httpGet = new HttpGet(imgSrc);
			// 伪装真实浏览器设置请求头
			httpGet.addHeader(
					"User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36");
			httpGet.addHeader("cache-control", "max-age=0");
			httpGet.addHeader("upgrade-insecure-requests", "1");
			httpGet.addHeader("accept-language", "zh-CN,zh;q=0.8");
			httpGet.addHeader("accept-encoding", "gzip, deflate");
			if (!StringUtils.isBlank(this.referer)) {
				httpGet.addHeader("referer", this.referer);
			}
			HttpResponse response = httpClient.execute(httpGet);
			logger.info(response.getStatusLine());
			HttpEntity entity = response.getEntity();
			// 下载图片
			BufferedInputStream bis = new BufferedInputStream(
					entity.getContent());
			BufferedOutputStream bos = new BufferedOutputStream(
					new FileOutputStream(file));
			int b;
			while ((b = bis.read()) != -1) {
				bos.write(b);
			}

			bis.close();
			bos.close();
			logger.info("第" + (count++) + "张图片：" + file.getAbsolutePath());
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

}

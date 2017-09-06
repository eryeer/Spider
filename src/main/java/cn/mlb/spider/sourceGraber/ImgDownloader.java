package cn.mlb.spider.sourceGraber;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.lang3.StringUtils;
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
	public ImgDownloader(String imgSrc, int folderNo, String path) {
		super();
		this.imgSrc = imgSrc;
		this.basePath = new StringBuffer(path + "/folder_" + folderNo);
	}

	public ImgDownloader(String imgSrc, int folderNo, String path,
			String referer) {
		this(imgSrc, folderNo, path);
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
			// 创建url对象
			URL url = new URL(imgSrc);
			// 创建连接
			URLConnection conn = url.openConnection();
			// 设置conn
			conn.setConnectTimeout(16000);
			conn.setReadTimeout(90000);
			// 配置请求头,伪装浏览器
			conn.setRequestProperty("cache-control", "max-age=0");
			conn.setRequestProperty("upgrade-insecure-requests", "1");
			conn.setRequestProperty("accept-encoding", "gzip, deflate");
			conn.setRequestProperty("accept-language", "zh-CN,zh;q=0.8");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty(
					"user-agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36");
			// 设置防盗链
			if (!StringUtils.isBlank(this.referer)) {
				conn.setRequestProperty("referer", this.referer);
			}
			// 开启连接
			conn.connect();
			// 下载图片
			BufferedInputStream bis = new BufferedInputStream(
					conn.getInputStream());
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

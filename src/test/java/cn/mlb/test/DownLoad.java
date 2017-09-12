package cn.mlb.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Created by IntelliJ IDEA. User: haoshihai Date: 13-6-17 Time: 下午5:22 To
 * change this template use File | Settings | File Templates.
 */
public class DownLoad {
	private String workPath = null;
	private String downLoadUrl = null;
	private DefaultHttpClient httpClient = new DefaultHttpClient();

	public void downLoad(String url, String dst) {
		try {
			HttpGet httpGet = new HttpGet(url);
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
					"PHPSESSID=ajikl3acks54qk5eeh91htn1m4; kt_referer=http%3A%2F%2F00fcw.com%2F; HstCfa3870843=1505062746476; HstCmu3870843=1505062746476; kt_qparams=download%3Dtrue%26amp%3Bdownload_filename%3D2110.mp4; kt_tcookie=1; SC_unique_343517=0; SC_unique_343525=0; kt_tcookie=1; HstCla3870843=1505109989675; HstPn3870843=2; HstPt3870843=18; HstCnv3870843=3; HstCns3870843=4; _ga=GA1.2.1561102511.1505062614; _gid=GA1.2.536880938.1505062614; kt_is_visited=1; __dtsu=2DE7B66BE4520559C1188236023D65E3");

			HttpResponse httpResponse = httpClient.execute(httpGet);
			HttpEntity entity = httpResponse.getEntity();
			InputStream in = entity.getContent();
			long length = entity.getContentLength();
			System.out.println(httpResponse.getStatusLine());
			System.out.println(length);
			if (length <= 0) {
				System.out.println("下载文件不存在！");
				return;
			}
			OutputStream out = new FileOutputStream(new File(dst));
			saveTo(in, out);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveTo(InputStream in, OutputStream out) throws Exception {
		byte[] data = new byte[1024 * 1024];
		int index = 0;
		while ((index = in.read(data)) != -1) {
			out.write(data, 0, index);
		}
		in.close();
		out.close();
	}

	public static void main(String args[]) {
		DownLoad downLoad = new DownLoad();
		String url = "http://www.fcw30.com/get_file/1/25ecb497cfd6e82f3795aa6383497f7288060ac0cb/12000/12677/12677.mp4/?download=true&amp;download_filename=s9.mp4";
		downLoad.downLoad(url, "E:\\1024.m4");
	}
}
package cn.mlb.spider.sourceGraber;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;

public class HttpClient {

    public static SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sc = SSLContext.getInstance("TLSv1.2");

        X509TrustManager trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                                           String paramString) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                                           String paramString) throws CertificateException {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };

        sc.init(null, new TrustManager[]{trustManager}, null);
        return sc;
    }

    public static String sendPost(String url, final String ip, Object object)
            throws KeyManagementException, NoSuchAlgorithmException, IOException {
    	String jsonBody = JSON.toJSONString(object);
		System.out.println(jsonBody);
		String body = "";
        SSLContext sslcontext = createIgnoreVerifySSL();

        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", new SSLConnectionSocketFactory(sslcontext, new String[]{"TLSv1.2"}, null,
                        new HostnameVerifier() {
                            @Override
                            public boolean verify(String hostname, SSLSession session) {
                                return hostname.equals(ip);
                            }

                        }))
                .build();
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        HttpClients.custom().setConnectionManager(connManager);

        CloseableHttpClient client = HttpClients.custom().setConnectionManager(connManager).build();

        HttpPost httpPost = new HttpPost(url);

        StringEntity entity = new StringEntity(jsonBody, "utf-8");
        entity.setContentEncoding(new BasicHeader("Content-Type", "application/json"));
        httpPost.setEntity(entity);

        httpPost.setHeader("Content-type", "application/json");

        CloseableHttpResponse response = client.execute(httpPost);
        HttpEntity entity2 = response.getEntity();
        if (entity2 != null) {
            body = EntityUtils.toString(entity2, "UTF-8");
        }

        EntityUtils.consume(entity2);
        response.close();
        return body;
    }

    public static String sendGet(String url)
            throws KeyManagementException, NoSuchAlgorithmException, ClientProtocolException, IOException {
        String body = "";
        SSLContext sslcontext = createIgnoreVerifySSL();

        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", new SSLConnectionSocketFactory(sslcontext, new String[]{"TLSv1.2"}, null,
                        new HostnameVerifier() {
                            @Override
                            public boolean verify(String hostname, SSLSession session) {
                            	return true;
                                //return hostname.equals(ip);
                            }
                        }))
                .build();
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        HttpClients.custom().setConnectionManager(connManager);

        CloseableHttpClient client = HttpClients.custom().setConnectionManager(connManager).build();

        HttpGet httpGet = new HttpGet(url);

        httpGet.setHeader("Content-type", "application/json");

        CloseableHttpResponse response = client.execute(httpGet);
        HttpEntity entity2 = response.getEntity();
        if (entity2 != null) {
            body = EntityUtils.toString(entity2, "UTF-8");
        }

        EntityUtils.consume(entity2);
        response.close();
        return body;
    }
    
	/**
	 * 
	 * @param url  :full ip
	 * @param ip  �� �Զ�ip
	 * @param localFileName :�����ļ���
	 * @return
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static boolean downLoadFile(String url, final String ip, String localFileName)
			throws KeyManagementException, NoSuchAlgorithmException, ClientProtocolException, IOException {
		SSLContext sslcontext = createIgnoreVerifySSL();

		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
				.register("http", PlainConnectionSocketFactory.INSTANCE)
				.register("https", new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1.2" }, null,
						new HostnameVerifier() {
							@Override
							public boolean verify(String hostname, SSLSession session) {
								return hostname.equals(ip);
							}
						}))
				.build();
		PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
		HttpClients.custom().setConnectionManager(connManager);

		CloseableHttpClient client = HttpClients.custom().setConnectionManager(connManager).build();

		HttpGet httpGet = new HttpGet(url);

		httpGet.setHeader("Content-type", "application/json");

		CloseableHttpResponse response = client.execute(httpGet);
		HttpEntity entity = response.getEntity();
		OutputStream out = null;
		InputStream in = null;
		try {

			in = entity.getContent();

			File file = new File(localFileName);
			if (!file.exists()) {
				file.createNewFile();
			}

			out = new FileOutputStream(file);
			byte[] buffer = new byte[4096];
			int readLength = 0;
			while ((readLength = in.read(buffer)) > 0) {
				byte[] bytes = new byte[readLength];
				System.arraycopy(buffer, 0, bytes, 0, readLength);
				out.write(bytes);
			}

			out.flush();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		response.close();
		return true;
	}
	
	public static void main(String[] args) {
        String url ="https://fpcyweb.tax.sh.gov.cn:1001/WebQuery/query?callback=jQuery110203457384982721047_1514362018739&fpdm=131001520651&fphm=00815164&kprq=20171201&fpje=400&fplx=03&yzm=4w&yzmSj=2017-12-27+15%3A40%3A47&index=5e12f663d0e1cbfde14b49b772cb4ca5&iv=2ed272fcc6b182e0fffc74744453dc52&salt=f603a0be2817f0f8bd9a3218df90bce3&publickey=7CDD8714495DDCBF6679EFEA25AFD84D&_=1514362018743";

		try {
			HttpClient.sendGet(url);
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

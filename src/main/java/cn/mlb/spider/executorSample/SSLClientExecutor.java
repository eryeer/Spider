package cn.mlb.spider.executorSample;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import javax.net.ssl.SSLContext;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class SSLClientExecutor {

    private static final String KEYSTOREPATH = "D:\\Develop\\Java\\jdk1.8.0_144\\jre\\lib\\security\\cacerts"; // or .p12
    private static final String KEYSTOREPASS = "changeit";
    private static final String KEYPASS = null;

    KeyStore readStore() throws Exception {
        try (InputStream keyStoreStream = new FileInputStream(new File(KEYSTOREPATH))) {
            KeyStore keyStore = KeyStore.getInstance("JKS"); // or "PKCS12"
            keyStore.load(keyStoreStream, KEYSTOREPASS.toCharArray());
            return keyStore;
        }
    }
    @Test
    public void readKeyStore() throws Exception {
        assertNotNull(readStore());
    }
    @Test
    public void performClientRequest() throws Exception {
        SSLContext sslContext = SSLContexts.custom()
                .loadKeyMaterial(readStore(), null) // use null as second param if you don't have a separate key password
                .build();

        HttpClient httpClient = HttpClients.custom().setSslcontext(sslContext).build();
        HttpGet httpGet = new HttpGet("https://fpcyweb.tax.sh.gov.cn:1001/WebQuery/query?callback=jQuery110203457384982721047_1514362018739&fpdm=131001520651&fphm=00815164&kprq=20171201&fpje=400&fplx=03&yzm=4w&yzmSj=2017-12-27+15%3A40%3A47&index=5e12f663d0e1cbfde14b49b772cb4ca5&iv=2ed272fcc6b182e0fffc74744453dc52&salt=f603a0be2817f0f8bd9a3218df90bce3&publickey=7CDD8714495DDCBF6679EFEA25AFD84D&_=1514362018743");
        httpGet.addHeader(
				"User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko");
        httpGet.addHeader("accept-language", "zh-CN");
        httpGet.addHeader("referer", "https://inv-veri.chinatax.gov.cn/");
        httpGet.addHeader("connection", "Keep-Alive");
		httpGet.addHeader("accept", "application/javascript, */*; q=0.8");
		httpGet.addHeader("accept-encoding", "gzip, deflate");
        HttpResponse response = httpClient.execute(httpGet);
        assertEquals(200, response.getStatusLine().getStatusCode());
        HttpEntity entity = response.getEntity();

        System.out.println("----------------------------------------");
        System.out.println(response.getStatusLine());
        EntityUtils.consume(entity);
    }
}




package ru.kinoservice.movie.parser.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.kinoservice.movie.parser.exception.TooManyConnectionsException;

import javax.annotation.PostConstruct;
import javax.net.ssl.*;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ProxyConnectionService implements ConnectionService {

    private static Logger logger = LoggerFactory.getLogger(ProxyConnectionService.class);

    @Value("${proxy.host}")
    private String hostProxy;

    @Value("${proxy.port}")
    private String portProxy;

    private AtomicInteger countConnections = new AtomicInteger();


    @PostConstruct
    public void init() {
        System.setProperty("https.proxyHost", hostProxy);
        System.setProperty("https.proxyPort", portProxy);
    }

    @Override
    public Document getDocument(String url) throws IOException {

        try {
            if (countConnections.incrementAndGet() > 50) {
                logger.error("Too many open connections");
                throw new TooManyConnectionsException();
            }

            return Jsoup.connect(url).timeout(30 * 1000).get();

        } finally {
            countConnections.decrementAndGet();
        }

    }

    static {
        disableSslVerification();
    }

    private static void disableSslVerification() {
        try {
            // Create a trust manager that does not validate certificate chains
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }
            };

            // Install the all-trusting trust manager
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            // Create all-trusting host name verifier
            HostnameVerifier allHostsValid = new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };

            // Install the all-trusting host verifier
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }
}

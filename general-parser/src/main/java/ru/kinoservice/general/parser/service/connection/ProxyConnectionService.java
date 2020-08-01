package ru.kinoservice.general.parser.service.connection;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;
import ru.kinoservice.general.parser.config.ParserProperties;
import ru.kinoservice.general.parser.exception.TooManyConnectionsException;

import javax.annotation.PostConstruct;
import javax.net.ssl.*;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
@AllArgsConstructor
public class ProxyConnectionService implements ConnectionService {

    private final ParserProperties parserProperties;
    private AtomicInteger countConnections;


    @PostConstruct
    public void init() {
        System.setProperty("https.proxyHost", parserProperties.getHostProxy());
        System.setProperty("https.proxyPort", parserProperties.getPortProxy());
        countConnections = new AtomicInteger();
    }

    @Override
    public Document getDocument(String url) throws IOException {

        try {
            if (countConnections.incrementAndGet() > 50) {
                log.error("Too many open connections");
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
                public X509Certificate[] getAcceptedIssuers() {
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

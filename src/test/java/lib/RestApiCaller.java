package lib;

import java.io.IOException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RestApiCaller {

    private final String baseUrl = Environment.getBaseUrlEndpoint();
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    public Boolean isProduction = Environment.isProductionEnvironment();

    TrustManager[] trustAllCerts = new TrustManager[] {
            new X509TrustManager() {
                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                }

                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[] {};
                }
            }
    };

    /**
     * Gets the first and last name of this Student.
     * 
     * @param targetUrl   The endpoint name to be called using GET request method.
     * @param channel     The channel request header to track where the call is
     *                    coming from.
     * @param jsonRequest The JSON string of the request body.
     * @param bearerToken The token to authenticate the API call.
     * @return Response object that contains the API response as a result of a
     *         successful API call.
     */
    public Response post(String targetUrl, String channel, String jsonRequest, String bearerToken) throws IOException {

        try {
            OkHttpClient client = new OkHttpClient();

            /*
             * This SSL issue should not exist in production. We shouldn't actually even be
             * doing this on lower environments as well.
             */
            if (!isProduction) {
                SSLContext sslContext = SSLContext.getInstance("SSL");
                sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

                OkHttpClient.Builder newBuilder = new OkHttpClient.Builder();
                newBuilder.sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) trustAllCerts[0]);
                newBuilder.hostnameVerifier((hostname, session) -> true);

                client = newBuilder.build();
            }

            var body = RequestBody.create(jsonRequest, JSON);

            Request request = new Request.Builder()
                    .url(baseUrl + targetUrl)
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer " + bearerToken)
                    .build();

            return client.newCall(request).execute();

        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    /**
     * Gets the first and last name of this Student.
     * 
     * @param targetUrl       The endpoint name to be called using GET request
     *                        method.
     * @param channel         The channel request header to track where the call is
     *                        coming from.
     * @param queryParameters The URL query string - starts with ? and every
     *                        key-value pair is appended by &.
     * @param bearerToken     The token to authenticate the API call.
     * @return Response object that contains the API response as a result of a
     *         successful API call.
     */
    public Response get(String targetUrl, String channel, String queryParameters, String bearerToken)
            throws IOException {

        try {
            OkHttpClient client = new OkHttpClient();

            /*
             * This SSL issue should not exist in production. We shouldn't actually even be
             * doing this.
             */
            if (!isProduction) {
                SSLContext sslContext = SSLContext.getInstance("SSL");
                sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

                OkHttpClient.Builder newBuilder = new OkHttpClient.Builder();
                newBuilder.sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) trustAllCerts[0]);
                newBuilder.hostnameVerifier((hostname, session) -> true);

                client = newBuilder.build();
            }

            Request request = new Request.Builder()
                    .url(baseUrl + targetUrl + queryParameters)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer " + bearerToken)
                    .build();

            return client.newCall(request).execute();

        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }
}
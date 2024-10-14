//package com.example.uberbackend.configuration;
//
//import org.apache.http.Header;
//import org.apache.http.message.BasicHeader;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpHeaders;
//
//
//@Configuration
//public class ElasticsearchClientConfig {
//
//    @Value("${elasticsearch.host}")
//    private String host;
//
//    @Value("${elasticsearch.port}")
//    private int port;
//
//    @Value("${elasticsearch.protocol}")
//    private String protocol;
//
//    @Value("${elasticsearch.username}")
//    private String userName;
//
//    @Value("${elasticsearch.password}")
//    private String password;
//
////    @Bean(destroyMethod = "close")
////    public RestHighLevelClient restHighLevelClient() {
////        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
////        credentialsProvider.setCredentials(AuthScope.ANY,
////                new UsernamePasswordCredentials(userName, password));
////
////        RestClientBuilder builder = RestClient.builder(new HttpHost(host, port, protocol))
////                .setHttpClientConfigCallback(httpClientBuilder -> {
////                    try {
////                        return httpClientBuilder
////                                .setDefaultCredentialsProvider(credentialsProvider)
////                                .setSSLContext(SSLContextBuilder.create().loadTrustMaterial(null, (X509Certificate[] chain, String authType) -> true).build());
////                    } catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
////                        throw new RuntimeException(e);
////                    }
////                });
////
////        return new RestHighLevelClient(builder);
////    }
//
//    private Header[] compatibilityHeaders() {
//        return new Header[]{
//                new BasicHeader(HttpHeaders.ACCEPT, "application/vnd.elasticsearch+json;compatible-with=7"),
//                new BasicHeader(HttpHeaders.CONTENT_TYPE, "application/vnd.elasticsearch+json;compatible-with=7")
//        };
//    }
//}
//

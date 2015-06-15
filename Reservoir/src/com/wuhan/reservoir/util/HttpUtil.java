package com.wuhan.reservoir.util;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.zip.GZIPInputStream;

import android.util.Log;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

/**
 * http请求工具方法
 * @author 
 *
 */
public final class HttpUtil {
    private static final String TAG = HttpUtil.class.getSimpleName();

    public static final int CONNECTION_TIMEOUT_SHORT = 5 * 100;
    public static final int CONNECTION_TIMEOUT = 15 * 1000;
    private static final String CHARSET = "UTF8";
    public static final String CONTENT_TYPE = "Content-Type";
    private static final Header[] JSON_HEADERS = new Header[] {
        new BasicHeader("Accept", "application/json"),
        new BasicHeader(CONTENT_TYPE, "application/json"),
        //如果需要服务器返回gzip压缩过的json串，需要放开以下注释
        new BasicHeader("Accept-Encoding", "gzip")
    };

    public static String requestJson(String url) throws ClientProtocolException, IOException {
        return requestJson(url, null);
    }

    public static String requestJson(String url, String jsonParams) throws ClientProtocolException, IOException {
        HttpEntity entity = null;
        if (jsonParams != null) {
            entity = new StringEntity(jsonParams, CHARSET);
        }
        final HttpPost post = createHttpJsonPost(url, entity);
        entity = execute(post, CONNECTION_TIMEOUT);

        if (entity == null)
        	return "";

        //如果服务器返回的是gzip压缩的字符串，需要放出以下注释的代码进行解压缩
        Header encodingHeader = entity.getContentEncoding();
        if (encodingHeader != null) {
            String encodingValue = encodingHeader.getValue();
            if (encodingValue != null) {
                if (encodingValue.contains("gzip")) {
                    Log.d(TAG, "Json string is gziped.");
                    return unGzipInputStream(entity.getContent());
                }
            }
        }

        return EntityUtils.toString(entity, CHARSET);
    }
    
    
    public static String requestJson(String url, String jsonParams,int timeout) throws ClientProtocolException, IOException {
        HttpEntity entity = null;
        if (jsonParams != null) {
            entity = new StringEntity(jsonParams, CHARSET);
        }
        final HttpPost post = createHttpJsonPost(url, entity);
        entity = execute(post, timeout);

        if (entity == null)
            return "";

        //如果服务器返回的是gzip压缩的字符串，需要放出以下注释的代码进行解压缩
        Header encodingHeader = entity.getContentEncoding();
        if (encodingHeader != null) {
            String encodingValue = encodingHeader.getValue();
            if (encodingValue != null) {
                if (encodingValue.contains("gzip")) {
                    Log.d(TAG, "Json string is gziped.");
                    return unGzipInputStream(entity.getContent());
                }
            }
        }

        return EntityUtils.toString(entity, CHARSET);
    }

    private static HttpPost createHttpJsonPost(final String url, final HttpEntity entity) {
        return createHttpPost(url, entity, JSON_HEADERS);
    }

    private static HttpPost createHttpPost(final String url, final HttpEntity entity, Header... headers) {
        final HttpPost post = new HttpPost(url);
        if (headers != null) {
            post.setHeaders(headers);
        }
        post.setEntity(entity);
        return post;
    }

    /**
     * @return
     * @throws IOException
     * @throws ClientProtocolException
     */
    private static HttpEntity execute(final HttpUriRequest request, int timeout) throws ClientProtocolException, IOException {
        final HttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, timeout);
        HttpConnectionParams.setSoTimeout(httpParams, timeout);

        final HttpClient httpClient = new DefaultHttpClient(httpParams);

        HttpResponse response = httpClient.execute(request);
        if (response.getStatusLine().getStatusCode() == 200) {
            return response.getEntity();
        }
        return null;
    }

    public static byte[] requestGet(URI uri, int timeout) throws ClientProtocolException, IOException {
        HttpUriRequest request = new HttpGet(uri);
        final HttpEntity entity = execute(request, timeout);
        byte[] data = null;
        if (entity != null) {
            data = EntityUtils.toByteArray(entity);
        }
        return data;
    }
    
    public static byte[] requestGet(final String url) throws ClientProtocolException, IOException, URISyntaxException {
    	return requestGet(new URI(url), -1);
    }

    public static URI parseUri(final String uriString) throws MalformedURLException, URISyntaxException {
        final URL url = new URL(uriString);
        return new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
    }

    public static String unGzipInputStream(final InputStream inputStream) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            GZIPInputStream gzipInputStream = new GZIPInputStream(inputStream);
            byte[] buffer = new byte[256];

            int count = 0;
            while ((count = gzipInputStream.read(buffer)) >= 0) {
                out.write(buffer, 0, count);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }

        String outStr = null;
        try {
            outStr = out.toString(CHARSET);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

        return outStr;
    }
}

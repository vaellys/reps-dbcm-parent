package com.reps.dbcm.deploy.util;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClientFactory {

	public static final Logger logger = LoggerFactory.getLogger(HttpClientFactory.class);

	/**
	 * 连接超时时间 可以配到配置文件 （单位毫秒）
	 */
	private static int MAX_TIME_OUT = 20000;

	// 设置整个连接池最大连接数
	private static int MAX_CONN = 200;
	// 设置单个路由默认连接数
	private static int SINGLE_ROUTE_MAX_CONN = 100;

	// 连接丢失后,重试次数
	private static int MAX_EXECUT_COUNT = 3;

	// 创建连接管理器
	private PoolingHttpClientConnectionManager connManager = null;

	private HttpClient httpClient = null;

	// 单例开始
	private HttpClientFactory() {
		creatHttpClientConnectionManager();
	}

	private static class HttpClientFactoryInner {
		public static final HttpClientFactory httpClientFactory = new HttpClientFactory();
	}

	public static HttpClientFactory getInstance() {
		return HttpClientFactoryInner.httpClientFactory;
	}

	/**
	 * 设置HttpClient连接池
	 */
	private void creatHttpClientConnectionManager() {
		try {
			if (httpClient != null) {
				return;
			}

			// 创建SSLSocketFactory
			// 定义socket工厂类 指定协议（Http、Https）
			Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory> create().register("http", PlainConnectionSocketFactory.getSocketFactory())
					.register("https", createSSLConnSocketFactory())// SSLConnectionSocketFactory.getSocketFactory()
					.build();

			// 创建连接管理器
			connManager = new PoolingHttpClientConnectionManager(registry);
			connManager.setMaxTotal(MAX_CONN);// 设置最大连接数
			connManager.setDefaultMaxPerRoute(SINGLE_ROUTE_MAX_CONN);// 设置每个路由默认连接数

			// 设置目标主机的连接数
			// HttpHost host = new HttpHost("account.dafy.service");//针对的主机
			// connManager.setMaxPerRoute(new HttpRoute(host),
			// 50);//每个路由器对每个服务器允许最大50个并发访问

			// 创建httpClient对象
			httpClient = HttpClients.custom().setConnectionManager(connManager).setRetryHandler(httpRequestRetry()).setDefaultRequestConfig(config()).build();

		} catch (Exception e) {
			logger.error("获取httpClient(https)对象池异常:" + e.getMessage(), e);
		}
	}

	/**
	 * 创建SSL连接
	 * 
	 * @throws Exception
	 */
	private SSLConnectionSocketFactory createSSLConnSocketFactory() throws Exception {
		// 创建TrustManager
		X509TrustManager xtm = new X509TrustManager() {
			@Override
			public void checkClientTrusted(java.security.cert.X509Certificate[] arg0, String arg1) throws java.security.cert.CertificateException {

			}

			@Override
			public void checkServerTrusted(java.security.cert.X509Certificate[] arg0, String arg1) throws java.security.cert.CertificateException {

			}

			@Override
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};
		// TLS1.0与SSL3.0基本上没有太大的差别，可粗略理解为TLS是SSL的继承者，但它们使用的是相同的SSLContext
		SSLContext ctx = SSLContext.getInstance("TLS");
		// 使用TrustManager来初始化该上下文，TrustManager只是被SSL的Socket所使用
		ctx.init(null, new TrustManager[] { xtm }, null);
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(ctx);
		return sslsf;
	}

	/**
	 * 配置请求连接重试机制
	 */
	private HttpRequestRetryHandler httpRequestRetry() {
		HttpRequestRetryHandler httpRequestRetryHandler = new HttpRequestRetryHandler() {
			public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
				if (executionCount >= MAX_EXECUT_COUNT) {// 如果已经重试MAX_EXECUT_COUNT次，就放弃
					return false;
				}
				if (exception instanceof NoHttpResponseException) {// 如果服务器丢掉了连接，那么就重试
					logger.error("httpclient 服务器连接丢失");
					return true;
				}
				if (exception instanceof SSLHandshakeException) {// 不要重试SSL握手异常
					logger.error("httpclient SSL握手异常");
					return false;
				}
				if (exception instanceof InterruptedIOException) {// 超时
					logger.error("httpclient 连接超时");
					return false;
				}
				if (exception instanceof UnknownHostException) {// 目标服务器不可达
					logger.error("httpclient 目标服务器不可达");
					return false;
				}
				if (exception instanceof ConnectTimeoutException) {// 连接被拒绝
					logger.error("httpclient 连接被拒绝");
					return false;
				}
				if (exception instanceof SSLException) {// ssl握手异常
					logger.error("httpclient SSLException");
					return false;
				}

				// HttpClientContext clientContext =
				// HttpClientContext.adapt(context);
				// HttpRequest request = clientContext.getRequest();
				// 如果请求是幂等的，就再次尝试 暂时没理解先注释
				// if (!(request instanceof HttpEntityEnclosingRequest)) {
				// return true;
				// }
				return false;
			}
		};
		return httpRequestRetryHandler;
	}

	/**
	 * 配置默认请求参数
	 */
	private static RequestConfig config() {
		// 配置请求的超时设置 其他参数可以追加
		RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(MAX_TIME_OUT)// 设置从连接池获取连接实例的超时
				.setConnectTimeout(MAX_TIME_OUT)// 设置连接超时
				.setSocketTimeout(MAX_TIME_OUT)// 设置读取超时
				.build();
		return requestConfig;
	}

	public synchronized void close() {
		if (connManager == null) {
			return;
		}
		// 关闭连接池
		connManager.shutdown();
		// 设置httpClient失效
		httpClient = null;
		connManager = null;
	}

	public synchronized boolean isOpen() {
		if (connManager == null) {
			return false;
		}
		return true;
	}

	/**
	 * 发送 post请求
	 * 
	 * @param httpUrl
	 *            地址
	 * @param maps
	 *            参数
	 */
	public String sendHttpPost(String httpUrl, Map<String, String> maps) throws Exception{
		if (logger.isDebugEnabled()) {
			logger.info("HttpClient POST for:" + httpUrl);
		}
		long start = System.currentTimeMillis();
		HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
		// 创建参数队列
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		for (String key : maps.keySet()) {
			nameValuePairs.add(new BasicNameValuePair(key, maps.get(key)));
		}
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		String sendHttpPost = sendHttpPost(httpPost);
		long end = System.currentTimeMillis();
		logger.info("HttpClient POST for:" + httpUrl + ", 请求花费时间：" + (end - start) + "ms");
		return sendHttpPost;
	}

	/**
	 * 发送Post请求
	 * 
	 * @param httpPost
	 * @return
	 */
	private String sendHttpPost(HttpPost httpPost) throws Exception{
		if (!isOpen()) {
			return "本地连接池关闭";
		}
		HttpResponse response = null;
		String responseContent = null;
		try {
			// 执行请求
			response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			responseContent = EntityUtils.toString(entity, "UTF-8");
		} catch (ClientProtocolException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} finally {
			// 释放连接
			if (response != null) {
				try {
					EntityUtils.consume(response.getEntity());// 会自动释放连接
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return responseContent;
	}

	/**
	 * 发送 get请求
	 * 
	 * @param httpUrl
	 */
	public String sendHttpGet(String httpUrl) {
		if (logger.isDebugEnabled()) {
			logger.info("HttpClient GET for:" + httpUrl);
		}
		long start = System.currentTimeMillis();
		HttpGet httpGet = new HttpGet(httpUrl);// 创建get请求
		String sendHttpGet = sendHttpGet(httpGet);
		long end = System.currentTimeMillis();
		logger.info("HttpClient GET for:" + httpUrl + ", 请求花费时间：" + (end - start) + "ms");
		return sendHttpGet;
	}

	public String sendHttpGet(HttpGet httpGet) {
		if (!isOpen()) {
			return "本地连接池关闭";
		}
		HttpResponse response = null;
		// 开始进行get请求
		String strReturn = "";
		try {
			response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				strReturn = EntityUtils.toString(entity);
			}
			return strReturn;
		} catch (ClientProtocolException e) {
			throw new RuntimeException("ClientProtocolException:" + e.getMessage(), e);
		} catch (IOException e) {
			throw new RuntimeException("IOException:" + e.getMessage(), e);
		} finally {
			// 释放连接
			if (response != null) {
				try {
					EntityUtils.consume(response.getEntity());// 会自动释放连接
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {
		String hf = HttpClientFactory.getInstance().sendHttpGet("http://www.baidu.com");
		System.out.println(hf);
		try {
			Thread.sleep(Integer.MAX_VALUE);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

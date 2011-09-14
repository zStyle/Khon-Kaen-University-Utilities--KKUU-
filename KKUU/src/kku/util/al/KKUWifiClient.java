package kku.util.al;

import java.io.IOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

public class KKUWifiClient {

	// These are not regex
	static final String LOGIN_SUCCESSFUL_PATTERN = "URL";
	
	static final String FORM_USERNAME = "user";
	static final String FORM_PASSWORD = "password";
	static final String FORM_URL = "https://rahunas-07/login.php";
	static final int CONNECTION_TIMEOUT = 2000;
	static final int SOCKET_TIMEOUT = 2000;
	static final int RETRY_COUNT = 2;
	
	private String mUsername;
	private String mPassword;
	private DefaultHttpClient mHttpClient;
	
	public KKUWifiClient(String username, String password) {
		mUsername = username;
		mPassword = password;
		
		mHttpClient = new DefaultHttpClient();
		HttpParams params = mHttpClient.getParams();
		HttpConnectionParams.setConnectionTimeout(params, CONNECTION_TIMEOUT);
		HttpConnectionParams.setSoTimeout(params, SOCKET_TIMEOUT);
		
		// Also retry POST requests (normally not retried because it is not regarded idempotent)
		mHttpClient.setHttpRequestRetryHandler(new HttpRequestRetryHandler() {
			@Override
			public boolean retryRequest(IOException exception, int executionCount,
					HttpContext context) {
		        if (executionCount >= RETRY_COUNT) {
		            // Do not retry if over max retry count
		            return false;
		        }
		        if (exception instanceof UnknownHostException) {
		            // Unknown host
		            return false;
		        }
		        if (exception instanceof ConnectException) {
		            // Connection refused 
		            return false;
		        }
		        if (exception instanceof SSLHandshakeException) {
		            // SSL handshake exception
		            return false;
		        }

		        return true;
			}
		});
	}
	
	public boolean loginRequired() throws IOException {
		try {
			HttpGet httpget = new HttpGet("https://www.joinstick.net/");
			mHttpClient.execute(httpget);
		}
		catch (SSLException e) {
			return true; // If login is required, the certificate sent will be securelogin.arubanetworks.com
		}
		return false;
	}
	
	public void login() throws IOException, LoginException {
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair(FORM_USERNAME, mUsername));
		formparams.add(new BasicNameValuePair(FORM_PASSWORD, mPassword));
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, "UTF-8");
		HttpPost httppost = new HttpPost(FORM_URL);
		httppost.setEntity(entity);
		HttpResponse response = mHttpClient.execute(httppost);
		String strRes = EntityUtils.toString(response.getEntity());
		
		if (strRes.contains(LOGIN_SUCCESSFUL_PATTERN)) {
			// login successful
		} else {
			throw new LoginException(strRes);
		}
	}
	
}

package net.nomadblacky.gsex.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.nomadblacky.gsex.exceptions.AuthenticationFailedException;
import net.nomadblacky.gsex.exceptions.MyPageAccessFailedException;
import net.nomadblacky.gsex.exceptions.MyPageClientException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * TODO: javadoc書けや
 * @author blacky
 *
 */
public class MypageClient {
	
	private final String _nesicaId;
	private final String _playerName;
	
	private final CloseableHttpClient client;
	private final BasicCookieStore cookieStore;

	public MypageClient(String nesicaId, String playerName) {
		
		this._nesicaId   = nesicaId;
		this._playerName = playerName;
		
    	// クライアントのリクエスト設定
    	RequestConfig requestConfig = RequestConfig.custom()
    			.setRedirectsEnabled(true)
    			.build();
    	
    	// クッキーストア生成
    	cookieStore = new BasicCookieStore();
    	
    	// クライアント生成
    	client = HttpClientBuilder.create()
    			.setDefaultRequestConfig(requestConfig)
    			.setDefaultCookieStore(cookieStore)
    			.build();
    	

	}
	
	public String getNesicaId() {
		return _nesicaId;
	}
	
	public String getPlayerName() {
		return _playerName;
	}

	public boolean doAuth() throws MyPageClientException, IOException {
		
		// 認証ページへのリクエスト設定
		HttpPost post = new HttpPost("https://mypage.groovecoaster.jp/sp/login/auth_con.php");
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("nesysCardId", _nesicaId));
		params.add(new BasicNameValuePair("playerName", _playerName));
		post.setEntity(new UrlEncodedFormEntity(params));
		
		try(CloseableHttpResponse response = client.execute(post)) {
			
			int statusCode = response.getStatusLine().getStatusCode();
			
			if( statusCode <= 199 || 500 <= statusCode ) {
				throw new MyPageAccessFailedException(statusCode);
			}
			
			if( !hasSession() ) {
				throw new AuthenticationFailedException(_nesicaId, _playerName);
			}
		}
		return true;
	}

	private boolean hasSession() {
		
		for(Cookie cookie : cookieStore.getCookies()) {
			
			String name   = cookie.getName();
			String domain = cookie.getDomain();
			if(name.equals("PHPSESSID") && domain.equals("mypage.groovecoaster.jp")) {
				return true;
			}
		}
		return false;
	}
	
	public List<Integer> getMusicIdList() throws MyPageClientException, IOException {
		
		
		List<Integer> idlist = new ArrayList<Integer>();
		HttpGet get = new HttpGet("https://mypage.groovecoaster.jp/sp/json/music_list.php");

		try(CloseableHttpResponse response = client.execute(get)) {
			
			int statusCode = response.getStatusLine().getStatusCode();
			
			if(!(statusCode == HttpStatus.SC_OK)) {
				System.err.println("楽曲ページへのアクセスに失敗");
				System.err.println("HTTPステータースコード: " + statusCode);
				return null;
			}
			
			HttpEntity body = response.getEntity();
			JSONObject jsonRoot = new JSONObject(EntityUtils.toString(body));
			
			if(jsonRoot.getInt("status") == 1) {
				System.err.println("JSONステータスが不正");
				return null;
			}
			
			JSONArray musics = jsonRoot.getJSONArray("music_list");
			
			for(int i = 0; i < musics.length(); i++) {
				idlist.add(musics.getJSONObject(i).getInt("music_id"));
			}
		}
		return idlist;
		
	}
	
	public JSONObject getMusicJson(int i) throws IOException {
		
		String jsonString = null;
		
		// 楽曲jsonヘアクセス
		HttpGet get = new HttpGet("https://mypage.groovecoaster.jp/sp/json/music_detail.php?music_id=" + i);
		try(CloseableHttpResponse response = client.execute(get)) {
			
			int statusCode = response.getStatusLine().getStatusCode();
			
			if(statusCode == HttpStatus.SC_OK) {
				HttpEntity body = response.getEntity();
				jsonString = EntityUtils.toString(body);
			}
			else {
				return new JSONObject("{\"status\":-1}");
			}
		}
		
		return new JSONObject(jsonString);
	}
	
}

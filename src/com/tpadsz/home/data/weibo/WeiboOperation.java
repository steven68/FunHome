package com.tpadsz.home.data.weibo;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.weibo.sdk.android.Oauth2AccessToken;
import com.weibo.sdk.android.Weibo;
import com.weibo.sdk.android.WeiboAuthListener;

public class WeiboOperation
{
	public final static boolean _DEBUG_ = false;
	public final static String KEEP_ACCESSTOKEN_FILE = "keep_accessToken_File";
	private final static String APPKEY = "2140310582";
	private final static String REDIRECT_URL = "https://api.weibo.com/oauth2/default.html";
	private Context mContext;
	private Weibo mWeibo;

	public WeiboOperation(Context context)
	{
		mContext = context;
	}

	public void keepAccessToken(Oauth2AccessToken mAccessToken)
	{
		SharedPreferences preferences = mContext.getSharedPreferences(KEEP_ACCESSTOKEN_FILE, Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putString("token", mAccessToken.getToken());
		editor.putLong("expires_time", mAccessToken.getExpiresTime());
		editor.commit();
	}

	public Oauth2AccessToken getAccessToken()
	{
		Oauth2AccessToken mAccessToken = new Oauth2AccessToken();
		SharedPreferences preferences = mContext.getSharedPreferences(KEEP_ACCESSTOKEN_FILE, Context.MODE_PRIVATE);
		String token = preferences.getString("token", "");
		long expires_time = preferences.getLong("expires_time", 0);
		mAccessToken.setToken(token);
		mAccessToken.setExpiresTime(expires_time);
		return mAccessToken;
	}

	public void authorize(WeiboAuthListener authListener)
	{
		if (mWeibo == null) mWeibo = Weibo.getInstance(APPKEY, REDIRECT_URL);
		mWeibo.authorize(mContext, authListener);
	}

}

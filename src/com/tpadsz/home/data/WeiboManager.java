package com.tpadsz.home.data;

import org.json.JSONException;

import android.content.Context;
import android.os.Bundle;

import com.tpadsz.home.data.weibo.JSONBaseParser;
import com.tpadsz.home.data.weibo.WeiboOperation;
import com.weibo.sdk.android.Oauth2AccessToken;
import com.weibo.sdk.android.WeiboAuthListener;
import com.weibo.sdk.android.api.StatusesAPI;

public class WeiboManager
{
	private Context mContext;
	private WeiboOperation mOperation;

	public WeiboManager(Context context)
	{
		mContext = context;
		mOperation = new WeiboOperation(mContext);
	}

	public void keepAccessToken(Oauth2AccessToken token)
	{
		mOperation.keepAccessToken(token);
	}

	public Oauth2AccessToken obtainAccessTokenFromCache()
	{
		return mOperation.getAccessToken();
	}

	public Oauth2AccessToken obtainAccessTokenFromBundle(Bundle b)
	{
		String token = b.getString("access_token");
		String expires_in = b.getString("expires_in");
		Oauth2AccessToken mAccessToken = new Oauth2AccessToken(token, expires_in);
		return mAccessToken;
	}

	public void authorize(WeiboAuthListener mAuthListener)
	{
		mOperation.authorize(mAuthListener);
	}

	public StatusesAPI createStatusesApi(Oauth2AccessToken token)
	{
		return new StatusesAPI(token);
	}

	public void parsedData(JSONBaseParser<?> parser, String json)
	{
		try
		{
			Object obj = parser.parser(json);
			parser.onParsedComplete(obj);
		}
		catch (JSONException e)
		{
			parser.onParsedException(e);
		}
	}

}

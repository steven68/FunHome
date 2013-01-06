package com.tpadsz.home.data.weibo;

import org.json.JSONException;

public interface JSONBaseParser<T>
{
	public T parser(String json) throws JSONException;

	public void onParsedComplete(Object line);

	public void onParsedException(JSONException e);
}

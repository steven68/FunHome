package com.tpadsz.home.data.weibo;

import org.json.JSONException;

public interface ParserListener
{
	public void parsedComplete(Object obj);

	public void parsedException(JSONException e);
}

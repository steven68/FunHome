package com.tpadsz.home.jni.json;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONParser
{
	public static JSONProgram parseJSON(String json)
	{
		JSONProgram mJsonProgram = null;
		try
		{
			JSONObject mProgram = new JSONObject(json);
			mJsonProgram = new JSONProgram();
			mJsonProgram.pid = mProgram.getInt("pid");
			mJsonProgram.pname = mProgram.getString("pname");
			mJsonProgram.pversion = mProgram.getString("pversion");
			mJsonProgram.ppath = mProgram.getString("ppath");
			mJsonProgram.pagecount = mProgram.getInt("pagecount");
			JSONArray mPages = mProgram.getJSONArray("page");
			int mPagesLength = mPages.length();
			mJsonProgram.pages = new HashMap<Integer, JSONPage>();
			for (int i = 0; i < mPagesLength; i++)
			{
				JSONObject mPage = mPages.getJSONObject(i);
				JSONPage mJsonPage = new JSONPage();
				mJsonPage.id = mPage.getInt("id");
				mJsonPage.name = mPage.getString("name");

				JSONArray mSpirits = mPage.getJSONArray("spirits");
				int mSpiritsLength = mSpirits.length();
				mJsonPage.spirits = new HashMap<Integer, JSONSpirit>();
				for (int j = 0; j < mSpiritsLength; j++)
				{
					JSONObject mSpirit = mSpirits.getJSONObject(j);
					JSONSpirit mJsonSpirit = new JSONSpirit();
					mJsonSpirit.spirit_id = mSpirit.getInt("spirit_id");
					mJsonSpirit.spirit_name = mSpirit.getString("spirit_name");
					mJsonPage.spirits.put(mJsonSpirit.spirit_id, mJsonSpirit);
				}
				mJsonProgram.pages.put(mJsonPage.id, mJsonPage);
			}
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		return mJsonProgram;
	}

}

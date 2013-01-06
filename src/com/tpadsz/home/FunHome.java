package com.tpadsz.home;

import java.io.IOException;
import java.io.InputStream;

import org.cocos2dx.lib.Cocos2dxActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.tpadsz.home.jni.ProgramManager;
import com.tpadsz.home.jni.ProgramManager.LoadfileCallback;
import com.tpadsz.home.jni.json.JSONParser;
import com.tpadsz.home.jni.model.BasePage;
import com.tpadsz.home.view.Program;

public class FunHome extends Cocos2dxActivity implements LoadfileCallback
{
	public final static String TAG = "FunHome";

	static
	{
		System.loadLibrary("game");
	}

	ProgramManager mProgramManager;
	Program mProgram;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		Log.e(TAG, "FunHome is onCreate !!!");
		super.onCreate(savedInstanceState);
		startService(new Intent(this, com.tpadsz.home.service.FunHomeService.class));
		mProgramManager = ProgramManager.getInstance();
		mProgramManager.setLoadFileCallBack(this);
		String str = getFromStream(getResources().openRawResource(R.raw.page_cocos));
		mProgramManager.createProgram(getApplicationContext(),JSONParser.parseJSON(str));
		mProgram = mProgramManager.getProgram();
	}

	@Override
	protected void onNewIntent(Intent intent)
	{
		super.onNewIntent(intent);
		if (intent == null) return;
		Log.e(TAG, intent.toString());
		if (intent.getAction().equals(Intent.ACTION_MAIN) && intent.getCategories().contains(Intent.CATEGORY_HOME))
		{
			// mProgram.gotoPage(BasePage.PAGE_LOCKER);
		}
	}

	public String getFromStream(InputStream is)
	{
		try
		{
			StringBuffer sbIn = new StringBuffer();
			byte[] b = new byte[4056];
			for (int n; (n = is.read(b)) != -1;)
			{
				sbIn.append(new String(b, 0, n));
			}
			return sbIn.toString();
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}
		return null;
	}

	@Override
	public void callback(boolean mResult)
	{
		Log.e(TAG, "loadFileCallback!!!!------>" + mResult);
		mProgram.gotoPage(BasePage.PAGE_LOCKER);
	}

}

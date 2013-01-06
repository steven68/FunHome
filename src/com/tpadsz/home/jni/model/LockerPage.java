package com.tpadsz.home.jni.model;

import android.content.Context;
import android.util.Log;

import com.tpadsz.home.jni.json.JSONProgram;
import com.tpadsz.home.view.MotionEvent;

public class LockerPage extends BasePage
{

	

	public final static String TAG = LockerPage.class.getName();

	public LockerPage(Context context, int pageID, OnSceneChangedListener l, JSONProgram json)
	{
		super(context, pageID, l, json);
	}

	@Override
	protected void onStart(int fromPage)
	{
		Log.e(TAG, "onStart!!!!");
		showAll();
		playAnimation(6, 4, 1, 0);
		playAnimation(7, 5, 2, 0);
		playAnimation(8, 7, 1, 0);
		playAnimation(38, 37, 2, 0);
		handleIntoEnd();
	}

	@Override
	protected void onResume(int fromPage)
	{
		Log.e(TAG, "onResume!!!!");
		getSpiritByid(3).setTouchable(true);
	}

	@Override
	protected void onPause(int gotoPage)
	{
		Log.e(TAG, "onPause!!!!" + gotoPage);
		getSpiritByid(3).setTouchable(false);
		switch (gotoPage)
		{
		case PAGE_UNLOCKER:
			playAnimation(3, 36, 8, 0);
			register(playAnimation(81, 60, 1, 0), "playLight_Alpha");
			break;
		}
	}

	@Override
	protected void onStop(int gotoPage)
	{
		Log.e(TAG, "onStop!!!!");
		disappearSpiritByid(38);
		disappearSpiritByid(81);
	}

	@Override
	public void onTouch(int spiritid, MotionEvent event)
	{
		if (spiritid == 3 && event.getAction() == MotionEvent.ACTION_UP)
		{
			gotoPage(PAGE_UNLOCKER);
		}
	}

	void playLight_Alpha()
	{
		Log.e(TAG, "playLight_Alpha!!!!");
		register(playAnimation(38, 37, 16, 0), "CallbackToUnlock");
	}

	void CallbackToUnlock()
	{
		Log.e(TAG, "CallbackToUnlock!!!!");
		handleOutEnd();
	}
}

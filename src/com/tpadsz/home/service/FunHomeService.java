package com.tpadsz.home.service;

import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

public class FunHomeService extends Service
{

	KeyguardManager mKeyguardManager;

	KeyguardLock mKeyguardLock;

	@Override
	public void onCreate()
	{
		super.onCreate();
		mKeyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
		mKeyguardLock = mKeyguardManager.newKeyguardLock("FunHome");
		mKeyguardLock.disableKeyguard();
	}

	@Override
	public IBinder onBind(Intent intent)
	{
		return null;
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
		mKeyguardLock.reenableKeyguard();
	}
	
}

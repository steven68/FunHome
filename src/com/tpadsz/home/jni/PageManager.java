package com.tpadsz.home.jni;

import java.util.HashMap;

import android.content.Context;

import com.tpadsz.home.jni.json.JSONProgram;
import com.tpadsz.home.jni.model.ApplistPage;
import com.tpadsz.home.jni.model.BasePage;
import com.tpadsz.home.jni.model.BasePage.OnSceneChangedListener;
import com.tpadsz.home.jni.model.ContactShortCutPage;
import com.tpadsz.home.jni.model.HomePage;
import com.tpadsz.home.jni.model.InfoBoxPage;
import com.tpadsz.home.jni.model.LockerPage;
import com.tpadsz.home.jni.model.UnlockerPage;

public class PageManager
{
	private HashMap<Integer, BasePage> MAP_PID_PAGES = new HashMap<Integer, BasePage>();

	private static PageManager mPageManager;
	private JSONProgram mJsonProgram;
	private OnSceneChangedListener mChangedListener;

	private Context mContext;

	private PageManager(Context context, JSONProgram p, OnSceneChangedListener l)
	{
		mContext = context;
		mJsonProgram = p;
		mChangedListener = l;
	}

	public static PageManager getInstance(Context context, JSONProgram p, OnSceneChangedListener l)
	{
		if (mPageManager == null) mPageManager = new PageManager(context, p, l);
		return mPageManager;
	}

	public boolean containPageID(int pid)
	{
		return MAP_PID_PAGES.containsKey(pid);
	}

	public BasePage getPagebyId(int pid)
	{
		return MAP_PID_PAGES.get(pid);
	}

	public boolean removePagebyId(int pid)
	{
		return MAP_PID_PAGES.remove(pid) != null;
	}

	public BasePage createPage(int pageID)
	{
		if (containPageID(pageID)) return MAP_PID_PAGES.get(pageID);
		BasePage mBasePage = null;
		switch (pageID)
		{
		case BasePage.PAGE_LOCKER:
			mBasePage = new LockerPage(mContext, pageID, mChangedListener, mJsonProgram);
			break;
		case BasePage.PAGE_UNLOCKER:
			mBasePage = new UnlockerPage(mContext, pageID, mChangedListener, mJsonProgram);
			break;
		case BasePage.PAGE_HOME:
			mBasePage = new HomePage(mContext, pageID, mChangedListener, mJsonProgram);
			break;
		case BasePage.PAGE_APPLIST:
			mBasePage = new ApplistPage(mContext, pageID, mChangedListener, mJsonProgram);
			break;
		case BasePage.PAGE_INFOBOX:
			mBasePage = new InfoBoxPage(mContext, pageID, mChangedListener, mJsonProgram);
			break;
		case BasePage.PAGE_CONTACTSHORTCUT:
			mBasePage = new ContactShortCutPage(mContext, pageID, mChangedListener, mJsonProgram);
			break;
		default:
			return null;
		}
		MAP_PID_PAGES.put(pageID, mBasePage);
		return mBasePage;
	}
}

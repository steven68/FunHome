package com.tpadsz.home.view;

import android.content.Context;
import android.util.Log;

import com.tpadsz.home.jni.PageManager;
import com.tpadsz.home.jni.json.JSONProgram;
import com.tpadsz.home.jni.model.BasePage;
import com.tpadsz.home.jni.model.BasePage.OnSceneChangedListener;

public class Program implements OnSceneChangedListener
{

	public final static String TAG = Program.class.getName();

	private int programID;

	private String programName;

	private String programPath;

	private String programVersionCode;

	protected int currentPageNum;

	protected int nextPageNum;

	protected PageManager mPageManager;

	public Program(Context context,JSONProgram p)
	{
		this.programID = p.pid;
		this.programPath = p.ppath;
		this.programName = p.pname;
		this.programVersionCode = p.pversion;
		this.currentPageNum = -1;
		mPageManager = PageManager.getInstance(context,p, Program.this);
	}

	public void gotoPage(int page)
	{
		nextPageNum = page;
		BasePage mOperation = mPageManager.createPage(currentPageNum == -1 ? page : currentPageNum);
		if (currentPageNum == -1)
		{
			mOperation.intoSceneFromPage(BasePage.PAGE_DEFAULT);
		}
		else
		{
			mOperation.outSceneGotoPage(page);
		}
	}

	public BasePage getPageByid(int id)
	{
		return mPageManager.getPagebyId(id);
	}

	public BasePage getCurrentPage()
	{
		return mPageManager.getPagebyId(currentPageNum);
	}

	public int getCurrentPageNum()
	{
		return currentPageNum;
	}

	public String getPath()
	{
		return programPath;
	}

	@Override
	public void onIntoSceneEnd(int pageID, int fromPage)
	{
		Log.e("", "onIntoSceneEnd + (" + pageID + " <--- " + fromPage + ")");
		currentPageNum = pageID;
	}

	@Override
	public void onOutSceneEnd(int pageID, int gotoPage)
	{
		Log.e("", "onOutSceneEnd + (" + pageID + " ---> " + gotoPage + ")");
		currentPageNum = gotoPage;
		mPageManager.createPage(gotoPage).intoSceneFromPage(pageID);
	}

	@Override
	public String toString()
	{
		return String.format("ProgramID[%d] , ProgramName[%s] , ProgramPath[%s] , ProgramVersion[%s]", programID, programName, programPath, programVersionCode);
	}
}

/****************************************************************************
Copyright (c) 2010-2011 cocos2d-x.org

http://www.cocos2d-x.org

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
 ****************************************************************************/
package org.cocos2dx.lib;

import java.io.File;

import org.cocos2dx.lib.Cocos2dxHelper.Cocos2dxHelperListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Message;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

public abstract class Cocos2dxActivity extends Activity implements Cocos2dxHelperListener
{
	// ===========================================================
	// Constants
	// ===========================================================
	private static final String TAG = Cocos2dxActivity.class.getSimpleName();

	// ===========================================================
	// Fields
	// ===========================================================
	protected Cocos2dxGLSurfaceView mGLSurefaceView;

	private Cocos2dxHandler mHandler;

	// ===========================================================
	// Constructors
	// ===========================================================

	@Override
	protected void onCreate(final Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.init();
		Cocos2dxHelper.init(this, this);
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

//	@Override
//	protected void onResume()
//	{
//		super.onResume();
//		Cocos2dxHelper.onResume();
//		this.mGLSurefaceView.onResume();
//	}
//
//	@Override
//	protected void onPause()
//	{
//		super.onPause();
//		Cocos2dxHelper.onPause();
//		this.mGLSurefaceView.onPause();
//	}

	@Override
	public void showDialog(final String pTitle, final String pMessage)
	{
		Message msg = new Message();
		msg.what = Cocos2dxHandler.HANDLER_SHOW_DIALOG;
		msg.obj = new Cocos2dxHandler.DialogMessage(pTitle, pMessage);
		this.mHandler.sendMessage(msg);
	}

	@Override
	public void showEditTextDialog(final String pTitle, final String pContent, final int pInputMode, final int pInputFlag, final int pReturnType, final int pMaxLength)
	{
		Message msg = new Message();
		msg.what = Cocos2dxHandler.HANDLER_SHOW_EDITBOX_DIALOG;
		msg.obj = new Cocos2dxHandler.EditBoxMessage(pTitle, pContent, pInputMode, pInputFlag, pReturnType, pMaxLength);
		this.mHandler.sendMessage(msg);
	}

	@Override
	public void runOnGLThread(final Runnable pRunnable)
	{
		this.mGLSurefaceView.queueEvent(pRunnable);
	}

	// ===========================================================
	// Methods
	// ===========================================================

	public void init()
	{
		// Init handler
		this.mHandler = new Cocos2dxHandler(this);

		// FrameLayout
		ViewGroup.LayoutParams framelayout_params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
		FrameLayout framelayout = new FrameLayout(this);
		framelayout.setLayoutParams(framelayout_params);

		// Cocos2dxEditText layout
		ViewGroup.LayoutParams edittext_layout_params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		Cocos2dxEditText edittext = new Cocos2dxEditText(this);
		edittext.setLayoutParams(edittext_layout_params);

		// ...add to FrameLayout
		framelayout.addView(edittext);

		// Cocos2dxGLSurfaceView
		this.mGLSurefaceView = this.onCreateGLSurfaceView();

		// ...add to FrameLayout
		framelayout.addView(mGLSurefaceView);

		mGLSurefaceView.setCocos2dxRenderer(new Cocos2dxRenderer());
		mGLSurefaceView.setCocos2dxEditText(edittext);

		// Set framelayout as the content view
		setContentView(framelayout);
	}

	public Cocos2dxGLSurfaceView onCreateGLSurfaceView()
	{
		return new Cocos2dxGLSurfaceView(this);
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}

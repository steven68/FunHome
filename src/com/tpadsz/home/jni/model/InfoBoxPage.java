package com.tpadsz.home.jni.model;

import android.content.Context;

import com.tpadsz.home.jni.json.JSONProgram;
import com.tpadsz.home.view.MotionEvent;

public class InfoBoxPage extends BasePage
{

	public final static String TAG = InfoBoxPage.class.getName();

	public InfoBoxPage(Context context, int pageID, OnSceneChangedListener l, JSONProgram json)
	{
		super(context, pageID, l, json);
	}

	@Override
	protected void onStart(int fromPage)
	{
		showAll();
		switch (fromPage)
		{
		case PAGE_HOME:
			playBackground_Move_Posi();
			break;
		}
	}

	@Override
	protected void onResume(int fromPage)
	{
		getSpiritByid(43).setTouchable(true);
	}

	@Override
	protected void onPause(int gotoPage)
	{
		getSpiritByid(43).setTouchable(false);
		switch (gotoPage)
		{
		case PAGE_HOME:
			playInfobox_MoveScale_Versa();
			break;
		}

	}

	@Override
	protected void onStop(int gotoPage)
	{
		disappearAll();
	}

	@Override
	public void onTouch(int spiritid, MotionEvent event)
	{
		if (spiritid == 43 && event.getAction() == MotionEvent.ACTION_UP)
		{
			gotoPage(PAGE_HOME);
		}
	}

	void playBackground_Move_Posi()
	{
		register(playAnimation(71, 49, 2, 0), "playBallLabelBox_MoveSeq_Posi");
	}

	void playBackground_Move_Versa()
	{
		register(playAnimation(71, 49, 2, 1), "CallbackOutToHome");
	}

	void playBallLabelBox_MoveSeq_Posi()
	{
		playAnimation(72, 50, 2, 0);
		playAnimation(43, 51, 2, 0);
		playAnimation(74, 52, 2, 0);
		register(playAnimation(73, 53, 1, 0), "playInfobox_MoveScale_Posi");
	}

	void playBallLabelBox_MoveSeq_Versa()
	{
		playAnimation(72, 50, 2, 1);
		playAnimation(43, 51, 2, 1);
		playAnimation(74, 52, 2, 1);
		register(playAnimation(73, 53, 1, 1), "playBackground_Move_Versa");
	}

	void playInfobox_MoveScale_Posi()
	{
		new Thread()
		{
			@Override
			public void run()
			{
				super.run();
				try
				{
					playAnimation(75, 54, 18, 0);
					sleep(50);
					playAnimation(76, 55, 18, 0);
					sleep(50);
					playAnimation(77, 56, 18, 0);
					sleep(50);
					playAnimation(78, 57, 18, 0);
					sleep(50);
					playAnimation(79, 58, 18, 0);
					sleep(50);
					playAnimation(80, 59, 2, 0);
					register(playAnimation(80, 59, 16, 0), "CallbackIntoFromHome");
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}.start();
	}

	void playInfobox_MoveScale_Versa()
	{
		new Thread()
		{
			@Override
			public void run()
			{
				super.run();
				try
				{
					playAnimation(75, 54, 16, 1);
					sleep(50);
					playAnimation(76, 55, 16, 1);
					sleep(50);
					playAnimation(77, 56, 16, 1);
					sleep(50);
					playAnimation(78, 57, 16, 1);
					sleep(50);
					playAnimation(79, 58, 16, 1);
					sleep(50);
					register(playAnimation(80, 59, 16, 1), "playBallLabelBox_MoveSeq_Versa");

				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}.start();
	}

	void CallbackIntoFromHome()
	{
		handleIntoEnd();
	}

	void CallbackOutToHome()
	{
		handleOutEnd();
	}

}

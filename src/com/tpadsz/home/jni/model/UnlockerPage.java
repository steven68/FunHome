package com.tpadsz.home.jni.model;

import android.content.Context;
import android.util.Log;

import com.tpadsz.home.jni.json.JSONProgram;
import com.tpadsz.home.view.MotionEvent;

public class UnlockerPage extends BasePage
{

	public final static String TAG = UnlockerPage.class.getName();

	public UnlockerPage(Context context, int pageID, OnSceneChangedListener l, JSONProgram json)
	{
		super(context, pageID, l, json);
	}

	@Override
	protected void onStart(int fromPage)
	{
		showAll();
		switch (fromPage)
		{
		case PAGE_CONTACTSHORTCUT:
		case PAGE_LOCKER:
			handleIntoEnd();
			break;
		case PAGE_HOME:
			playCurtainRedBox_Move_Versa();
			break;
		}
	}

	@Override
	protected void onResume(int fromPage)
	{
		Log.e(TAG, "onResume comeFrom : " + fromPage);

		getSpiritByid(3).setTouchable(true);
		getSpiritByid(13).setTouchable(true);
	}

	@Override
	protected void onPause(int gotoPage)
	{
		getSpiritByid(3).setTouchable(false);
		getSpiritByid(13).setTouchable(false);
		switch (gotoPage)
		{
		case PAGE_HOME:
			playHeart_Seq_Posi();
			break;
		case PAGE_CONTACTSHORTCUT:
			handleOutEnd();
			break;
		}
	}

	@Override
	protected void onStop(int gotoPage)
	{
		switch (gotoPage)
		{
		case PAGE_HOME:
			disappearSpiritByid(4);
			disappearSpiritByid(7);
			for (int i = 9; i < 22; i++)
				disappearSpiritByid(i);
			break;
		case PAGE_CONTACTSHORTCUT:
			break;
		}
	}

	@Override
	public void onTouch(int spiritid, MotionEvent event)
	{
		if (spiritid == 3 && event.getAction() == MotionEvent.ACTION_UP)
		{
			gotoPage(PAGE_HOME);
		}
		else if (spiritid == 13 && event.getAction() == MotionEvent.ACTION_UP)
		{
			gotoPage(PAGE_CONTACTSHORTCUT);
		}
	}

	void playCurtainRedBox_Move_Posi()
	{
		playAnimation(22, 21, 3, 0);
		register(playAnimation(7, 61, 2, 0), "CallbackToHome");
	}

	void playCurtainRedBox_Move_Versa()
	{
		playAnimation(7, 61, 2, 1);
		playAnimation(22, 21, 2, 1);
		register(playAnimation(22, 21, 1, 1), "playRightFlower_Move_Versa");
	}

	void playRightFlower_Move_Posi()
	{
		register(playAnimation(8, 7, 2, 0), "playCurtainRedBox_Move_Posi");
	}

	void playRightFlower_Move_Versa()
	{
		register(playAnimation(8, 7, 2, 1), "playYellowBox_Move_Versa");
	}

	void playYellowBox_Move_Posi()
	{
		register(playAnimation(21, 20, 2, 0), "playRightFlower_Move_Posi");
	}

	void playYellowBox_Move_Versa()
	{
		register(playAnimation(21, 20, 2, 1), "playWRLabels_Move_Versa");
	}

	void playWRLables_Rotate_Posi()
	{
		new Thread()
		{
			@Override
			public void run()
			{
				super.run();
				try
				{
					playAnimation(17, 16, 8, 0);
					sleep(50);
					playAnimation(19, 18, 8, 0);
					sleep(50);
					playAnimation(18, 17, 8, 0);
					sleep(50);
					// register(playAnimation(20, 19, 8, 0),
					// "playYellowBox_Move_Posi");
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}.start();
	}

	void playWRLables_Rotate_Versa()
	{
		new Thread()
		{
			@Override
			public void run()
			{
				super.run();
				try
				{
					playAnimation(17, 16, 8, 1);
					sleep(50);
					playAnimation(19, 18, 8, 1);
					sleep(50);
					playAnimation(18, 17, 8, 1);
					sleep(50);
					register(playAnimation(20, 19, 8, 1), "playYellowBox_Move_Posi");
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}.start();
	}

	void playWRLabels_Move_Posi()
	{
		new Thread()
		{
			@Override
			public void run()
			{
				super.run();
				try
				{
					playAnimation(17, 16, 2, 0);
					sleep(50);
					playAnimation(19, 18, 2, 0);
					sleep(50);
					playAnimation(18, 17, 2, 0);
					sleep(50);
					register(playAnimation(20, 19, 2, 0), "playYellowBox_Move_Posi");
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}.start();
	}

	void playWRLabels_Move_Versa()
	{
		new Thread()
		{
			@Override
			public void run()
			{
				super.run();
				try
				{
					playAnimation(17, 16, 2, 1);
					sleep(50);
					playAnimation(19, 18, 2, 1);
					sleep(50);
					playAnimation(18, 17, 2, 1);
					sleep(50);
					register(playAnimation(20, 19, 2, 1), "playHeart_Scale_Versa");
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}.start();
	}

	void playHeart_Scale_Posi()
	{
		register(playAnimation(3, 2, 4, 0), "playWRLabels_Move_Posi");
	}

	void playHeart_Scale_Versa()
	{
		register(playAnimation(3, 2, 4, 1), "playRedIcon_MA_Versa");
	}

	void playRedIcon_MA_Posi()
	{
		playFlower_Seq_Posi();
		playAnimation(9, 8, 18, 0);
		playAnimation(10, 9, 18, 0);
		playAnimation(11, 10, 18, 0);
		playAnimation(12, 11, 18, 0);
		playAnimation(13, 12, 18, 0);
		playAnimation(14, 13, 18, 0);
		playAnimation(15, 14, 18, 0);
		playAnimation(16, 15, 16, 0);
		register(playAnimation(16, 15, 2, 0), "playHeart_Scale_Posi");
	}

	void playRedIcon_MA_Versa()
	{
		playFlower_Seq_Versa();
		playAnimation(9, 8, 18, 1);
		playAnimation(10, 9, 18, 1);
		playAnimation(11, 10, 18, 1);
		playAnimation(12, 11, 18, 1);
		playAnimation(13, 12, 18, 1);
		playAnimation(14, 13, 18, 1);
		playAnimation(15, 14, 18, 1);
		playAnimation(16, 15, 16, 1);
		register(playAnimation(16, 15, 2, 1), "playHeart_Seq_Versa");
	}

	void playFlower_Seq_Posi()
	{
		playAnimation(4, 3, 1, 0);
	}

	void playFlower_Seq_Versa()
	{
		playAnimation(4, 3, 1, 1);
	}

	void playHeart_Seq_Posi()
	{
		register(playAnimation(3, 2, 1, 0), "playRedIcon_MA_Posi");
	}

	void playHeart_Seq_Versa()
	{
		register(playAnimation(3, 2, 1, 1), "CallbackFromHome");
	}

	void CallbackFromHome()
	{
		handleIntoEnd();
	}

	void CallbackToHome()
	{
		handleOutEnd();
	}

}

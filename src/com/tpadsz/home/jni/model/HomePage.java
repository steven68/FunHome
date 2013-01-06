package com.tpadsz.home.jni.model;

import android.content.Context;

import com.tpadsz.home.jni.json.JSONProgram;
import com.tpadsz.home.view.MotionEvent;

public class HomePage extends BasePage
{
	public final static String TAG = HomePage.class.getName();

	public HomePage(Context context,int pageID, OnSceneChangedListener l, JSONProgram json)
	{
		super(context,pageID, l, json);
	}

	@Override
	protected void onStart(int fromPage)
	{
		showAll();
		switch (fromPage)
		{
		case PAGE_UNLOCKER:
		case PAGE_APPLIST:
		case PAGE_INFOBOX:
			playIntoAnimation();
			break;
		}
	}

	@Override
	protected void onResume(int fromPage)
	{
		getSpiritByid(37).setTouchable(true);
		getSpiritByid(3).setTouchable(true);
	}

	@Override
	protected void onPause(int gotoPage)
	{
		getSpiritByid(37).setTouchable(false);
		getSpiritByid(3).setTouchable(false);
		switch (gotoPage)
		{
		case PAGE_UNLOCKER:
			register(playAnimation(37, 35, 1, 0), "playOutAnimation");
			break;
		case PAGE_APPLIST:
		case PAGE_INFOBOX:
			playOutAnimation();
			break;
		}
	}

	@Override
	protected void onStop(int gotoPage)
	{
		disappearSpiritByid(3);
		for (int i = 23; i < 38; i++)
		{
			disappearSpiritByid(i);
		}
	}

	float down_x = 0;
	float down_y = 0;

	@Override
	public void onTouch(int spiritid, MotionEvent event)
	{
		if (spiritid == 37 && event.getAction() == MotionEvent.ACTION_UP)
		{
			gotoPage(PAGE_UNLOCKER);
		}
		else if (spiritid == 3)
		{
			switch (event.getAction())
			{
			case MotionEvent.ACTION_DOWN:
				down_x = event.getX();
				down_y = event.getY();
				break;
			case MotionEvent.ACTION_MOVE:
				float current_x = event.getX();
				float current_y = event.getY();
				int sam = (int) (Math.pow((current_x - down_x), 2) + Math.pow((current_y - down_y), 2));
				if (sam < 100) return;
				else moveSpiritTo(spiritid, event.getX(), event.getY());
				break;
			case MotionEvent.ACTION_UP:
				float up_x = event.getX();
				float up_y = event.getY();
				int cut = (int) (Math.pow((up_x - down_x), 2) + Math.pow((up_y - down_y), 2));
				if (cut < 100)
				{
					gotoPage(PAGE_INFOBOX);
				}
				else if (cut < 16900)
				{

				}
				else if (cut > 16900)
				{
					gotoPage(PAGE_APPLIST);
				}
				moveSpiritTo(spiritid, 240, 285);
				break;
			}
		}
	}

	void playIntoAnimation()
	{
		new Thread()
		{
			@Override
			public void run()
			{
				super.run();
				try
				{
					playAnimation(24, 22, 16, 0);
					playAnimation(25, 23, 2, 0);
					sleep(80);
					playAnimation(26, 24, 2, 0);
					sleep(80);
					playAnimation(27, 25, 2, 0);
					sleep(80);
					playAnimation(28, 26, 2, 0);
					sleep(80);
					playAnimation(29, 27, 2, 0);
					sleep(80);
					playAnimation(30, 28, 2, 0);
					sleep(80); 
					playAnimation(31, 29, 2, 0);
					sleep(80);
					playAnimation(32, 30, 2, 0);
					playAnimation(33, 31, 2, 0);
					sleep(80);
					playAnimation(34, 32, 2, 0);
					sleep(80);
					playAnimation(35, 33, 2, 0);
					sleep(80);
					playAnimation(36, 34, 2, 0);
					sleep(80);
					register(playAnimation(37, 35, 2, 0), "CallbackInto");
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}.start();
	}

	void playOutAnimation()
	{
		new Thread()
		{
			@Override
			public void run()
			{
				super.run();
				try
				{
					playAnimation(24, 22, 16, 1);
					playAnimation(32, 30, 2, 1);
					playAnimation(36, 34, 2, 1);
					sleep(50);
					playAnimation(31, 29, 2, 1);
					playAnimation(35, 33, 2, 1);
					sleep(50);
					playAnimation(30, 28, 2, 1);
					playAnimation(34, 32, 2, 1);
					sleep(50);
					playAnimation(29, 27, 2, 1);
					playAnimation(33, 31, 2, 1);
					sleep(50);
					playAnimation(28, 26, 2, 1);
					sleep(50);
					playAnimation(27, 25, 2, 1);
					sleep(50);
					playAnimation(26, 24, 2, 1);
					sleep(50);
					playAnimation(37, 35, 2, 1);
					register(playAnimation(25, 23, 2, 1), "CallbackOut");
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();  
				}
			}
		}.start();
	}

	void CallbackInto()
	{
		handleIntoEnd();
	}

	void CallbackOut()
	{
		switch (gotoPage)
		{
		case PAGE_UNLOCKER:
			break;
		case PAGE_APPLIST:
		case PAGE_INFOBOX:
			break;
		}
		handleOutEnd();
	}
}

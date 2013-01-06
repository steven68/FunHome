package com.tpadsz.home.data.applist;

import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;

public class ApplicationInfo
{
	public String title;

	public Intent intent;

	public Bitmap icon;

	/**
	 * Creates the application intent based on a component name and various
	 * launch flags.
	 * 
	 * @param className
	 *            the class name of the component representing the intent
	 * @param launchFlags
	 *            the launch flags
	 */
	public void setActivity(ComponentName className, int launchFlags)
	{
		intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_LAUNCHER);
		intent.setComponent(className);
		intent.setFlags(launchFlags);
	}
	
	@Override
	public String toString()
	{
		String addr = super.toString();
		return String.format("Title[%s] , #[%s]", title, addr);
	}
}

package com.tpadsz.home.data;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.BitmapDrawable;

import com.tpadsz.home.data.applist.ApplicationInfo;

public class ApplicationManager
{

	private Context mContext;
	private PackageManager mPackageManager;
	private List<ApplicationInfo> mProgramInfos;
	private ProgramSort mSort;

	private boolean isLaunched;

	private static ApplicationManager mProgramManager;

	private ApplicationManager(Context context)
	{
		mContext = context;
		mPackageManager = context.getPackageManager();
		mSort = new ProgramSort();
	}

	public static ApplicationManager getInstance(Context context)
	{
		if (mProgramManager == null) mProgramManager = new ApplicationManager(context);
		return mProgramManager;
	}

	private void setLaunched(boolean isLaunched)
	{
		this.isLaunched = isLaunched;
	}

	/**
	 * load all the applications
	 */
	private void loadAllApplication()
	{
		if (isLaunched && mProgramInfos != null) return;
		isLaunched = true;
		Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
		mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		List<ResolveInfo> resolveInfos = mPackageManager.queryIntentActivities(mainIntent, 0);

		final int count = resolveInfos.size();
		if (resolveInfos != null && count > 0)
		{
			mProgramInfos = new ArrayList<ApplicationInfo>(count);
			for (int i = 0; i < count; i++)
			{
				ApplicationInfo programInfo = new ApplicationInfo();
				ResolveInfo info = resolveInfos.get(i);
				programInfo.title = info.loadLabel(mPackageManager).toString();
				programInfo.setActivity(new ComponentName(info.activityInfo.applicationInfo.packageName, info.activityInfo.name), Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
				programInfo.icon = ((BitmapDrawable) info.activityInfo.loadIcon(mPackageManager)).getBitmap();
				mProgramInfos.add(programInfo);
			}
		}
	}

	private char getCharCode(int ascii)
	{
		// if (ascii < 45217 || ascii > 62289) return (char) ascii;
		if ((ascii >= 45217) && (ascii <= 45252)) return 'a';
		else if ((ascii >= 45253) && (ascii <= 45760)) return 'b';
		else if ((ascii >= 45761) && (ascii <= 46317)) return 'c';
		else if ((ascii >= 46318) && (ascii <= 46825)) return 'd';
		else if ((ascii >= 46826) && (ascii <= 47009)) return 'e';
		else if ((ascii >= 47010) && (ascii <= 47296)) return 'f';
		else if ((ascii >= 47297) && (ascii <= 47613)) return 'g';
		else if ((ascii >= 47614) && (ascii <= 48118)) return 'h';
		else if ((ascii >= 48119) && (ascii <= 49061)) return 'j';
		else if ((ascii >= 49062) && (ascii <= 49323)) return 'k';
		else if ((ascii >= 49324) && (ascii <= 49895)) return 'l';
		else if ((ascii >= 49896) && (ascii <= 50370)) return 'm';
		else if ((ascii >= 50371) && (ascii <= 50613)) return 'n';
		else if ((ascii >= 50614) && (ascii <= 50621)) return 'o';
		else if ((ascii >= 50622) && (ascii <= 50905)) return 'p';
		else if ((ascii >= 50906) && (ascii <= 51386)) return 'q';
		else if ((ascii >= 51387) && (ascii <= 51445)) return 'r';
		else if ((ascii >= 51446) && (ascii <= 52217)) return 's';
		else if ((ascii >= 52218) && (ascii <= 52697)) return 't';
		else if ((ascii >= 52698) && (ascii <= 52979)) return 'w';
		else if ((ascii >= 52980) && (ascii <= 53640)) return 'x';
		else if ((ascii >= 53689) && (ascii <= 54480)) return 'y';
		else if ((ascii >= 54481) && (ascii <= 62289)) return 'z';
		else return '#';
	}

	private int getAsciiCode(String str)
	{
		String iso;
		int length = 0;
		try
		{
			iso = new String(str.getBytes(DataConstant.FORMATTER_CODE_GBK), DataConstant.FORMATTER_CODE_ISO_8859_1);
			byte[] buffer = iso.getBytes(DataConstant.FORMATTER_CODE_ISO_8859_1);
			int[] result = new int[buffer.length];
			for (int i = 0; i < buffer.length; i++)
			{
				result[i] = buffer[i] & 0xff;
				length += (result[i] << ((buffer.length - 1 - i) * 8));
			}
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		return length;
	}

	public List<ApplicationInfo> obtainProgramsBySort(boolean update)
	{
		obtainPrograms(update);
		Collections.sort(mProgramInfos, mSort);
		return mProgramInfos;
	}

	public List<ApplicationInfo> obtainPrograms(boolean update)
	{
		setLaunched(!update);
		loadAllApplication();
		return mProgramInfos;
	}

	public void launchProgram(ApplicationInfo program)
	{
		mContext.startActivity(program.intent);
	}

	class ProgramSort implements Comparator<ApplicationInfo>
	{

		@Override
		public int compare(ApplicationInfo object1, ApplicationInfo object2)
		{
			String name_1 = object1.title.toLowerCase().trim().substring(0, 1);
			String name_2 = object2.title.toLowerCase().trim().substring(0, 1);
			int ascii_1 = getAsciiCode(name_1);
			int ascii_2 = getAsciiCode(name_2);
			char c_1 = getCharCode(ascii_1);
			char c_2 = getCharCode(ascii_2);
			int num_app_1 = (int) c_1;
			int num_app_2 = (int) c_2;
			// System.out.println(String.format("Name[1]:%s ,C[1]:%c,Num[1]:%d  ----  Name[2]:%s ,C[2]:%c,Num[2]:%d",
			// object1.title, c_1, num_app_1, object2.title, c_2, num_app_2));
			if (num_app_1 < num_app_2) return -1;
			else if (num_app_1 > num_app_2) return 1;
			else if (ascii_1 < ascii_2) return -1;
			else if (ascii_1 < ascii_2) return 1;
			return 0;
		}
	}

}

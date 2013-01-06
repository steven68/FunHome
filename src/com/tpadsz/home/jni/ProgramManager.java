package com.tpadsz.home.jni;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.tpadsz.home.jni.json.JSONProgram;
import com.tpadsz.home.view.Program;

public class ProgramManager
{
	public static final boolean _DEBUG_ = false;

	private static final String TAG = "ProgramManager";

	private static int mProgramId;

	private static LoadfileCallback mCallback;

	private Program mProgram;

	private static ProgramManager mProgramManager;

	private ProgramManager()
	{
	}

	public static ProgramManager getInstance()
	{
		if (mProgramManager == null) mProgramManager = new ProgramManager();
		return mProgramManager;
	}

	public void setLoadFileCallBack(LoadfileCallback callback)
	{
		mCallback = callback;
	}

	public Program getProgram()
	{
		return mProgram;
	}

	/**
	 * Init program by program path
	 * 
	 * @param programPath
	 *            The path of the program content
	 */
	public int createProgram(Context context,JSONProgram p)
	{
		if (mProgram == null) mProgram = new Program(context,p);
		return mProgramId = NativeInterface.initProgram(p.ppath);
	}

	/**
	 * Destroy the current program
	 */
	public void destroyProgram()
	{
		NativeInterface.destroyProgram(mProgramId);
	}

	/**
	 * Call back when load file complete.
	 * 
	 * @param mResult
	 *            the result of load-file
	 * @param mSize
	 *            size of the data
	 */
	public static void onLoadFileCallback(boolean mResult)
	{
		if (_DEBUG_) Log.e(TAG, String.format("mResult[%s] ", mResult));
		parserJSONInfos();
		mCallback.callback(mResult);
	}

	/**
	 * Get the spirit informations
	 * 
	 * @return json data about spirit
	 */
	static String getInfos()
	{
		return NativeInterface.getInfos(mProgramId);
	}

	/**
	 * Add informations to the collections as
	 * {@link ProgramConstant#Lists_SpiritID} ,
	 * {@link ProgramConstant#Maps_SpiritID_Matched_AnimationID} ,
	 * {@link ProgramConstant#Maps_SpiritName_Matched_SpiritID}
	 */
	static void parserJSONInfos()
	{
		String json = getInfos();

		if (_DEBUG_) Log.e(TAG, "GetInfos : " + json);

		try
		{
			JSONObject data = new JSONObject(json);
			JSONArray spirit_animation = data.getJSONArray("spirit-animation");
			int spirit_animation_len = spirit_animation.length();
			for (int i = 0; i < spirit_animation_len; i++)
			{
				JSONObject spi_anim = spirit_animation.getJSONObject(i);
				ProgramConstant.Maps_AnimationID_Matched_SpiritID.put(spi_anim.getInt("animation_id"), spi_anim.getInt("spirit_id"));
			}

			JSONArray spiritid_spiritname = data.getJSONArray("spiritid-spiritname");
			int spiritid_spiritname_len = spiritid_spiritname.length();
			for (int i = 0; i < spiritid_spiritname_len; i++)
			{
				JSONObject spi_spn = spiritid_spiritname.getJSONObject(i);
				ProgramConstant.Maps_SpiritName_Matched_SpiritID.put(spi_spn.getString("spirit_name"), spi_spn.getInt("spirit_id"));
			}

		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}

	}

	public interface LoadfileCallback
	{
		public void callback(boolean mResult);
	}

}

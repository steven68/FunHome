package com.tpadsz.home.jni.model;

import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.tpadsz.home.jni.ProgramConstant;
import com.tpadsz.home.jni.ProgramManager;
import com.tpadsz.home.jni.json.JSONProgram;
import com.tpadsz.home.jni.json.JSONSpirit;
import com.tpadsz.home.view.Adapter;
import com.tpadsz.home.view.Program;
import com.tpadsz.home.view.Spirit;
import com.tpadsz.home.view.Spirit.OnItemClickListener;
import com.tpadsz.home.view.Spirit.OnTouchListener;
import com.tpadsz.home.view.Spirit.PlayAnimationCallback;

public abstract class BasePage implements PageOperation, PlayAnimationCallback, OnTouchListener, Adapter, OnItemClickListener
{

	public interface OnSceneChangedListener
	{
		/**
		 * Call back on into this page. ( pageID <--- fromPage ).
		 * 
		 * @param pageID
		 *            currentPage id.
		 * @param fromPage
		 *            the page come from .
		 */
		public void onIntoSceneEnd(int pageID, int fromPage);

		/**
		 * Call back on goto the page from this page. ( pageID ---> gotoPage ).
		 * 
		 * @param pageID
		 *            currentPage id.
		 * @param gotoPage
		 *            the page to goto.
		 */
		public void onOutSceneEnd(int pageID, int gotoPage);
	}

	public final static String TAG = BasePage.class.getName();

	public final static boolean _DEBUG_ = true;

	/**
	 * The id of default-page.
	 */
	public final static int PAGE_DEFAULT = -1;

	/**
	 * The id of locker-page.
	 */
	public final static int PAGE_LOCKER = 0;

	/**
	 * The id of unlock-page.
	 */
	public final static int PAGE_UNLOCKER = 1;

	/**
	 * The id of home-page.
	 */
	public final static int PAGE_HOME = 2;

	/**
	 * The id of application-list-page.
	 */
	public final static int PAGE_APPLIST = 3;

	/**
	 * The id of info-box-page.
	 */
	public final static int PAGE_INFOBOX = 4;

	/**
	 * The id of contact-shortcut-page.
	 */
	public final static int PAGE_CONTACTSHORTCUT = 5;

	/**
	 * Contain the all spirits in current page. <br>
	 * KEY[spirit_id] --- VALUE[spirit_obj].
	 */
	protected final HashMap<Integer, Spirit> MAP_SID_SPIRIT = new HashMap<Integer, Spirit>();

	/**
	 * Contain the all the running-animations in current page.<br>
	 * KEY[animation_id] --- VALUE[method_name].
	 */
	protected final HashMap<Integer, String> MAP_RUNKEY_METHODNAME = new HashMap<Integer, String>();

	/**
	 * Collection of the jsonSpirits. Only can be given a value.
	 */
	protected final HashMap<Integer, JSONSpirit> MAP_SID_JSONSP;

	protected int mPageID, comeFromPage, gotoPage;

	protected String mPageName;

	protected Program mProgram;

	protected Context mContext;

	private OnSceneChangedListener mSceneChangedListener;

	private JSONProgram mJsonProgram;

	public BasePage(Context context, int pageID, OnSceneChangedListener l, JSONProgram json)
	{
		this.mContext = context;
		this.mPageID = pageID;
		this.mJsonProgram = json;
		this.mSceneChangedListener = l;
		MAP_SID_JSONSP = mJsonProgram.getPageByid(pageID).spirits;
		mProgram = ProgramManager.getInstance().getProgram();
	}

	/**
	 * Create the specified spirit by id.<br>
	 * if it has been exists return the spirit in maps.
	 * 
	 * @param spiritid
	 *            the spirit id.
	 * @return the spirit which be created.
	 */
	protected Spirit createSpiritByid(int spiritid)
	{
		if (!MAP_SID_SPIRIT.containsKey(spiritid))
		{
			MAP_SID_SPIRIT.put(spiritid, spiritid < 8000 ? new Spirit(spiritid) : new Spirit(spiritid, this, this));
		}
		return MAP_SID_SPIRIT.get(spiritid);
	}

	/**
	 * Goto the specified page.
	 * 
	 * @param page
	 *            id of the page.
	 */
	protected void gotoPage(int page)
	{
		mProgram.gotoPage(page);
	}

	/**
	 * Make the spirit which id is spirit-id show.
	 * 
	 * @param spiritid
	 *            id of the spirit.
	 */
	protected void showSpiritByid(int spiritid)
	{
		MAP_SID_SPIRIT.get(spiritid).setVisible(true);
	}

	/**
	 * Make the spirit which id is spirit-id disappear.
	 * 
	 * @param spiritid
	 *            id of the spirit.
	 */
	protected void disappearSpiritByid(int spiritid)
	{
		MAP_SID_SPIRIT.get(spiritid).setVisible(false);
	}

	/**
	 * Move the spirit to the end.
	 * 
	 * @param sp_id
	 *            id of spirit.
	 * @param x
	 *            the x-coordinate of the end.
	 * @param y
	 *            the y-coordinate of the end.
	 * @return if <b>true</b> moved successfully.
	 */
	protected boolean moveSpiritTo(int sp_id, float x, float y)
	{
		Spirit mSpirit = MAP_SID_SPIRIT.get(sp_id);
		boolean result = mSpirit != null;
		if (result) mSpirit.setPosition(x, y);
		return result;
	}

	/**
	 * Move the spirit over a distance.
	 * 
	 * @param sp_id
	 *            id of spirit.
	 * @param x_d
	 *            the distance of x-coordinate.
	 * @param y_d
	 *            the distance of y-coordinate.
	 * @return if <b>true</b> moved successfully.
	 */
	protected boolean moveSpiritBy(int sp_id, float x_d, float y_d)
	{
		Spirit mSpirit = MAP_SID_SPIRIT.get(sp_id);
		boolean result = mSpirit != null;
		if (result)
		{
			float x_coordinate = (float) (mSpirit.x + x_d);
			float y_coordinate = (float) (mSpirit.y + y_d);
			mSpirit.setPosition(x_coordinate, y_coordinate);
		}
		return result;
	}

	/**
	 * Release the specified spirit by id.<br>
	 * 
	 * @param spiritid
	 *            the spirit id.
	 * @return if true - has been released.
	 */
	protected boolean releaseSpiritByid(int spiritid)
	{
		if (!MAP_SID_SPIRIT.containsKey(spiritid)) return false;
		return MAP_SID_SPIRIT.remove(spiritid).release();
	}

	/**
	 * Play the specified animation.
	 * 
	 * @param sp_id
	 *            id of spirit.
	 * @param anim_id
	 *            id of animation.
	 * @param anim_type
	 *            type of animation.
	 * @param order
	 *            order of animation.
	 * @return the key of animation.
	 */
	protected int playAnimation(int sp_id, int anim_id, int anim_type, int order)
	{
		Spirit mSpirit = MAP_SID_SPIRIT.get(sp_id);
		boolean result = mSpirit != null;
		if (result) return mSpirit.playAnimation(anim_id, anim_type, order, ProgramConstant.GetIndexForAnimation());
		else throw new RuntimeException("There is not exists the spirit which id is :" + sp_id);
	}

	/**
	 * Create all the spirits in the configured file.
	 */
	protected void createAllSpirits()
	{
		for (Integer sp_id : MAP_SID_JSONSP.keySet())
		{
			Spirit sp = createSpiritByid(sp_id);
			sp.setOnTouchListener(this);
			sp.setPlayAnimationCallback(this);
		}
	}

	protected void showAll()
	{
		// TODO
		for (Spirit sp : MAP_SID_SPIRIT.values())
			sp.setVisible(true);
	}

	protected void showAll(int i)
	{
		for (Spirit sp : MAP_SID_SPIRIT.values())
		{
			if (sp.spirit_id == i) continue;
			sp.setVisible(true);
		}
	}

	protected void disappearAll()
	{
		for (Spirit sp : MAP_SID_SPIRIT.values())
			sp.setVisible(false);
	}

	/**
	 * Invoked the specified method by name.
	 * 
	 * @param name
	 *            the method name
	 * @return if true - invoked correctly.
	 */
	protected boolean invokeMethod(String name)
	{
		if (name == null) return false;
		try
		{
			getClass().getDeclaredMethod(name, new Class[] {}).invoke(this, new Object[] {});
			return true;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return false;
	}

	/**
	 * Invoked the specified method by key and removed the key.
	 * 
	 * @param key
	 *            the animation key.
	 * @return if true - invoked correctly.
	 */
	protected boolean invokeMethod(int key)
	{
		return MAP_RUNKEY_METHODNAME.containsKey(key) ? invokeMethod(MAP_RUNKEY_METHODNAME.remove(key)) : false;
	}

	/**
	 * Take care of this key , invoked the method at the key be received.
	 * 
	 * @param key
	 *            the animation key.
	 * @param mName
	 *            the method name.
	 * @return if true - register successfully ,otherwise the key has been
	 *         exists.
	 */
	protected boolean register(int key, String mName)
	{
		return MAP_RUNKEY_METHODNAME.put(key, mName) == null;
	}

	/**
	 * Don't need to care about this key . It's usually been invoked.
	 * 
	 * @param key
	 *            the key will be unregistered.
	 * @return if true - unregister successfully , otherwise there is no key.
	 */
	protected boolean unregister(int key)
	{
		return MAP_RUNKEY_METHODNAME.remove(key) != null;
	}

	/**
	 * Invoked by child page at the end of into-scene.
	 */
	protected void handleIntoEnd()
	{
		onResume(comeFromPage);
		mSceneChangedListener.onIntoSceneEnd(mPageID, comeFromPage);
	}

	/**
	 * Invoked by child page at the end of out-scene.
	 */
	protected void handleOutEnd()
	{
		onStop(gotoPage);
		mSceneChangedListener.onOutSceneEnd(mPageID, gotoPage);
	}

	/**
	 * Get the pageID .
	 * 
	 * @return page id.Such as <br>{@link #PAGE_DEFAULT} , {@link #PAGE_LOCKER} ,<br>
	 *         {@link #PAGE_UNLOCKER} , {@link #PAGE_HOME} ,<br>
	 *         {@link #PAGE_APPLIST} , {@link #PAGE_INFOBOX} ,<br>
	 *         {@link #PAGE_CONTACTSHORTCUT}
	 *         <p>
	 *         if <i>-1</i> the page id is {@link #PAGE_DEFAULT}
	 */
	public int getPageID()
	{
		return this.mPageID;
	}

	/**
	 * Get the spirit by spirit_id.
	 * 
	 * @param spiritid
	 *            id of spirit.
	 * @return if <i>null</i> there is no spirit matched this spirit id
	 */
	public Spirit getSpiritByid(int spiritid)
	{
		return MAP_SID_SPIRIT.get(spiritid);
	}

	/**
	 * Whether contains this spirit id.
	 * 
	 * @param spiritid
	 *            id of the spirit.
	 * @return if <b>true</b> there is exists the spirit id.
	 */
	public boolean containsSpirit(int spiritid)
	{
		return MAP_SID_SPIRIT.containsKey(spiritid);
	}

	@Override
	public void intoSceneFromPage(int fromPage)
	{
		this.comeFromPage = fromPage;
		this.gotoPage = PAGE_DEFAULT;
		this.createAllSpirits();
		onStart(fromPage);
	}

	@Override
	public void outSceneGotoPage(int gotoPage)
	{
		this.gotoPage = gotoPage;
		this.comeFromPage = PAGE_DEFAULT;
		onPause(gotoPage);
	}

	@Override
	public void onPlayedBegin(int mSpriteId, int mAnimationId, int mAnimationTypeId, int key)
	{
		if (_DEBUG_) Log.i(TAG, String.format("onPlayedBegin --> sp_id[%d] , anim_id[%d] , anim_type[%d] , key[%d]", mSpriteId, mAnimationId, mAnimationTypeId, key));
	}

	@Override
	public void onPlayedEnd(int mSpriteId, int mAnimationId, int mAnimationTypeId, int key)
	{
		if (_DEBUG_) Log.i(TAG, String.format("onPlayedEnd --> sp_id[%d] , anim_id[%d] , anim_type[%d] , key[%d]", mSpriteId, mAnimationId, mAnimationTypeId, key));
		boolean result = invokeMethod(key);
		if (_DEBUG_) Log.i(TAG, String.format("invoked key[%d]  -->  %s", key, result));
	}

	@Override
	public void onPlayedError(int mSpriteId, int mAnimationId, int mAnimationTypeId, int key)
	{
		if (_DEBUG_) Log.i(TAG, String.format("onPlayedError --> sp_id[%d] , anim_id[%d] , anim_type[%d] , key[%d]", mSpriteId, mAnimationId, mAnimationTypeId, key));
	}

	@Override
	public void onItemClick(int listid, int index)
	{
		Log.e(TAG, String.format("ListViewID[%d] , index[%d]", listid, index));
	}

	@Override
	public long getCount(int listid)
	{
		return 0;
	}

	@Override
	public String getWidthAndHeight(int listid)
	{
		return null;
	}

	@Override
	public Bitmap getData(int listViewId, int position)
	{
		return null;
	}

	/**
	 * Called when the into-animation start play.
	 */
	abstract protected void onStart(int fromPage);

	/**
	 * Called when the into-animation played over.
	 */
	abstract protected void onResume(int fromPage);

	/**
	 * Called when the out-animation start play.
	 */
	abstract protected void onPause(int gotoPage);

	/**
	 * Called when the out-animation played over.
	 */
	abstract protected void onStop(int gotoPage);

}

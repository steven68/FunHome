package com.tpadsz.home.jni;

import java.util.HashMap;

import com.tpadsz.home.jni.model.BasePage;

public class ProgramConstant
{

	/**
	 * The collection of the relationship between animation_id and spirit_id.
	 */
	public final static HashMap<Integer, Integer> Maps_AnimationID_Matched_SpiritID = new HashMap<Integer, Integer>();

	/**
	 * The collection of the relationship between spirit_name and spirit_id.
	 */
	public final static HashMap<String, Integer> Maps_SpiritName_Matched_SpiritID = new HashMap<String, Integer>();

	/**
	 * The collection of the running animations,the relationship between
	 * animation_id and spirit_id.
	 */
	public final static HashMap<Integer, Integer> Maps_Running_Animation = new HashMap<Integer, Integer>();

	/**
	 * The collection of all the pages.
	 */
	public final static HashMap<Integer, BasePage> Maps_All_Page = new HashMap<Integer, BasePage>();

	private static int INDEX_ANIMATION = 0;

	public static int GetIndexForAnimation()
	{
		return INDEX_ANIMATION++;
	}

}

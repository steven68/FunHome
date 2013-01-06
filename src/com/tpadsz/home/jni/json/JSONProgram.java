package com.tpadsz.home.jni.json;

import java.util.HashMap;

public class JSONProgram
{
	public int pid;
	public String pname;
	public String pversion;
	public String ppath;
	public int pagecount;
	public HashMap<Integer, JSONPage> pages;

	public JSONPage getPageByid(int pageid)
	{
		return pages.get(pageid);
	}

}

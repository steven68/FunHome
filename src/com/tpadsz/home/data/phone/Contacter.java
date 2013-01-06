package com.tpadsz.home.data.phone;

import android.graphics.Bitmap;

public class Contacter
{
	/**
	 * Contact's name
	 */
	String cName;

	/**
	 * Contact's email
	 */
	String cEmail;

	/**
	 * Contact's tele_num
	 */
	String cTelnum;

	/**
	 * Contact's group_id
	 */
	String cGroupid;

	/**
	 * Contact's organization
	 */
	String cOrganization;

	/**
	 * Contact's photo
	 */
	Bitmap cPhoto;

	public String getcName()
	{
		return cName;
	}

	public void setcName(String cName)
	{
		this.cName = cName;
	}

	public String getcEmail()
	{
		return cEmail;
	}

	public void setcEmail(String cEmail)
	{
		this.cEmail = cEmail;
	}

	public String getcTelnum()
	{
		return cTelnum;
	}

	public void setcTelnum(String cTelnum)
	{
		this.cTelnum = cTelnum;
	}

	public String getcGroupid()
	{
		return cGroupid;
	}

	public void setcGroupid(String cGroupid)
	{
		this.cGroupid = cGroupid;
	}

	public String getcOrganization()
	{
		return cOrganization;
	}

	public void setcOrganization(String cOrganization)
	{
		this.cOrganization = cOrganization;
	}

	public Bitmap getcPhoto()
	{
		return cPhoto;
	}

	public void setcPhoto(Bitmap cPhoto)
	{
		this.cPhoto = cPhoto;
	}

}

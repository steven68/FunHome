package com.tpadsz.home.data.weibo.dao;

public class Statuse
{
	private String created_at;
	private long id;
	private String mid;
	private String idstr;
	private String text;
	private String source;
	private boolean favorited;
	private boolean truncated;
	private String in_reply_to_status_id;
	private String in_reply_to_user_id;
	private String in_reply_to_screen_name;
	private String geo;
	private User user;
	private int reposts_count;
	private int comments_count;
	private int attitudes_count;
	private int mlevel;
	private Visible visible;

	public String getCreated_at()
	{
		return created_at;
	}

	public void setCreated_at(String created_at)
	{
		this.created_at = created_at;
	}

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public String getMid()
	{
		return mid;
	}

	public void setMid(String mid)
	{
		this.mid = mid;
	}

	public String getIdstr()
	{
		return idstr;
	}

	public void setIdstr(String idstr)
	{
		this.idstr = idstr;
	}

	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}

	public String getSource()
	{
		return source;
	}

	public void setSource(String source)
	{
		this.source = source;
	}

	public boolean isFavorited()
	{
		return favorited;
	}

	public void setFavorited(boolean favorited)
	{
		this.favorited = favorited;
	}

	public boolean isTruncated()
	{
		return truncated;
	}

	public void setTruncated(boolean truncated)
	{
		this.truncated = truncated;
	}

	public String getIn_reply_to_status_id()
	{
		return in_reply_to_status_id;
	}

	public void setIn_reply_to_status_id(String in_reply_to_status_id)
	{
		this.in_reply_to_status_id = in_reply_to_status_id;
	}

	public String getIn_reply_to_user_id()
	{
		return in_reply_to_user_id;
	}

	public void setIn_reply_to_user_id(String in_reply_to_user_id)
	{
		this.in_reply_to_user_id = in_reply_to_user_id;
	}

	public String getIn_reply_to_screen_name()
	{
		return in_reply_to_screen_name;
	}

	public void setIn_reply_to_screen_name(String in_reply_to_screen_name)
	{
		this.in_reply_to_screen_name = in_reply_to_screen_name;
	}

	public String getGeo()
	{
		return geo;
	}

	public void setGeo(String geo)
	{
		this.geo = geo;
	}

	public User getUser()
	{
		return user;
	}

	public void setUser(User user)
	{
		this.user = user;
	}

	public int getReposts_count()
	{
		return reposts_count;
	}

	public void setReposts_count(int reposts_count)
	{
		this.reposts_count = reposts_count;
	}

	public int getComments_count()
	{
		return comments_count;
	}

	public void setComments_count(int comments_count)
	{
		this.comments_count = comments_count;
	}

	public int getAttitudes_count()
	{
		return attitudes_count;
	}

	public void setAttitudes_count(int attitudes_count)
	{
		this.attitudes_count = attitudes_count;
	}

	public int getMlevel()
	{
		return mlevel;
	}

	public void setMlevel(int mlevel)
	{
		this.mlevel = mlevel;
	}

	public Visible getVisible()
	{
		return visible;
	}

	public void setVisible(Visible visible)
	{
		this.visible = visible;
	}

}

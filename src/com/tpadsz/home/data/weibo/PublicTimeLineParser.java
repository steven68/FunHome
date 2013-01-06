package com.tpadsz.home.data.weibo;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tpadsz.home.data.weibo.dao.PublicTimeLine;
import com.tpadsz.home.data.weibo.dao.Statuse;
import com.tpadsz.home.data.weibo.dao.User;
import com.tpadsz.home.data.weibo.dao.Visible;

public abstract class PublicTimeLineParser implements JSONBaseParser<PublicTimeLine>
{

	@Override
	public PublicTimeLine parser(String json) throws JSONException
	{
		PublicTimeLine mPublicTimeLine = new PublicTimeLine();
		JSONObject publicTimeLine = new JSONObject(json);
		mPublicTimeLine.setHasvisible(publicTimeLine.getBoolean("hasvisible"));
		mPublicTimeLine.setPrevious_cursor(publicTimeLine.getInt("previous_cursor"));
		mPublicTimeLine.setNext_cursor(publicTimeLine.getInt("next_cursor"));
		mPublicTimeLine.setTotal_number(publicTimeLine.getInt("total_number"));
		JSONArray statuses = publicTimeLine.getJSONArray("statuses");
		List<Statuse> mStatuses = null;
		if (statuses != null && statuses.length() > 0)
		{
			int arrayLen = statuses.length();
			mStatuses = new ArrayList<Statuse>(arrayLen);
			for (int i = 0; i < arrayLen; i++)
			{
				JSONObject statuse = statuses.getJSONObject(i);
				Statuse mStatuse = new Statuse();
				mStatuse.setCreated_at(statuse.getString("created_at"));
				mStatuse.setId(statuse.getLong("id"));
				mStatuse.setMid(statuse.getString("mid"));
				mStatuse.setIdstr(statuse.getString("idstr"));
				mStatuse.setText(statuse.getString("text"));
				mStatuse.setSource(statuse.getString("source"));
				mStatuse.setFavorited(statuse.getBoolean("favorited"));
				mStatuse.setTruncated(statuse.getBoolean("truncated"));
				mStatuse.setIn_reply_to_status_id(statuse.getString("in_reply_to_status_id"));
				mStatuse.setIn_reply_to_user_id(statuse.getString("in_reply_to_user_id"));
				mStatuse.setIn_reply_to_screen_name(statuse.getString("in_reply_to_screen_name"));
				mStatuse.setGeo(statuse.getString("geo"));

				JSONObject user = statuse.getJSONObject("user");
				User mUser = new User();
				mUser.setId(user.getLong("id"));
				mUser.setIdstr(user.getString("idstr"));
				mUser.setScreen_name(user.getString("screen_name"));
				mUser.setName(user.getString("name"));
				mUser.setProvince(user.getString("province"));
				mUser.setCity(user.getString("city"));
				mUser.setLocation(user.getString("location"));
				mUser.setDescription(user.getString("description"));
				mUser.setUrl(user.getString("url"));
				mUser.setProfile_image_url(user.getString("profile_image_url"));
				mUser.setProfile_url(user.getString("profile_url"));
				mUser.setDomain(user.getString("domain"));
				mUser.setWeihao(user.getString("weihao"));
				mUser.setGender(user.getString("gender"));
				mUser.setFollowers_count(user.getInt("followers_count"));
				mUser.setFriends_count(user.getInt("friends_count"));
				mUser.setStatuses_count(user.getInt("statuses_count"));
				mUser.setFavourites_count(user.getInt("favourites_count"));
				mUser.setCreate_at(user.getString("created_at"));
				mUser.setFollowing(user.getBoolean("following"));
				mUser.setAllow_all_act_msg(user.getBoolean("allow_all_act_msg"));
				mUser.setGeo_enabled(user.getBoolean("geo_enabled"));
				mUser.setVerified(user.getBoolean("verified"));
				mUser.setVerified_type(user.getInt("verified_type"));
				mUser.setAllow_all_comment(user.getBoolean("allow_all_comment"));
				mUser.setAvatar_large(user.getString("avatar_large"));
				mUser.setVerified_reason(user.getString("verified_reason"));
				mUser.setFollow_me(user.getBoolean("follow_me"));
				mUser.setOnline_status(user.getInt("online_status"));
				mUser.setBi_followers_count(user.getInt("bi_followers_count"));
				mUser.setLang(user.getString("lang"));
				mUser.setStar(user.getInt("star"));
				mUser.setMbtype(user.getInt("mbtype"));
				mUser.setMbrank(user.getInt("mbrank"));
				mUser.setBlock_word(user.getInt("block_word"));

				mStatuse.setUser(mUser);
				mStatuse.setReposts_count(statuse.getInt("reposts_count"));
				mStatuse.setComments_count(statuse.getInt("comments_count"));
				mStatuse.setAttitudes_count(statuse.getInt("attitudes_count"));
				mStatuse.setMlevel(statuse.getInt("mlevel"));

				JSONObject visible = statuse.getJSONObject("visible");
				Visible mVisible = new Visible();
				mVisible.setType(visible.getInt("type"));
				mVisible.setList_id(visible.getInt("list_id"));

				mStatuse.setVisible(mVisible);
				mStatuses.add(mStatuse);
			}
		}
		mPublicTimeLine.setStatuses(mStatuses);
		return mPublicTimeLine;
	}
}

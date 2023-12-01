package com.example.BrandPlusGoogleAnalytics.repository;

import com.example.BrandPlusGoogleAnalytics.model.GoogleAnalyticsDBModel;
import org.apache.ibatis.annotations.*;

@Mapper
public interface GoogleAuthMyBatisRepository {
//    @Select("select * from google_auth")
//    public List<Object> findAll();
//
//    @Select("SELECT * FROM google_auth WHERE user_id = #{userId}")
//    public Object findByBusinessId(@NotBlank String userID);
//
//    @Delete("DELETE FROM google_auth WHERE user_id = #{userId}")
//    public int deleteByUserID(@NotBlank String userId);
//
    @Insert("INSERT INTO google_auth(user_id, email, access_token, access_token_expires) " +
            " VALUES (#{userID}, #{email}, #{accessToken}, #{accessTokenExpires})")
    public void googleAuthInsert(String userID, String email, String accessToken, String accessTokenExpires);




    @Insert("INSERT INTO google_analytics(user_id, first_time_purchasers, item_revenue, items_purchased, total_purchasers) " +
            " VALUES (#{userId}, #{firstTimePurchasers}, #{itemRevenue}, #{itemsPurchased}, #{totalPurchasers})")
    public void googleAnalyticInsert(GoogleAnalyticsDBModel g);

    @Update("UPDATE google_auth SET access_token=#{accessToken}, access_token_expires= #{accessTokenExpires} WHERE user_id=#{userID}")
    public int updateAccessToken(String userID, String accessToken, String accessTokenExpires);

    @Select("SELECT COUNT(*) FROM google_auth WHERE user_id = #{userID}")
    public int countByUserId(String userID);

    @Select("SELECT access_token_expires FROM google_auth WHERE user_id = #{userID}")
    public Long getAccessExpiryByUserId(String userID);

}

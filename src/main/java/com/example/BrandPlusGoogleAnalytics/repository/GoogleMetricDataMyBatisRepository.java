package com.example.BrandPlusGoogleAnalytics.repository;

import com.example.BrandPlusGoogleAnalytics.model.GoogleAnalyticsDBModel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface GoogleMetricDataMyBatisRepository {

    @Insert("INSERT INTO google_analytics(user_id, property_id, date, total_purchasers, first_time_purchasers, items_purchased, item_revenue, average_purchase_revenue) " +
            " VALUES (#{userId}, #{propertyId}, #{startDate}, #{totalPurchasers}, #{firstTimePurchasers}, #{itemsPurchased}, #{itemRevenue}, #{averagePurchaseRevenue})")
    public void googleAnalyticInsert(GoogleAnalyticsDBModel g);

    @Select("SELECT COUNT(*) FROM google_analytics " +
            "WHERE user_id = #{userId} AND property_id = #{propertyId} AND date = #{startDate}")
    Integer countByUserIdAndDates(GoogleAnalyticsDBModel g);

    @Update("UPDATE google_analytics SET " +
            "total_purchasers = #{totalPurchasers}, " +
            "first_time_purchasers = #{firstTimePurchasers}, " +
            "items_purchased = #{itemsPurchased}, " +
            "item_revenue = #{itemRevenue}, " +
            "average_purchase_revenue = #{averagePurchaseRevenue}" +
            "WHERE user_id = #{userId} AND date = #{startDate} AND property_id = #{propertyId}")
    public void googleAnalyticsUpdate(GoogleAnalyticsDBModel g);
}

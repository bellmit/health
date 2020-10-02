package com.health.dao;

import com.health.pojo.OrderSetting;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderSettingDao {
    //通过日期查询预约设置
    OrderSetting findByOrderDate(Date orderDate);

    //通过日期更新最大预约数
    void updateNumber(OrderSetting orderSetting);

    //批量导入预约设置
    void add(OrderSetting orderSetting);

    //通过月份获取预约设置信息
    List<Map<String, Integer>> getOrderSettingBetween(@Param("startDate") String startDate, @Param("endDate") String endDate);

    int editReservationsByOrderDate(OrderSetting orderSetting);
}

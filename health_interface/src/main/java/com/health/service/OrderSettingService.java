package com.health.service;

import com.health.HealthException;
import com.health.pojo.OrderSetting;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface OrderSettingService {

    //批量导入预约设置
    void add(List<OrderSetting> list);

    //通过月份获取预约设置信息

    List<Map<String, Integer>> getOrderSettingByMonth(String month);
    //通过日期设置预约信息

    void editNumberByDate(OrderSetting os) throws HealthException;
}

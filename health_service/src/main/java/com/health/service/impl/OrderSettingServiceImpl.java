package com.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.health.HealthException;
import com.health.dao.OrderSettingDao;
import com.health.pojo.OrderSetting;
import com.health.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @program: health_parent
 * @description:
 * @author: hw
 * @create: 2020-09-27 13:25
 **/
@Service(interfaceClass = OrderSettingService.class)
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    private OrderSettingDao orderSettingDao;

    //批量导入预约设置
    @Override
    @Transactional
    public void add(List<OrderSetting> list) throws HealthException {
        if (null != list && list.size() > 0) {
            for (OrderSetting orderSetting : list) {
                //通过日期查询预约设置
                OrderSetting osInDB = orderSettingDao.findByOrderDate(orderSetting.getOrderDate());
                //存在
                if (null != osInDB) {
                    //要设置的最大预约数不能小于已预约数
                    if (orderSetting.getNumber() < osInDB.getReservations()) {
                        throw new HealthException("最大预约数不能小于已预约数");
                    }
                    //更新最大预约数
                    orderSettingDao.updateNumber(orderSetting);
                } else {
                    //不存在
                    orderSettingDao.add(orderSetting);
                }
            }
        }
    }

    //通过月份获取预约设置信息
    @Override
    public List<Map<String, Integer>> getOrderSettingByMonth(String month) {
        //month：当前选择的日期
        //拼接开始日期与结束日期
        String startDate = month + "-1";
        String endDate = month + "-31";
        List<Map<String, Integer>> monthDate = orderSettingDao.getOrderSettingBetween(startDate, endDate);
        return monthDate;
    }

    //通过日期设置预约信息
    @Override
    public void editNumberByDate(OrderSetting os) throws HealthException{
        OrderSetting osInDB = orderSettingDao.findByOrderDate(os.getOrderDate());
        // 存在
        if(null != osInDB) {
            //    是否 要设置的最大预约数量 < 已预约数量
            if(os.getNumber() < osInDB.getReservations()) {
                //       是 抛出异常
                throw new HealthException("最大预约数量不能小于已预约数量");
            }
            //       否，通过日期更新最大预约数
            orderSettingDao.updateNumber(os);
        }else {
            // 不存在
            //   插入预约设置
            orderSettingDao.add(os);
        }
    }
}

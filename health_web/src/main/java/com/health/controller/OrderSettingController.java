package com.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.health.constant.MessageConstant;
import com.health.entity.Result;
import com.health.pojo.OrderSetting;
import com.health.service.OrderSettingService;
import com.health.utils.POIUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @program: health_parent
 * @description:
 * @author: hw
 * @create: 2020-09-27 13:15
 **/
@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {
    private static final Logger log = LoggerFactory.getLogger(OrderSettingController.class);

    @Reference
    private OrderSettingService orderSettingService;

    /**
     * 批量导入预约设置
     */
    @PostMapping("/upload")
    public Result upload(MultipartFile excelFile) {
        // 读取excel内容
        try {
            List<String[]> strings = POIUtils.readExcel(excelFile);
            // 转成List<OrderSetting>
            List<OrderSetting> list = new ArrayList<OrderSetting>(strings.size());
            OrderSetting os = null;
            SimpleDateFormat sdf = new SimpleDateFormat(POIUtils.DATE_FORMAT);
            for (String[] strArr : strings) {
                //strArr 代表着一行记录 0日期，1：数量
                Date orderDate = sdf.parse(strArr[0]);
                os = new OrderSetting(orderDate, Integer.valueOf(strArr[1]));
                list.add(os);
            }
            // 调用服务
            orderSettingService.add(list);
            // 返回结果给页面
            return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            //e.printStackTrace();
            log.error("批量导入失败",e);
        }
        return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
    }

    /**
     * 通过月份获取预约设置信息
     */
    @GetMapping("/getOrderSettingByMonth")
    public Result getOrderSettingByMonth(String month) {
        List<Map<String, Integer>> monthData = orderSettingService.getOrderSettingByMonth(month);
        return new Result(true, MessageConstant.GET_ORDERSETTING_SUCCESS, monthData);
    }

    /**
     * 通过日期设置预约信息
     */
    @PostMapping("/editNumberByDate")
    public Result editNumberByDate(@RequestBody OrderSetting os) {
        orderSettingService.editNumberByDate(os);
        return new Result(true, MessageConstant.ORDERSETTING_SUCCESS);
    }
}

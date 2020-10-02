package com.health.service;

import com.health.HealthException;
import com.health.pojo.Order;

import java.util.Map;

public interface OrderService {
    Order submit(Map<String, String> orderInfo) throws HealthException;

    Map<String, String> findById(int id);
}

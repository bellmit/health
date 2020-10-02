package com.health.service;

import com.health.HealthException;
import com.health.entity.PageResult;
import com.health.entity.QueryPageBean;
import com.health.pojo.Setmeal;

import java.util.List;
import java.util.Map;

public interface SetmealService {


    //新增套餐
    void add(Setmeal setmeal, Integer[] checkgroupIds);

    //套餐分页条件查询
    PageResult<Setmeal> findPage(QueryPageBean queryPageBean);

    //通过id查询套餐信息
    Setmeal findById(int id);

    //查询选中的检查组id
    List<Integer> findCheckGroupIdsBySetmealId(int id);

    //编辑套餐
    void update(Setmeal setmeal, Integer[] checkgroupIds);

    //删除套餐
    void deleteById(int id) throws HealthException;

    //查询数据库中的所有图片
    List<String> findImgs();

    //查询所有
    List<Setmeal> findAll();

    //查询套餐详情
    Setmeal findDetailById3(int id);

    //套餐预约占比
    List<Map<String, Object>> getSetmealReport();
}

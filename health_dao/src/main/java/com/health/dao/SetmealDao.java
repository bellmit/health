package com.health.dao;

import com.github.pagehelper.Page;
import com.health.pojo.CheckGroup;
import com.health.pojo.CheckItem;
import com.health.pojo.Setmeal;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SetmealDao {
    //新增套餐
    void add(Setmeal setmeal);

    //添加套餐与检查组关系
    void addSetmealCheckgroup(@Param("setmealId") Integer setmealId, @Param("checkgroupId") Integer checkgroupId);

    //套餐分页条件查询
    Page<Setmeal> findByCondition(String queryString);

    //通过id查询套餐信息
    Setmeal findById(int id);

    //查询选择的检查组id集合
    List<Integer> findCheckGroupIdsBySetmealId(int id);

    //更新套餐
    void update(Setmeal setmeal);

    //删除旧关系
    void deleteSetmealCheckGroup(Integer id);

    //判断是否被订单使用
    int findCountBySetmealId(int id);

    //删除套餐
    void deleteById(int id);

    //获取数据库所有图片
    List<String> findImgs();

    //查询所有套餐
    List<Setmeal> findAll();

    List<CheckGroup> findCheckGroupBySetmealId(int id);

    List<CheckItem> findCheckItemsByCheckGroupId(Integer id);

    //套餐预约占比
    List<Map<String, Object>> getSetmealReport();
}

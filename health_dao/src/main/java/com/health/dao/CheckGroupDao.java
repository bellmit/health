package com.health.dao;

import com.github.pagehelper.Page;
import com.health.pojo.CheckGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CheckGroupDao {
    //新增检查组
    void add(CheckGroup checkGroup);

    //添加检查组与检查项关系
    void addCheckGroupCheckItem(@Param("checkGroupId") Integer checkGroupId, @Param("checkitemId") Integer checkitemId);

    //检查组分页查询
    Page<CheckGroup> findPage(String queryString);

    //通过id查询
    CheckGroup findById(int id);

    //findCheckItemIdsByCheckGroup
    List<Integer> findCheckItemIdsByCheckGroup(int id);

    //更新检查组信息
    void update(CheckGroup checkGroup);

    //删除旧关系
    void deleteCheckGroupCheckItem(Integer id);

    //查看检查组是否被套餐使用
    int findSetmealCountByCheckGroupId(int id);

    //删除检查组
    void deleteById(int id);

    //查询所有检查组
    List<CheckGroup> findAll();
}

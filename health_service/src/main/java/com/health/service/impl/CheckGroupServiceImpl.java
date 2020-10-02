package com.health.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.health.HealthException;
import com.health.dao.CheckGroupDao;
import com.health.entity.PageResult;
import com.health.entity.QueryPageBean;
import com.health.pojo.CheckGroup;
import com.health.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @program: health_parent
 * @description: 检查组业务层实现类
 * @author: hw
 * @create: 2020-09-27 00:14
 **/
@Service(interfaceClass = CheckGroupService.class)
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    private CheckGroupDao checkGroupDao;

    //新增检查组
    @Override
    @Transactional
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
        //新增检查组
        checkGroupDao.add(checkGroup);
        //获取检查组的id
        Integer checkGroupId = checkGroup.getId();
        //遍历id、添加检查组与检查项关系
        if (null != checkitemIds) {
            for (Integer checkitemId : checkitemIds) {
                checkGroupDao.addCheckGroupCheckItem(checkGroupId, checkitemId);
            }
        }
    }

    //检查组分页查询
    @Override
    public PageResult<CheckGroup> findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());

        //有查询条件时进行模糊查询处理
        if (!StringUtils.isEmpty(queryPageBean.getQueryString())) {
            queryPageBean.setQueryString("%" + queryPageBean.getQueryString() + "%");
        }
        Page<CheckGroup> page = checkGroupDao.findPage(queryPageBean.getQueryString());
        return new PageResult<CheckGroup>(page.getTotal(), page.getResult());
    }

    //通过id查询
    @Override
    public CheckGroup findById(int id) {
        return checkGroupDao.findById(id);
    }

    //通过检查组id查询选择的检查项id
    @Override
    public List<Integer> findCheckItemIdsByCheckGroup(int id) {
        return checkGroupDao.findCheckItemIdsByCheckGroup(id);
    }

    //提交修改
    @Override
    public void update(CheckGroup checkGroup, Integer[] checkitemIds) {
        //1.更新检查组信息
        checkGroupDao.update(checkGroup);
        //2.删除旧关系
        checkGroupDao.deleteCheckGroupCheckItem(checkGroup.getId());
        //3.添加关系
        if (null != checkitemIds) {
            for (Integer checkitemId : checkitemIds) {
                checkGroupDao.addCheckGroupCheckItem(checkGroup.getId(), checkitemId);
            }
        }
    }

    //删除检查组
    @Override
    @Transactional
    public void deleteById(int id) throws HealthException {
//        //判断检查组有没有被套餐使用
//        int cnt = checkGroupDao.findSetmealCountByCheckGroupId(id);
//        if (cnt > 0) {
//            //被使用了、要先删除检查组与检查项的关系
//            checkGroupDao.deleteCheckGroupCheckItem(id);
//            //再删除检查组
//            checkGroupDao.deleteById(id);
//        }
//    }
        int count = checkGroupDao.findSetmealCountByCheckGroupId(id);
        if (count > 0) {
            throw new HealthException("检查组已经被套餐使用了、不能删除");
        }
        checkGroupDao.deleteCheckGroupCheckItem(id);
        checkGroupDao.deleteById(id);
    }

    //查询所有
    @Override
    public List<CheckGroup> findAll() {
        return checkGroupDao.findAll();
    }
}

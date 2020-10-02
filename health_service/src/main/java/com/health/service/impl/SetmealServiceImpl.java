package com.health.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.health.HealthException;
import com.health.dao.SetmealDao;
import com.health.entity.PageResult;
import com.health.entity.QueryPageBean;
import com.health.pojo.CheckGroup;
import com.health.pojo.CheckItem;
import com.health.pojo.Setmeal;
import com.health.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @program: health_parent
 * @description:
 * @author: hw
 * @create: 2020-09-27 11:44
 **/
@Service(interfaceClass = SetmealService.class)
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealDao setmealDao;

    //新增套餐
    @Override
    @Transactional
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        //1添加套餐
        setmealDao.add(setmeal);
        //2获取新增套餐的id
        Integer setmealId = setmeal.getId();
        //3遍历选择的检查组id
        if (null != checkgroupIds) {
            for (Integer checkgroupId : checkgroupIds) {
                //添加套餐与检查组关系
                setmealDao.addSetmealCheckgroup(setmealId, checkgroupId);
            }
        }
    }

    //套餐分页条件查询
    @Override
    public PageResult<Setmeal> findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        if (!StringUtils.isEmpty(queryPageBean.getQueryString())) {
            queryPageBean.setQueryString("%" + queryPageBean.getQueryString() + "%");
        }
        Page<Setmeal> page = setmealDao.findByCondition(queryPageBean.getQueryString());
        return new PageResult<Setmeal>(page.getTotal(), page.getResult());
    }

    //通过id查询套餐信息
    @Override
    public Setmeal findById(int id) {
        return setmealDao.findById(id);
    }

    //查询选择的检查组id集合
    @Override
    public List<Integer> findCheckGroupIdsBySetmealId(int id) {
        return setmealDao.findCheckGroupIdsBySetmealId(id);
    }

    //编辑套餐
    @Override
    @Transactional
    public void update(Setmeal setmeal, Integer[] checkgroupIds) {
        //1更新套餐
        setmealDao.update(setmeal);
        //2删除旧关系
        setmealDao.deleteSetmealCheckGroup(setmeal.getId());
        //3.添加新关系
        if (null != checkgroupIds) {
            for (Integer checkgroupId : checkgroupIds) {
                setmealDao.addSetmealCheckgroup(setmeal.getId(), checkgroupId);
            }
        }
    }

    /**
     * 删除套餐
     *
     * @param id
     * @throws HealthException
     */
    @Override
    @Transactional
    public void deleteById(int id) throws HealthException {
        //判断是否被订单使用
        int cnt = setmealDao.findCountBySetmealId(id);
        if (cnt > 0) {
            //被使用、报错
            throw new HealthException("该套餐已被订单使用，不能删除");
        }
        //没使用
        //1.删除套餐与检查组关系
        setmealDao.deleteSetmealCheckGroup(id);
        //2.删除套餐
        setmealDao.deleteById(id);
    }

    //获取数据库所有图片
    @Override
    public List<String> findImgs() {
        return setmealDao.findImgs();
    }

    //查询所有套餐
    @Override
    public List<Setmeal> findAll() {
        return setmealDao.findAll();
    }

    //查询套餐详情
    @Override
    public Setmeal findDetailById3(int id) {
        Setmeal setmeal = setmealDao.findById(id);
        //查询套餐下的检查组
        List<CheckGroup> checkGroups = setmealDao.findCheckGroupBySetmealId(id);
        setmeal.setCheckGroups(checkGroups);
        //查询每个检查组下的检查项
        if (checkGroups != null && checkGroups.size() > 0) {
            for (CheckGroup checkGroup : checkGroups) {
                List<CheckItem> checkItems = setmealDao.findCheckItemsByCheckGroupId(checkGroup.getId());
                checkGroup.setCheckItems(checkItems);
            }
        }
        return setmeal;
    }

    //套餐预约占比
    @Override
    public List<Map<String, Object>> getSetmealReport() {
        return setmealDao.getSetmealReport();
    }
}

package com.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.health.dao.MemberDao;
import com.health.pojo.Member;
import com.health.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: health_parent
 * @description:
 * @author: hw
 * @create: 2020-09-27 18:54
 **/
@Service(interfaceClass = MemberService.class)
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDao memberDao;

    @Override
    public Member findByTelephone(String telephone) {
        return memberDao.findByTelephone(telephone);
    }

    @Override
    public void add(Member member) {
        memberDao.add(member);
    }

    @Override
    public List<Integer> getMemberReport(List<String> months) {
        List<Integer> memberConut = new ArrayList<Integer>();
        if (null != months && months.size() > 0) {
            for (String month : months) {
                //拼接最后一天
                String endDate = month + "-31";
                Integer conut = memberDao.findMemberCountBeforeDate(endDate);
                memberConut.add(conut);
            }
        }
        return memberConut;
    }
}

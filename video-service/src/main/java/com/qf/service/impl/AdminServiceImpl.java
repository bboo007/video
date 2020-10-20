package com.qf.service.impl;

import com.qf.dao.AdminMapper;
import com.qf.pojo.Admin;
import com.qf.pojo.AdminExample;
import com.qf.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Admin findByBean(Admin admin) {
        AdminExample adminExample = new AdminExample();
        AdminExample.Criteria criteria = adminExample.createCriteria();
        criteria.andUsernameEqualTo(admin.getUsername());
        criteria.andPasswordEqualTo(admin.getPassword());
        List<Admin> adminList = adminMapper.selectByExample(adminExample);
        return adminList.isEmpty() ? null : adminList.get(0);
    }
}

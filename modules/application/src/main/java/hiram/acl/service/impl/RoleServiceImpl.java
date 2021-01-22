package hiram.acl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hiram.acl.mapper.RoleMapper;
import hiram.acl.pojo.po.SysRole;
import hiram.acl.pojo.query.RoleAddViewQuery;
import hiram.acl.pojo.query.RoleListViewQuery;
import hiram.acl.pojo.query.RoleUpdateViewQuery;
import hiram.acl.service.RoleService;
import hiram.utils.MyStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @Author: HiramHe
 * @Date: 2020/5/4 14:43
 * @Description: ""
 */

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public SysRole addOne(RoleAddViewQuery roleAddViewQuery) {

        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(roleAddViewQuery,sysRole);

        int rt = roleMapper.insert(sysRole);

        Assert.state(rt>0,"插入角色失败");

        return sysRole;
    }

    @Override
    public Integer deleteByIdPermanently(Long roleId) {
        return roleMapper.deleteByIdPermanently(roleId);
    }

    @Override
    public Integer deleteByIdLogically(Long roleId) {
        return roleMapper.deleteById(roleId);
    }

    @Override
    public Integer updateById(Long roleId, RoleUpdateViewQuery roleUpdateViewQuery) {

        SysRole dbRole = roleMapper.selectById(roleId);

        SysRole role = new SysRole();
        role.setRoleId(roleId);
        role.setVersion(dbRole.getVersion());

        BeanUtils.copyProperties(roleUpdateViewQuery,role);

        int rt = roleMapper.updateById(role);

        return rt;
    }

    @Override
    public List<SysRole> getRolesByUsername(String username) {
        return roleMapper.selectRolesByUsername(username);
    }

    @Override
    public Page<SysRole> list(Long pageNum, Long pageSize, RoleListViewQuery roleListViewQuery) {

        Page<SysRole> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);

        String roleNameZh = roleListViewQuery.getRoleNameZh();
        String roleNameEn = roleListViewQuery.getRoleNameEn();

        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();

        queryWrapper.like(!MyStringUtils.isEmpty(roleNameZh),"role_name_zh",roleNameZh);
        queryWrapper.like(!MyStringUtils.isEmpty(roleNameEn),"role_name_en",roleNameEn);

        Page<SysRole> sysRolePage = roleMapper.selectPage(page, queryWrapper);

        return sysRolePage;
    }

    @Override
    public SysRole getById(Long roleId) {
        return roleMapper.selectById(roleId);
    }
}

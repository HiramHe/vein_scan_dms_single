package hiram.hospital.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hiram.enums.ResultCodeEnum;
import hiram.exception.CustomException;
import hiram.hospital.mapper.DepartmentMapper;
import hiram.hospital.pojo.po.Department;
import hiram.hospital.pojo.po.Ward;
import hiram.hospital.pojo.query.DepartmentAddViewQuery;
import hiram.hospital.pojo.query.DepartmentListViewQuery;
import hiram.hospital.pojo.query.DepartmentUpdateViewQuery;
import hiram.hospital.service.DepartmentService;
import hiram.utils.MyObjectUtils;
import hiram.utils.MyStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * @Author: HiramHe
 * @Date: 2020/12/30 19:27
 * @Description: ""
 */

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public Department add(DepartmentAddViewQuery departmentAddViewQuery) {

        Department department = new Department();
        BeanUtils.copyProperties(departmentAddViewQuery,department);

        int affectRows = departmentMapper.insert(department);

        try {
            Assert.state(affectRows>0,"影响行数为0，插入失败");
        } catch (Exception e) {
            throw new CustomException(ResultCodeEnum.FAILED_INSERT);
        }

        return department;
    }

    @Override
    public Integer deleteByIdPermanently(Long departmentId) {
        return departmentMapper.deleteByIdPermanently(departmentId);
    }

    @Override
    public Integer deleteByIdLogically(Long departmentId) {
        return departmentMapper.deleteById(departmentId);
    }

    @Override
    public Integer updateById(Long departmengId, DepartmentUpdateViewQuery departmentUpdateViewQuery) {

        Department dbDepartment = departmentMapper.selectById(departmengId);

        try {
            Assert.notNull(dbDepartment,"科室不存在");
        } catch (IllegalArgumentException e) {
            throw new CustomException(ResultCodeEnum.RECORD_NOT_EXIST);
        }

        Department department = new Department();
        BeanUtils.copyProperties(departmentUpdateViewQuery,department);
        department.setDepartmentId(departmengId);
        department.setVersion(dbDepartment.getVersion());

        return departmentMapper.updateById(department);
    }

    @Override
    public Department queryById(Long departmentId) {
        return departmentMapper.selectById(departmentId);
    }

    @Override
    public Page<Department> list(Long pageNum, Long pageSize, DepartmentListViewQuery departmentListViewQuery) {

        Page<Department> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);

        QueryWrapper<Department> queryWrapper = new QueryWrapper<>();

        Long departmentId = departmentListViewQuery.getDepartmentId();
        String departmentName = departmentListViewQuery.getDepartmentName();
        LocalDate gmtCreate = departmentListViewQuery.getGmtCreate();

        queryWrapper.eq(!MyObjectUtils.isNull(departmentId),"department_id",departmentId);
        queryWrapper.like(!MyStringUtils.isEmpty(departmentName),"department_name",departmentName);
        queryWrapper.ge(!MyObjectUtils.isNull(gmtCreate),"gmt_create",gmtCreate);

        departmentMapper.selectPage(page, queryWrapper);

        return page;
    }

    @Override
    public List<Ward> getWardsOfDepartmentById(Long departmentId) {
        return departmentMapper.getWardsOfDepartmentById(departmentId);
    }

}

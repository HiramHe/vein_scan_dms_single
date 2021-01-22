package hiram.hospital.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import hiram.hospital.pojo.po.Department;
import hiram.hospital.pojo.po.Ward;
import hiram.hospital.pojo.query.DepartmentAddViewQuery;
import hiram.hospital.pojo.query.DepartmentListViewQuery;
import hiram.hospital.pojo.query.DepartmentUpdateViewQuery;
import io.swagger.models.auth.In;

import java.util.List;

/**
 * @Author: HiramHe
 * @Date: 2020/12/30 19:26
 * @Description: ""
 */


public interface DepartmentService {
    Department add(DepartmentAddViewQuery departmentAddViewQuery);

    Integer deleteByIdPermanently(Long departmentId);

    Integer deleteByIdLogically(Long departmentId);

    Integer updateById(Long departmengId, DepartmentUpdateViewQuery departmentUpdateViewQuery);

    Department queryById(Long departmentId);

    Page<Department> list(Long pageNum, Long pageSize, DepartmentListViewQuery departmentListViewQuery);

    List<Ward> getWardsOfDepartmentById(Long departmentId);
}

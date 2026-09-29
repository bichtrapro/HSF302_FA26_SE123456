package com.hsf302.ch4.service;

import com.hsf302.ch4.dto.DepartmentStatDTO;
import com.hsf302.ch4.pojo.Department;

import java.util.List;

public interface DepartmentService {
    long countDepartments();
    boolean findById(Long id);

    //TODO 11
    List<Department> timDepartmentWithoutSV();

    //TODO 14
    List<DepartmentStatDTO> getStatistics();   // TODO 14 (dùng lại ở TODO 23)

    //TODO 16
    Department getWithStudents(String code);   // TODO 16b
}

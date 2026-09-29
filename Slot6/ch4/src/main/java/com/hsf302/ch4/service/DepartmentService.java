package com.hsf302.ch4.service;

import com.hsf302.ch4.pojo.Department;

import java.util.List;

public interface DepartmentService {
    long countDepartments();
    boolean findById(Long id);

    //TODO 11
    List<Department> timDepartmentWithoutSV();
}

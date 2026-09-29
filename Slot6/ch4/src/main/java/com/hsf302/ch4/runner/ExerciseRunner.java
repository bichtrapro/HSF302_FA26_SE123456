package com.hsf302.ch4.runner;

import com.hsf302.ch4.pojo.Student;
import com.hsf302.ch4.service.DepartmentService;
import com.hsf302.ch4.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@Order(2)
@RequiredArgsConstructor
public class ExerciseRunner implements org.springframework.boot.CommandLineRunner {

    //Runner chỉ phụ thuộc vào Service, không Reject Repository
    private final DepartmentService departmentService;
    private final StudentService studentService;
    @Override
    public void run(String... args) throws Exception {
        //In tổng số departments
       // todo6();
        todo7();
    }
    // ===== helpers =====
    private void title(String t) {
        System.out.println("\n===== " + t + " =====");
    }

    private void printList(String label, Collection<?> list) {
        System.out.println("-- " + label + ":");
        list.forEach(o -> System.out.println("   " + o));
        System.out.println("   -> " + list.size() + " record(s)");
    }
    private void todo6(){
        title("count departments");
        System.out.println("Total departments: " + departmentService.countDepartments());
        System.out.println("Total students: " + studentService.countStudents());

        //Tìm kiếm student id =1 và id = 99
        studentService.findStudentById(1L).ifPresentOrElse(
            student -> System.out.println("Student found: " + student),
            () -> System.out.println("Student not found")
        );
        studentService.findStudentById(99L).ifPresentOrElse(
            student -> System.out.println("Student found: " + student),
            () -> System.out.println("Student not found")
        );
        //Tiìm kiếm department id=4
        System.out.println("Department id=4 exists: " + departmentService.findById(4L));
    }

    private void todo7(){

        //GPA giảm dần
        printList("All students order by GPA Desc", studentService.SapXepGPADesc());

        //Trang thứ 2 - index = 1
        Page<Student> page = studentService.phanTrang(1,3,"fullName");
        System.out.println("totalElements=" + page.getTotalElements()
                + ", totalPages=" + page.getTotalPages()
                + ", hasNext=" + page.hasNext()
                + ", hasPrevious=" + page.hasPrevious());
    }
}

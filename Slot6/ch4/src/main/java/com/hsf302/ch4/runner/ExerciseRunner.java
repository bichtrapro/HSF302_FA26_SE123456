package com.hsf302.ch4.runner;

import com.hsf302.ch4.pojo.Gender;
import com.hsf302.ch4.pojo.Student;
import com.hsf302.ch4.service.DepartmentService;
import com.hsf302.ch4.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

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
        // todo7();
        //todo8();
        //todo9();
        //todo10();
        //todo11();
        //todo12();
       // todo13();
        todo14();
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
        printList("All students order by GPA Desc", studentService.sapXepGPADesc());

        //Trang thứ 2 - index = 1
        Page<Student> page = studentService.phanTrang(1,3,"fullName");
        System.out.println("totalElements=" + page.getTotalElements()
                + ", totalPages=" + page.getTotalPages()
                + ", hasNext=" + page.hasNext()
                + ", hasPrevious=" + page.hasPrevious());
    }
    private void todo8(){
        title("TODO 8: findBy/ existsBy / CountBy");
        title("TODO 8: findBy / existsBy / countBy");
        for (String code : List.of("AI002", "XX999")) {
            System.out.println("findByStudentCode(" + code + ") -> " +
                    studentService.timTheoMaSV(code).map(Object::toString).orElse("Not found"));
        }
        System.out.println("isEmailExisted(binh.tt@fpt.edu.vn) -> "
                + studentService.kiemTraEmailTonTai("binh.tt@fpt.edu.vn"));
        System.out.println("countActive -> " + studentService.demStatusActive());
    }

    private void todo9(){
        title("TODO 9: Containing / EndingWith / IsNull");
        printList("fullName contains 'nguyen'", studentService.timSVTheoTen("nguyen"));
        printList("email domain 'gmail.com'", studentService.timSVTheoEmailDomain("gmail.com"));
        printList("email is null", studentService.timSVKhongCoEmail());
    }
    private void todo10() {
        title("TODO 10: Between / And / True / After");
        printList("GPA in [3.0, 3.6] desc", studentService.timGPABetween(3.0, 3.6));
        printList("MALE & active", studentService.timGenderActive(Gender.MALE));
        printList("dob after 2005-01-01", studentService.timDobAfter(LocalDate.of(2005, 1, 1)));
    }
    private void todo11() {
        title("TODO 11: Nested property / Top / IsEmpty");
        printList("Students of SE (order by name)", studentService.timDepartment("SE"));
        System.out.println("count students of AI -> " + studentService.demSVTheoDepartment("AI"));
        printList("Top 3 GPA", studentService.timTop3SVGpa());
        printList("Departments without students", departmentService.timDepartmentWithoutSV());
    }
    private void todo12() {
        title("TODO 12: JPQL + named parameter");
        printList("SE, GPA >= 3.0", studentService.timGoodStudents("SE", 3.0));
    }
    private void todo13() {
        title("TODO 13: JPQL LIKE");
        printList("keyword 'hoa'", studentService.timSVTheoKeyword("hoa"));
        printList("keyword 'gmail'", studentService.timSVTheoKeyword("gmail"));
    }
    private void todo14() {
        title("TODO 14: Statistics by department (DTO)");
        printList("code | name | total | avgGpa", departmentService.getStatistics());
    }
}

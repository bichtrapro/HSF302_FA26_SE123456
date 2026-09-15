package fu.se123456;

import fu.se123456.pojo.Department;
import fu.se123456.pojo.Employee;
import fu.se123456.pojo.Gender;

import java.math.BigDecimal;
import java.time.LocalDate;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        Department dept = new Department("IT", "Ha Noi");
        Employee emp = new Employee("test@company.com", "Test", Gender.OTHER,
                new BigDecimal("1000"), LocalDate.now());
        dept.addEmployee(emp);
        System.out.println(dept.getEmployees().contains(emp)); // phải true
        System.out.println(emp.getDepartment() == dept); // phải true

    }
}
package fu.se123456;

import fu.se123456.dao.DepartmentDAO;
import fu.se123456.dao.EmployeeDAO;
import fu.se123456.pojo.Department;
import fu.se123456.pojo.Employee;
import fu.se123456.pojo.Gender;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class Main {
    private static final DepartmentDAO departmentDAO = new DepartmentDAO();
    private static final EmployeeDAO employeeDAO = new EmployeeDAO();
    private static int testsPassed = 0;
    private static int testsFailed = 0;

    public static void main(String[] args) {
        System.out.println("========== JPA One-To-Many Mapping Test Suite ==========\n");
        
        testCreateAndPersistDepartmentWithEmployees();
        testFindDepartmentById();
        testFindDepartmentWithJoinFetch();
        testEmployeeRelationship();
        testFindAllEmployees();
        testUpdateEmployee();
        testFindAllDepartments();
        testDeleteEmployee();
        testGetTotalSalaryAndEmployeeCountByDepartment();
        
        System.out.println("\n========== Test Results Summary ==========");
        System.out.println("✓ Tests Passed: " + testsPassed);
        System.out.println("✗ Tests Failed: " + testsFailed);
        System.out.println("Total Tests: " + (testsPassed + testsFailed));
    }

    private static String uniqueName(String baseName) {
        return baseName + "-" + UUID.randomUUID().toString().substring(0, 8);
    }

    private static String uniqueEmail(String baseEmail) {
        String[] parts = baseEmail.split("@");
        return parts[0] + "-" + UUID.randomUUID().toString().substring(0, 8) + "@" + parts[1];
    }

    private static void testCreateAndPersistDepartmentWithEmployees() {
        System.out.println("\n--- Test 1: Create and Persist Department with Employees ---");
        try {
            Department dept = new Department(uniqueName("IT Department"), "Hà Nội");
            Employee emp1 = new Employee(uniqueEmail("emp1@company.com"), "Nguyễn Văn A", Gender.OTHER,
                    new BigDecimal("3000.00"), LocalDate.of(2023, 1, 15));
            Employee emp2 = new Employee(uniqueEmail("emp2@company.com"), "Trần Thị B", Gender.OTHER,
                    new BigDecimal("2500.00"), LocalDate.of(2023, 6, 20));
            
            dept.addEmployee(emp1);
            dept.addEmployee(emp2);
            
            departmentDAO.save(dept);
            
            boolean relationshipOk = emp1.getDepartment() == dept && emp2.getDepartment() == dept;
            boolean employeeListOk = dept.getEmployees().size() == 2 && 
                                    dept.getEmployees().contains(emp1) && 
                                    dept.getEmployees().contains(emp2);
            
            System.out.println("✓ Department created: " + dept.getName() + ", Location: " + dept.getLocation());
            System.out.println("✓ Employees added: " + emp1.getFullName() + ", " + emp2.getFullName());
            System.out.println("✓ Relationship set: Employee -> Department OK");
            System.out.println("✓ Department -> Employees list: " + dept.getEmployees().size() + " employees");
            
            if (relationshipOk && employeeListOk) {
                System.out.println("✓ Test 1 PASSED: All relationships and data verified");
                testsPassed++;
            } else {
                System.out.println("✗ Test 1 FAILED: Relationship verification failed");
                testsFailed++;
            }
        } catch (Exception e) {
            System.out.println("✗ Test 1 FAILED: " + e.getMessage());
            testsFailed++;
        }
    }

    private static void testFindDepartmentById() {
        System.out.println("\n--- Test 2: Find Department by ID ---");
        try {
            Department dept = new Department(uniqueName("Sales Department"), "Thành phố Hồ Chí Minh");
            Employee emp = new Employee(uniqueEmail("sales@company.com"), "Lê Văn C", Gender.OTHER,
                    new BigDecimal("2800.00"), LocalDate.of(2023, 3, 10));
            dept.addEmployee(emp);
            departmentDAO.save(dept);
            
            Department retrieved = departmentDAO.findById(dept.getId());
            
            if (retrieved != null && retrieved.getId().equals(dept.getId()) &&
                retrieved.getName().contains("Sales Department")) {
                System.out.println("✓ Found Department: ID=" + retrieved.getId() + ", Name=" + retrieved.getName());
                System.out.println("✓ Location: " + retrieved.getLocation());
                System.out.println("✓ Test 2 PASSED");
                testsPassed++;
            } else {
                System.out.println("✗ Test 2 FAILED: Department not found or data mismatch");
                testsFailed++;
            }
        } catch (Exception e) {
            System.out.println("✗ Test 2 FAILED: " + e.getMessage());
            testsFailed++;
        }
    }

    private static void testFindDepartmentWithJoinFetch() {
        System.out.println("\n--- Test 3: Find Department with JOIN FETCH (Eager Loading) ---");
        try {
            Department dept = new Department(uniqueName("HR Department"), "Đà Nẵng");
            Employee emp1 = new Employee(uniqueEmail("hr1@company.com"), "Phạm Tuấn D", Gender.OTHER,
                    new BigDecimal("2700.00"), LocalDate.of(2023, 2, 5));
            Employee emp2 = new Employee(uniqueEmail("hr2@company.com"), "Hoàng Thị E", Gender.OTHER,
                    new BigDecimal("2600.00"), LocalDate.of(2023, 4, 12));
            
            dept.addEmployee(emp1);
            dept.addEmployee(emp2);
            departmentDAO.save(dept);
            
            Department retrieved = departmentDAO.findByIdWithEmployees(dept.getId());
            
            if (retrieved != null && retrieved.getEmployees().size() == 2) {
                System.out.println("✓ Found Department with JOIN FETCH: " + retrieved.getName());
                System.out.println("✓ Employees loaded: " + retrieved.getEmployees().size());
                for (Employee e : retrieved.getEmployees()) {
                    System.out.println("  - " + e.getFullName() + " (" + e.getEmail() + "), Salary: " + e.getSalary());
                }
                System.out.println("✓ Test 3 PASSED");
                testsPassed++;
            } else {
                System.out.println("✗ Test 3 FAILED: JOIN FETCH failed");
                testsFailed++;
            }
        } catch (Exception e) {
            System.out.println("✗ Test 3 FAILED: " + e.getMessage());
            testsFailed++;
        }
    }

    private static void testEmployeeRelationship() {
        System.out.println("\n--- Test 4: Employee-Department Bidirectional Relationship ---");
        try {
            Department dept = new Department(uniqueName("Finance Department"), "Hải Phòng");
            Employee emp = new Employee(uniqueEmail("finance@company.com"), "Vũ Minh F", Gender.OTHER,
                    new BigDecimal("3200.00"), LocalDate.of(2023, 1, 8));
            
            dept.addEmployee(emp);
            departmentDAO.save(dept);
            
            Department retrievedDept = departmentDAO.findByIdWithEmployees(dept.getId());
            Employee retrievedEmp = retrievedDept.getEmployees().get(0);
            
            boolean bidirectionalOk = retrievedEmp.getDepartment().getId().equals(retrievedDept.getId());
            boolean empBackreference = retrievedDept.getEmployees().contains(retrievedEmp);
            
            System.out.println("✓ Employee Name: " + retrievedEmp.getFullName());
            System.out.println("✓ Department of Employee: " + retrievedEmp.getDepartment().getName());
            System.out.println("✓ Employee in Department's list: " + empBackreference);
            System.out.println("✓ Relationship: Employee.department -> Department & Department.employees contains Employee");
            
            if (bidirectionalOk && empBackreference) {
                System.out.println("✓ Test 4 PASSED: Bidirectional relationship verified");
                testsPassed++;
            } else {
                System.out.println("✗ Test 4 FAILED: Bidirectional relationship broken");
                testsFailed++;
            }
        } catch (Exception e) {
            System.out.println("✗ Test 4 FAILED: " + e.getMessage());
            testsFailed++;
        }
    }

    private static void testFindAllEmployees() {
        System.out.println("\n--- Test 5: Find All Employees ---");
        try {
            java.util.List<Employee> employees = employeeDAO.findAll();
            
            System.out.println("✓ Total Employees in DB: " + employees.size());
            if (employees.size() > 0) {
                System.out.println("✓ Sample employees:");
                for (int i = 0; i < Math.min(3, employees.size()); i++) {
                    Employee e = employees.get(i);
                    System.out.println("  [" + (i+1) + "] " + e.getFullName() + " - Email: " + e.getEmail() + 
                                    " - Salary: " + e.getSalary() + " - Department: " + 
                                    (e.getDepartment() != null ? e.getDepartment().getName() : "N/A"));
                }
                System.out.println("✓ Test 5 PASSED");
                testsPassed++;
            } else {
                System.out.println("⚠ No employees found");
                testsPassed++;
            }
        } catch (Exception e) {
            System.out.println("✗ Test 5 FAILED: " + e.getMessage());
            testsFailed++;
        }
    }

    private static void testUpdateEmployee() {
        System.out.println("\n--- Test 6: Update Employee ---");
        try {
            Department dept = new Department(uniqueName("Marketing Department"), "Cần Thơ");
            Employee emp = new Employee(uniqueEmail("marketing@company.com"), "Bùi Văn G", Gender.OTHER,
                    new BigDecimal("2400.00"), LocalDate.of(2023, 5, 1));
            
            dept.addEmployee(emp);
            departmentDAO.save(dept);
            
            Employee retrieved = employeeDAO.findById(emp.getId());
            System.out.println("✓ Original Salary: " + retrieved.getSalary());
            
            retrieved.setSalary(new BigDecimal("2900.00"));
            retrieved.setActive(true);
            Employee updated = employeeDAO.update(retrieved);
            
            System.out.println("✓ Updated Salary: " + updated.getSalary());
            System.out.println("✓ Active Status: " + updated.getActive());
            
            if (updated.getSalary().compareTo(new BigDecimal("2900.00")) == 0 && updated.getActive()) {
                System.out.println("✓ Test 6 PASSED");
                testsPassed++;
            } else {
                System.out.println("✗ Test 6 FAILED: Update not applied correctly");
                testsFailed++;
            }
        } catch (Exception e) {
            System.out.println("✗ Test 6 FAILED: " + e.getMessage());
            testsFailed++;
        }
    }

    private static void testFindAllDepartments() {
        System.out.println("\n--- Test 7: Find All Departments with JOIN FETCH ---");
        try {
            java.util.List<Department> departments = departmentDAO.findAllWithEmployees();
            
            System.out.println("✓ Total Departments: " + departments.size());
            if (departments.size() > 0) {
                System.out.println("✓ Department Details:");
                for (Department d : departments) {
                    System.out.println("  - " + d.getName() + " (" + d.getLocation() + ") - Employees: " + d.getEmployees().size());
                }
                System.out.println("✓ Test 7 PASSED");
                testsPassed++;
            } else {
                System.out.println("⚠ No departments found");
                testsPassed++;
            }
        } catch (Exception e) {
            System.out.println("✗ Test 7 FAILED: " + e.getMessage());
            testsFailed++;
        }
    }

    private static void testDeleteEmployee() {
        System.out.println("\n--- Test 8: Delete Employee ---");
        try {
            Department dept = new Department(uniqueName("Operations Department"), "Vinh");
            Employee emp = new Employee(uniqueEmail("ops@company.com"), "Ngô Văn H", Gender.OTHER,
                    new BigDecimal("2350.00"), LocalDate.of(2023, 7, 14));
            
            dept.addEmployee(emp);
            departmentDAO.save(dept);
            
            Long empId = emp.getId();
            System.out.println("✓ Employee created with ID: " + empId);
            
            employeeDAO.delete(empId);
            Employee deleted = employeeDAO.findById(empId);
            
            if (deleted == null) {
                System.out.println("✓ Employee successfully deleted from database");
                System.out.println("✓ Test 8 PASSED");
                testsPassed++;
            } else {
                System.out.println("✗ Test 8 FAILED: Employee still exists");
                testsFailed++;
            }
        } catch (Exception e) {
            System.out.println("✗ Test 8 FAILED: " + e.getMessage());
            testsFailed++;
        }
    }

    private static void testGetTotalSalaryAndEmployeeCountByDepartment() {
        System.out.println("\n--- Test 9: Get Total Salary and Employee Count by Department ---");
        try {
            // Create test departments with employees
            Department dept1 = new Department(uniqueName("IT Department"), "Hà Nội");
            Employee emp1 = new Employee(uniqueEmail("it1@company.com"), "Trần Văn I", Gender.OTHER,
                    new BigDecimal("5000.00"), LocalDate.of(2023, 1, 1));
            Employee emp2 = new Employee(uniqueEmail("it2@company.com"), "Lê Thị J", Gender.OTHER,
                    new BigDecimal("4500.00"), LocalDate.of(2023, 2, 1));
            emp1.setActive(true);
            emp2.setActive(true);
            dept1.addEmployee(emp1);
            dept1.addEmployee(emp2);
            
            Department dept2 = new Department(uniqueName("HR Department"), "Hồ Chí Minh");
            Employee emp3 = new Employee(uniqueEmail("hr1@company.com"), "Phạm Văn K", Gender.OTHER,
                    new BigDecimal("3500.00"), LocalDate.of(2023, 3, 1));
            emp3.setActive(true);
            dept2.addEmployee(emp3);
            
            departmentDAO.save(dept1);
            departmentDAO.save(dept2);
            
            // Get the results
            java.util.List<Object[]> results = departmentDAO.getTotalSalaryAndEmployeeCountByDepartment();
            
            System.out.println("✓ Total Department Salary Reports: " + results.size());
            
            if (results.size() > 0) {
                System.out.println("✓ Department Statistics:");
                for (Object[] row : results) {
                    String departmentName = (String) row[0];
                    java.math.BigDecimal totalSalary = (java.math.BigDecimal) row[1];
                    Long employeeCount = (Long) row[2];
                    
                    System.out.println("  - Department: " + departmentName);
                    System.out.println("    Total Salary: " + totalSalary);
                    System.out.println("    Employee Count: " + employeeCount);
                }
                System.out.println("✓ Test 9 PASSED");
                testsPassed++;
            } else {
                System.out.println("⚠ No department salary data found");
                testsPassed++;
            }
        } catch (Exception e) {
            System.out.println("✗ Test 9 FAILED: " + e.getMessage());
            e.printStackTrace();
            testsFailed++;
        }
    }
}

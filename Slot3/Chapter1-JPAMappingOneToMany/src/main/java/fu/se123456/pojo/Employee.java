package fu.se123456.pojo;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "employees")
public class Employee {
    //Id, name, email, gender (kieu enum), active, salary kiểu BigDecimal, hireDate kiểu LocalDate, department kiểu Department
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fullName")
    private String fullName;

    @Column(name = "email", unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "salary", precision = 10, scale = 2)
    private java.math.BigDecimal salary;

    @Column(name = "hire_date")
    private java.time.LocalDate hireDate;

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public Employee(Long id, String fullName, String email, Gender gender, Boolean active, BigDecimal salary, LocalDate hireDate) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.gender = gender;
        this.active = active;
        this.salary = salary;
        this.hireDate = hireDate;
    }
    public Employee() {
    }
    public Employee(String email, String fullName, Gender gender, BigDecimal salary, LocalDate hireDate) {
        this.email = email;
        this.fullName = fullName;
        this.gender = gender;
        this.salary = salary;
        this.hireDate = hireDate;
    }

}

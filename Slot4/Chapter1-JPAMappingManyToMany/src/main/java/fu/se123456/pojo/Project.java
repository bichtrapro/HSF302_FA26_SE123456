package fu.se123456.pojo;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id1;
    //id, projectCode (unique), projectName, budget (BigDecimal), startDate (LocalDate), endDate (LocalDate, có thể null nếu dự án chưa kết thúc).
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    public Project() {
    }
    public Project(Long id1, Long id, String projectCode, String projectName, BigDecimal budget, LocalDate startDate, LocalDate endDate) {
        this.id1 = id1;
        this.id = id;
        this.projectCode = projectCode;
        this.projectName = projectName;
        this.budget = budget;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Column(unique = true, nullable = false)
    private String projectCode;

    public Long getId1() {
        return id1;
    }

    public void setId1(Long id1) {
        this.id1 = id1;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Column (nullable = false)
    private String projectName;
    @Column(precision = 10, scale = 2)
    private BigDecimal budget;
    @Column(nullable = false)
    private LocalDate startDate;
    @Column
    private LocalDate endDate;
}

package fu.se123456.dao;

import fu.se123456.pojo.Department;
import fu.se123456.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class DepartmentDAO {

    //Khai báo EntityManagerFactory
    private final EntityManagerFactory emf = JPAUtil.getEntityManagerFactory();

    //phương thức save a department
    public void save(Department department) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            //Chỉ ần persist (department) - cascade = ALL persist luôn cả employees khi đã add
            em.persist(department);
            em.getTransaction().commit();
        } catch (RuntimeException ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        } finally {
            em.close();
        }

    }
    public Department findById(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Department.class, id);
        } finally {
            em.close();
        }
    }

    public Department update(Department d) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            // merge() trả về MỘT ENTITY MANAGED KHÁC — phải gán lại kết quả.
            Department merged = em.merge(d);
            em.getTransaction().commit();
            return merged;
        } catch (RuntimeException ex) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    public void delete(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Department d = em.find(Department.class, id);
            if (d != null) {
                // cascade = ALL + orphanRemoval = true sẽ tự xóa luôn Employee thuộc department này.
                em.remove(d);
            }
            em.getTransaction().commit();
        } catch (RuntimeException ex) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    // Dùng để TÁI HIỆN N+1 (TODO 2.8) — KHÔNG JOIN FETCH.
    public List<Department> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT d FROM Department d", Department.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    // TODO 2.6 — JOIN FETCH theo 1 id cụ thể.
    public Department findByIdWithEmployees(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                            "SELECT d FROM Department d JOIN FETCH d.employees WHERE d.id = :id",
                            Department.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } finally {
            em.close();
        }
    }

    // TODO 2.9 — JOIN FETCH toàn bộ, dùng để fix N+1. Bắt buộc DISTINCT.
    public List<Department> findAllWithEmployees() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                            "SELECT DISTINCT d FROM Department d JOIN FETCH d.employees",
                            Department.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }
    //Viết phương thức tính tổng lương của nhân viên, và đếm số nhân viên đang làm theo từng phòng ban
    public List<Object[]> getTotalSalaryAndEmployeeCountByDepartment() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                            "SELECT d.name, SUM(e.salary), COUNT(e) FROM Department d LEFT JOIN d.employees e WHERE e.active = true GROUP BY d.name",
                            Object[].class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

}

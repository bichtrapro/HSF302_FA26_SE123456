package fu.se123456.util;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {
    public static final EntityManagerFactory EMF = Persistence.createEntityManagerFactory("hsf302FU");

    private JPAUtil() {
        // private constructor to prevent instantiation
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        return EMF;
    }

    public static void close() {
        if (EMF.isOpen()) {
            EMF.close();
        }
    }
}

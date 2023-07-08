package ouken.ouken_catalog;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import ouken.ouken_catalog.entity.Category;
import ouken.ouken_catalog.entity.Characteristic;

import java.util.Scanner;

public class CreatCategory1 {
    public static void main(String[] args) {

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("default");
        EntityManager manager = factory.createEntityManager();

        try {
            manager.getTransaction().begin();//новую, отправить, откат
            Scanner scanner = new Scanner(System.in);

            String categoryName = "";

            long a = 1;
            while (a == 1) {
                System.out.println("Введите название категории : ");
                categoryName = scanner.nextLine();
                TypedQuery<Long> categoryTypedQuery = manager.createQuery(
                        "select count (c.id) from Category c where c.name = ?1", Long.class);
                categoryTypedQuery.setParameter(1, categoryName);
                a = categoryTypedQuery.getSingleResult();
                System.out.println(a);
            }
            System.out.println("Введите название характеристики : ");
            String characteristicsIn = scanner.nextLine();
            String[] characteristics = characteristicsIn.split(",\\s*");

            Category category = new Category();
            category.setName(categoryName);
            manager.persist(category);

            for (String x : characteristics) {
                Characteristic characteristic = new Characteristic();
                characteristic.setName(x);
                characteristic.setCategory(category);
                manager.persist(characteristic);
            }
            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
            e.printStackTrace();
        }
    }
}

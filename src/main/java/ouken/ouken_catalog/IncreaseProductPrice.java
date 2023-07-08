package ouken.ouken_catalog;

import jakarta.persistence.*;
import ouken.ouken_catalog.entity.Category;

import java.util.Scanner;

public class IncreaseProductPrice {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("default");
        EntityManager manager = factory.createEntityManager();
        try {
            manager.getTransaction().begin();
            //Изменить цену по категорию
            //если категория нету еще раз спрашивать
            Scanner scanner = new Scanner(System.in);
            String categoryName = "";
            long a = 0;
            while (a == 0) {
                System.out.print("Введите название категории : ");
                categoryName = scanner.nextLine();
                TypedQuery<Long> categoryTypedQuery = manager.createQuery(
                        "select count (c.id) from Category c where c.name = ?1", Long.class);
                categoryTypedQuery.setParameter(1, categoryName);
                a = categoryTypedQuery.getSingleResult();
                //System.out.println(a);
            }

            TypedQuery<Category> categoryTypedQuery = manager.createQuery(
                    "select c from Category c where c.name = ?1", Category.class
            );
            categoryTypedQuery.setParameter(1, categoryName);
            Category category = categoryTypedQuery.getSingleResult();
            //System.out.println(category.getId());

            System.out.print("Введите updatePrice: ");
            Integer updatePrice = Integer.parseInt(scanner.nextLine());

            Query query = manager.createQuery(
                    "update Product p set p.price = p.price * ?2 where p.category.id = ?1"
            );
            query.setParameter(1, category.getId());
            query.setParameter(2, updatePrice);
            query.executeUpdate();

            manager.getTransaction().commit();
        }catch (Exception e){
            manager.getTransaction().rollback();
            e.printStackTrace();
        }
    }
}

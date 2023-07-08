package ouken.ouken_catalog;

import jakarta.persistence.*;
import ouken.ouken_catalog.entity.Category;
import ouken.ouken_catalog.entity.Characteristic;
import ouken.ouken_catalog.entity.Product;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class CreatCategory {

    public static void main(String[] args) throws IOException{

        //После ввода названия категории необходимо добавить проверку на уникальность введенного названия.
        //если есть - стоп, если нет - продолжить

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("default");

        EntityManager manager = factory.createEntityManager();

        try{
            manager.getTransaction().begin();//новую, отправить, откат

            Scanner scanner = new Scanner(System.in);
            String categoryName = scanner.nextLine();

            TypedQuery<String> categoryTypedQuery = manager.createQuery(
                    "select c.name from Category c where c.name = ?1", String.class);
            categoryTypedQuery.setParameter(1, categoryName);
            String s = categoryTypedQuery.getSingleResult();
            System.out.println(s);


                String characteristicsIn = scanner.nextLine();
                String[] characteristics = characteristicsIn.split(",\\s*");

            /*Category category = new Category();
            category.setName(categoryName);
            manager.persist(category);*/

                for (String x : characteristics
                ) {
                /*Characteristic characteristic = new Characteristic();
                characteristic.setName(x);
                characteristic.setCategory(category);
                manager.persist(characteristic);*/
                }

            /*Characteristic characteristic3 = manager.find(Characteristic.class, 12);
            manager.remove(characteristic3);
            Product p = manager.find(Product.class,5);
            manager.remove(p);
            Category category1 = manager.find(Category.class, 11);
            manager.remove(category1);//удаление*/

            manager.getTransaction().commit();
        }catch (Exception e) {
            manager.getTransaction().rollback();
            e.printStackTrace();
        }

    }
}

package ouken.ouken_catalog;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import ouken.ouken_catalog.entity.Category;
import ouken.ouken_catalog.entity.Characteristic;
import ouken.ouken_catalog.entity.Product;
import ouken.ouken_catalog.entity.Value;

import java.util.List;
import java.util.Scanner;

public class CreatProduct {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("default");

        EntityManager manager = factory.createEntityManager();

        try{
            manager.getTransaction().begin();//новую, отправить, откат
            Scanner scanner = new Scanner(System.in);
            TypedQuery<Category> categoryTypedQuery = manager.createQuery(
                    "select c from Category c order by c.id", Category.class);
            List<Category> categories = categoryTypedQuery.getResultList();
            for (Category category : categories
            ) {
                System.out.println(category.getName() + " [" + category.getId() + "]");
            }

            System.out.println("Выберите категорию: ");
            int categoryId = Integer.parseInt(scanner.nextLine());
            System.out.println("Введите название: ");
            String productName = scanner.nextLine();
            System.out.println("Введите стоимость: ");
            int productPrice = Integer.parseInt(scanner.nextLine());

            Category category = manager.find(Category.class, categoryId);
            System.out.println(category.getCharacteristics());

            Product product = new Product();
            product.setName(productName);
            product.setPrice(productPrice);
            product.setCategory(category);
            manager.persist(product);

            for (Characteristic c : category.getCharacteristics()
            ) {
                System.out.println("Введите название: " + c.getName());
                String characteristic_name = scanner.nextLine();
                Characteristic characteristic = manager.find(Characteristic.class, c.getId());
                Value value = new Value();
                value.setProduct(product);
                value.setCharacteristic(characteristic);
                value.setName(characteristic_name);
                manager.persist(value);
            }

            //Characteristic characteristic = manager.find(Characteristic.class, )
            // Выберите категорию: 2
            // Введите название: ___
            // Введите стоимость: ___
            // Производитель: ___
            // Матрица: ___
            // Диагональ: ___
            // Разрешение: ___

            manager.getTransaction().commit();
        }catch (Exception e) {
            manager.getTransaction().rollback();
            e.printStackTrace();
        }
    }
}

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


public class CatalogApplication {

    private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("default");

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        // - Создание товара [1]
        // - Редактирование товара [2]
        // - Удаление товара [3]
        // Выберите действие: ___

        //Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("- Создание товара [1]\n- Редактирование товара [2]" +
                    "\n- Удаление товара [3]\n- Завершить процесс [4]\nВыберите действие: ");
            Integer CSD = Integer.parseInt(scanner.nextLine());
            while (CSD != 4) {
                if (CSD == 1) {
                    create();
                } else if (CSD == 2) {
                    set();
                } else if (CSD == 3) {
                    delete();
                } else {
                    System.out.println("ошибка");
                }
                break;
            }
            if (CSD == 4){
                System.out.println("Процесс завершен");
                break;
            }
        }
    }
    private static void create() {
        EntityManager manager = factory.createEntityManager();
        try{
            manager.getTransaction().begin();//новую, отправить, откат

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
            manager.getTransaction().commit();
        }catch (Exception e) {
            manager.getTransaction().rollback();
            e.printStackTrace();
        }
    }
    private static void set(){
        EntityManager manager = factory.createEntityManager();
        try {
            manager.getTransaction().begin();

            System.out.print("Введите id товара: ");
            Integer product_id = Integer.parseInt(scanner.nextLine());
            Product product = manager.find(Product.class, product_id);
            System.out.print("Введите новое название товара: ");
            String newProduct = scanner.nextLine();

            if (newProduct.equals("")){
                product.setName(product.getName());
            } else  {
                product.setName(newProduct);
            }
            System.out.print("Введите новую цену товара: ");
            String newPrice1 = scanner.nextLine();
            boolean newPrice = newPrice1.matches("\\d*");
            while (!newPrice) {
                System.out.print("Введите новую цену товара: ");
                newPrice1 = scanner.nextLine();
                newPrice = newPrice1.matches("\\d*");
            }
            if (newPrice1.equals("")){
                product.setPrice(product.getPrice());
            }
            else {
                product.setPrice(Integer.parseInt(newPrice1));
            }
            List<Characteristic> characteristics = product.getCategory().getCharacteristics();
            for (Characteristic characteristic : characteristics) {
                // select
                TypedQuery<Value> valueTypedQuery = manager.createQuery(
                        "select v from Value v where v.product.id = ?1 and v.characteristic = ?2", Value.class
                );
                valueTypedQuery.setParameter(1, product_id);
                valueTypedQuery.setParameter(2, characteristic);
                Value value = valueTypedQuery.getSingleResult();
                System.out.println("Введите новое значение " + characteristic.getName() + ": ");
                String newValue = scanner.nextLine();
                if (!(newValue.isEmpty())) {
                    value.setName(newValue);
                }
            }
            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
            e.printStackTrace();
        }
    }
    private static void delete() {
        EntityManager manager = factory.createEntityManager();
        try {
            manager.getTransaction().begin();

            System.out.print("Введите id товара: ");
            Integer product_id = Integer.parseInt(scanner.nextLine());

            Product product = manager.find(Product.class, product_id);
            manager.remove(product);

            for (Value value : product.getValues()) {
                Value v = manager.find(Value.class, value.getId());
                manager.remove(v);
            }
            manager.getTransaction().commit();

        } catch (Exception e) {
            manager.getTransaction().rollback();
            e.printStackTrace();
        }
    }
}

package ouken.ouken_catalog;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import ouken.ouken_catalog.entity.Product;
import ouken.ouken_catalog.entity.Value;

import java.util.Scanner;

public class DeleteProduct {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("default");
        EntityManager manager = factory.createEntityManager();

        try{
            manager.getTransaction().begin();
            Scanner sc = new Scanner(System.in);
            System.out.print("Введите id товара: ");
            Integer product_id = sc.nextInt();

            Product product = manager.find(Product.class, product_id);
            manager.remove(product);

            for (Value value : product.getValues()) {
                Value v = manager.find(Value.class, value.getId());
                manager.remove(v);
            }
            manager.getTransaction().commit();
        }catch (Exception e) {
            manager.getTransaction().rollback();
            e.printStackTrace();
        }
    }
}

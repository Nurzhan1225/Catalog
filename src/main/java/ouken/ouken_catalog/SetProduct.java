package ouken.ouken_catalog;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import ouken.ouken_catalog.entity.Characteristic;
import ouken.ouken_catalog.entity.Category;
import ouken.ouken_catalog.entity.Product;
import ouken.ouken_catalog.entity.Value;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class SetProduct {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("default");
        EntityManager manager = factory.createEntityManager();

        try {
            manager.getTransaction().begin();

            Scanner sc = new Scanner(System.in);
            System.out.print("Введите id товара: ");
            Integer product_id = Integer.parseInt(sc.nextLine());
            //String s = "\n";

            Product product = manager.find(Product.class, product_id);
            System.out.print("Введите новое название товара: ");
            String newProduct = sc.nextLine();

            if (newProduct.equals("")){
                product.setName(product.getName());
            } else  {
                product.setName(newProduct);
            }

//если не число еще раз спрашивать
            System.out.print("Введите новую цену товара: ");
            String newPrice1 = sc.nextLine();
            boolean newPrice = newPrice1.matches("\\d*");
            while (!newPrice) {
                System.out.print("Введите новую цену товара: ");
                newPrice1 = sc.nextLine();
                newPrice = newPrice1.matches("\\d*");
            }
            if (newPrice1.equals("")){
                product.setPrice(product.getPrice());
            }
            else {
                product.setPrice(Integer.parseInt(newPrice1));
            }

//            TypedQuery <Value> valueTypedQuery = manager.createQuery(
//                    "select v from Value v where v.product.id = ?1", Value.class
//            );
//            valueTypedQuery.setParameter(1, product_id);
//            List<Value> values = valueTypedQuery.getResultList();
//            for (Value v:values) {
//                System.out.println("Введите новое значение ("+v.getCharacteristic().getName()+"): ");
//                String newValue = sc.nextLine();
//                v.setName(newValue);
//            }
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
                String newValue = sc.nextLine();
                if (!(newValue.isEmpty())) {
                      value.setName(newValue);
                }
            }


            /*Category category = manager.find(Category.class, product.getCategory().getId());
            System.out.println(category.getCharacteristics());
            for (Characteristic c: category.getCharacteristics()) {
                System.out.println("Введите новое значение ("+c.getName()+"): ");

                Characteristic characteristic = manager.find(Characteristic.class, c.getId());

                if (newValue.equals("")){
                    c.setName(c.getName());
                } else {
                    value.setProduct(product);
                    value.setCharacteristic(characteristic);
                    value.setName(newValue);
                }
            }*/
            /*for (Value value : product.getValues()) {
                System.out.print("Введите новое значение ("+value.getCharacteristic().getName()+"): ");
                String newValue = sc.nextLine();

                if (newValue.equals("")){
                   value.setName(value.getName());
                } else {
                    value.setName(newValue);
                }
            }*/

            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
            e.printStackTrace();
        }
    }
}

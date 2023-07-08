package ouken.ouken_catalog;

import jakarta.persistence.*;
import ouken.ouken_catalog.entity.Category;
import ouken.ouken_catalog.entity.Product;

import java.util.List;

public class JpaLesson {
    public static void main(String[] args) {

        // Maven - отдельная программа выполняющая функции автоматизации сборки для приложений написанных при помощи JDK

        // При использовании JDK мы можем использовать все возможности стандартной библиотеки языка Java которая
        //является довольно обширной, но при этом при разработке больших приложений часто требуется возможности
        // сторонних библиотек не входящий в стандартную библиотеку

        //Зависимость - внешняя библиотека без которой сборка и работа приложения не представляются возможными

        //Процесс добавления зависимости к проекту
        //1) Проверить совместимость версии зависимости с имеющимися инструментами
        //2) Скачать зависимость
        //3) Настроить среду разработки для работы со скаченной зависимостью
        //4) Произвести тестовую сборку и удостоверится что все работает

        // Все перечисленные позволяют автоматизировать системы автоматизации сборки. Maven и Gradle

        //pom.xml - конфигурационный файл системы сборки maven находящийся в корневом каталоге проекте и в котором
        // описывается инфо о проекте, его авторе, версии и используемых зависимостях

        //mvnrepository.com - центральная хранилище зависимостей Maven доступных для использования без доп конф

        //В JDK для работы с базами данных есть инструмент по умолчанию под названием JDBC позволяющий выполнять любые
        // запросы к бозе данных и брать результат выполнения запросов

        //Используя только JDBC и БД не получится построить подключение
        //нужна программная прослойка DRIVER

        //Java Application -> PostgreSQL Java Driver -> PostgreSQL Server


        // Для корректной работы JDBC подключить PostgresSQL Java Driver в качестве зависимости

        //JDBC обладает следующими недостатками
        //1) Все запросы пишутся на SQL специфичном для отдельно взятой БД
        //2) Тип данных для извлечения результата из запроса определяет сам разработчик
        //3) В результате работы с результатом мы получаем не полноценный объект, а отдельно взятую информацию

        // JPA - модуль стандарта Java EE /Jakarta EE описывающий взаимодействие с БД по принципу ORM

        //ORM - (объектно реляционное сопоставление) - модель согласно которой табличные данные должны быть
        // преобразованы в объект специфичный для языка и наоборот

        //Сущность - класс описывающий одну запись определенной таблицы в рамках стандарта JPA

        //JPA - описанный стандарт, то есть руководство, какие должны быть реализованы объекты, но при этом JPA стандарт
        // не представляет реализации описываемых им объектов. Это интерфейс

        // Для пользования JPA подключить одну из библиотек реализаций этого стандарта

        // 1)Hibernate
        // 2)EclipseLink

        // Для использования реализации Hibernate необходимо подключить в качестве зависимости hibernate - core

        // 'persistence.xml' - конфигурационный файл стандарта JPA  в котором можно определять конфигурационный блоки (unit-ы)
        // Данный файл должен находится в папке META-INF


        // 'EntityManagerFactory' - объект через которую строится подключение к серверу БД основываясь на конфигурационном
        // блоке (persistence unit) из файла persistence.xml

        // 'EntityManager' - объект предоставляющий возможность проводить CRUD операции над сущностями. Для получения
        // объекта EntityManager используется EntityManagerFactory

        // 'find(Class<T>clazz, Object key) : T' -  производит поиск сущности типа Т по значению первичного ключа key
        // и возвращает ее в случае нахождения такой сущности, иначе null.

        //Все операции изменения (создания, редактирование, удаление) должны в стандарте JPA должны выполняться в рамках транзакции

        //Транзакция - группа запросов отправляемых выполняться на сервер БД по принципу все или ничего.


        EntityManagerFactory factory = Persistence.createEntityManagerFactory("default");

        EntityManager manager = factory.createEntityManager();

        //Class<Category> categoryClass = Category.class;

        Category category = manager.find(Category.class, 2);
        //System.out.println(category.getProducts().get(1).getName());
        System.out.printf("%s:%n", category.getName());
        for (Product product : category.getProducts()) {
            System.out.printf("-%s (%d)%n", product.getName(), product.getPrice());
        }

        Product product = manager.find(Product.class, 2);
        System.out.println(product.getCategory().getName() + " - " + product.getName() +" цена " + product.getPrice());
try{
        manager.getTransaction().begin();//новую, отправить, откат

    /*Category category1 = new Category();
    category1.setName("Мебель");
    manager.persist(category1);//создать*/

    /*Category category = manager.find(Category.class, 3);
    category.setName("Телевизор");//изменение*/

   /* Category category = manager.find(Category.class, 5);
    manager.remove(category);//удаление*/

        manager.getTransaction().commit();
    }catch (Exception e) {
    manager.getTransaction().rollback();
    e.printStackTrace();
    }

        // select * from
        //в JPA запросы выполнять при помощи объектов Query и TypedQuery
        // Query - объект предназначенный для выполнения запросов на изменение данных в таблице . Использование объекта Query
        //не подразумевает наличие результата запроса то есть данный объект не подходит для выполнения select запросов

        //TypedQuery<T>  объект предн. для выполнения запросов подразумевающих наличие типизированного (select) где T,
        //тип результата для запросов. Объект TypedQuery подходит для выполнения select запросов и не пригоден
        // для update и delete

        //в JPA для написания запросов к БД применяется отдельный язык под названием JPQL который является абстракцией
        //от нативного SQL


        // JPQL -> ORM -> PostgreSQL Dialect -> PostgreSQL
        // JPQL -> ORM -> Oracle Database Dialect -> Oracle Database
        // JPQL -> ORM -> MySQL Dialect -> MySQL

        //Отличие JPQL от SQL является взаимадействие в запросе с сущностями вместо таблиц
        // SQL -> select c.* from categories c
        //JPQL -> select c from Category c

        //SQL -> select p.* from products where p.price > 90000
        //JPQL -> select p from Product p where p.price > 90000

        //SQL -> select p.* from products p join categories c on p.category_id = c.id where c.name = "smartphone"
        //JPQL -> select p from Product p where p.category.name = "smartphone"

        //SQL -> update products set price = price * 1.1 where category_id = 2
        // JPQL ->

        //categoryTypedQuery.getResultList() : List<T> - метод для получения результата типа Т из объекта TypedQuery

        //categoryTypedQuery.getSingleResult() : T - метод для получения рез типа Т из объекта TypedQuery.
        //Применяется если в рез находится только один объект если в рез не будет объекта то сформируется исключение

        // Ко


        System.out.println("---");
        TypedQuery<Category> categoryTypedQuery = manager.createQuery(
                "select c from Category c order by c.id", Category.class);

        List<Category> categories = categoryTypedQuery.getResultList();
        for (Category category1 : categories
             ) {
            System.out.println(category1.getName());
        }
        System.out.println("----");
        for (Product p: category.getProducts()
             ) {
            System.out.printf("- %s (%d)%n", p.getName(), p.getPrice());
        }

        System.out.println("---");

        // select c.name from Category c -> корректно так как выбирается только c.name
        // select c.id, c.name from Category c - некорректно так как выбирается несклько полей

        // Часто в запрос необходимо подставить информацию из переменных либо просто подставить ту или иную информацию.
        //Для решений задачи подстановки данных в запрос можно использовать JPQL параметры

        //JPQL параметры - обозначения в запросе вместо которых должны быть подставлены данные
        //Виды JPQL параметров:
        //1 Порядковые каждый параметр имеет свой пор номер начиная с 1 и должен начинаться с ? знака(?1, ?2)
        //2 Именные - нач с : (:param1, :param2)

        int minPrice = 100;
        int maxPrice = 200;
        TypedQuery<Product> productTypedQuery = manager.createQuery(
                "select p from Product p where p.price between ?1 and ?2", Product.class
        );
        productTypedQuery.setParameter(1, minPrice);
        productTypedQuery.setParameter(2, maxPrice);

        List<Product> products = productTypedQuery.getResultList();
        for (Product pr: products
             ) {
            System.out.printf("%s: %s (%d)%n", pr.getCategory().getName(), pr.getName(), pr.getPrice());
        }

        TypedQuery<Integer> maxPriceQuery = manager.createQuery(
                "select max (p.price) from Product p", Integer.class
        );
        int maxP = maxPriceQuery.getSingleResult();
        System.out.println(maxP);

        //обновление(изменение)
try {
    /*manager.getTransaction().begin();
    Query query = manager.createQuery("update Product p set p.price = p.price +1000");
    query.executeUpdate();//для delete тоже*/

    //удаление
    /*long categoryID = 2;
    Query query1 = manager.createQuery(
            "delete from Product p where p.category.id = ?1"
    );
    query1.setParameter(1,categoryID);
    query1.executeUpdate();
    manager.getTransaction().commit();*/

}catch (Exception e){
    manager.getTransaction().rollback();//отменяет все изменение
    e.printStackTrace();
}



    }
}





























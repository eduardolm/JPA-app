import model.State;
import model.Student;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class Program {

    public static void main(String[] args) {

        /** Create EntityManager */
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpa");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        /** Create instances to be persisted to DB */
        State stateToCreate = new State("Rio de Janeiro", "RJ");
        Student studentToCreate = new Student("Daniel", 29, stateToCreate);

        /** Initiate transactions to persist data on DB */
        entityManager.getTransaction().begin();

        entityManager.persist(stateToCreate);
        entityManager.persist(new State("São Paulo", "SP"));
        entityManager.persist(studentToCreate);

        entityManager.persist(new Student("João", 20, stateToCreate));
        entityManager.persist(new Student("Pedro", 30, stateToCreate));

        entityManager.getTransaction().commit();

        /** Using EntityManager find() method */
        String name = "Daniel";

        Student studentEntityManager = entityManager.find(Student.class, 1);
        System.out.println("Consulta studentEntityManager: " + studentEntityManager);

        /** Using SQL to return student */
        String sql = "SELECT * FROM Student WHERE name = :name";
        Student studentSql = (Student) entityManager
                .createNativeQuery(sql, Student.class)
                .setParameter("name", name)
                .getSingleResult();

        /** Lists all students */
        String sqlList = "SELECT * FROM Student;";
        List<Student> studentSqlList = entityManager
                .createNativeQuery(sqlList, Student.class)
                .getResultList();

        System.out.println("Consulta alunoSql: " + studentSql);
        System.out.println("\nConsulta studentSqlList: ");
        studentSqlList.forEach(System.out::println);

        /** JPQL */
        String jpql = "select a from Student a where a.name = :name";
        Student studentJpql = entityManager
                .createQuery(jpql, Student.class)
                .setParameter("name", name)
                .getSingleResult();

        // List
        String jpqlList = "select a from Student a";
        List<Student> studentJpqlList = entityManager
                .createQuery(jpqlList, Student.class)
                .getResultList();

        System.out.println("Consulta studentJpql: " + studentJpql);
        System.out.println();
        System.out.println("\nConsulta studentJpqlList: ");
        studentJpqlList.forEach(System.out::println);

        /** Using JPA Criteria API + JPA Metamodel API */

        // List
        CriteriaQuery<Student> criteriaQueryList = entityManager.getCriteriaBuilder().createQuery(Student.class);
        Root<Student> studentRootList = criteriaQueryList.from(Student.class);
        List<Student> studentAPICriteriaList = entityManager.createQuery(criteriaQueryList).getResultList();

        System.out.println("Consulta studentAPICriteriaList: ");
        studentAPICriteriaList.forEach(System.out::println);

        /** Close EntityManager and Factory */
        entityManager.close();
        entityManagerFactory.close();
    }
}

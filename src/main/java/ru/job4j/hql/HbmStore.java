package ru.job4j.hql;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.persistence.Query;

public class HbmStore {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

//            Candidate first = Candidate.of("First", 3, 45000);
//            Candidate second = Candidate.of("Second", 8, 95000);
//            Candidate third = Candidate.of("Third", 1, 25000);
//
//            session.save(first);
//            session.save(second);
//            session.save(third);

            Query query = session.createQuery("from Candidate ");
            for (Object st : ((org.hibernate.query.Query<?>) query).list()) {
                System.out.println(st);
            }

            query = session.createQuery("from Candidate s where s.id = 2");
            System.out.println(((org.hibernate.query.Query<?>) query).uniqueResult());

            query = session.createQuery("from Candidate s where s.name = 'Third'");
            System.out.println(((org.hibernate.query.Query<?>) query).uniqueResult());

            query = session.createQuery("from Candidate s where s.id = 2");
            System.out.println(((org.hibernate.query.Query<?>) query).uniqueResult());

            session.createQuery("update Candidate c set c.experience = :newExp, c.salary = :newSalary where c.id = :fId")
                    .setParameter("newExp", 5)
                    .setParameter("newSalary", 55000)
                    .setParameter("fId", 1)
                    .executeUpdate();

            session.createQuery("delete from Candidate where id = :fId")
                    .setParameter("fId", 3)
                    .executeUpdate();

            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}

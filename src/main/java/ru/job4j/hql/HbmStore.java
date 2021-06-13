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

            Job jobOne = Job.of("Middle");
            Job jobTwo = Job.of("Junior");
            Job jobThree = Job.of("Senor");

            JobDatabase jobDatabaseOne = JobDatabase.of("One");
            jobDatabaseOne.addJob(jobOne);
            jobDatabaseOne.addJob(jobTwo);

            JobDatabase jobDatabaseTwo = JobDatabase.of("Two");
            jobDatabaseTwo.addJob(jobThree);

            Candidate first = Candidate.of("First", 3, 45000);

            Candidate second = Candidate.of("Second", 8, 95000);

            first.setJobDatabase(jobDatabaseOne);
            second.setJobDatabase(jobDatabaseTwo);

            session.save(jobTwo);
            session.save(jobOne);
            session.save(jobThree);
            session.save(jobDatabaseOne);
            session.save(jobDatabaseTwo);
            session.save(first);
            session.save(second);

            Candidate candidate = session.createQuery(
                    "select distinct st from Candidate st "
                            + "join fetch st.jobDatabase a "
                            + "join fetch a.jobs b "
                            + "where st.id = :sId", Candidate.class
            ).setParameter("sId", 1).uniqueResult();
            System.out.println(candidate);
            System.out.println();

//            Candidate first = Candidate.of("First", 3, 45000);
//            Candidate second = Candidate.of("Second", 8, 95000);
//            Candidate third = Candidate.of("Third", 1, 25000);
//
//            session.save(first);
//            session.save(second);
//            session.save(third);
//
//            Query query = session.createQuery("from Candidate ");
//            for (Object st : ((org.hibernate.query.Query<?>) query).list()) {
//                System.out.println(st);
//            }
//
//            query = session.createQuery("from Candidate s where s.id = 2");
//            System.out.println(((org.hibernate.query.Query<?>) query).uniqueResult());
//
//            query = session.createQuery("from Candidate s where s.name = 'Third'");
//            System.out.println(((org.hibernate.query.Query<?>) query).uniqueResult());
//
//            query = session.createQuery("from Candidate s where s.id = 2");
//            System.out.println(((org.hibernate.query.Query<?>) query).uniqueResult());
//
//            session.createQuery("update Candidate c set c.experience = :newExp, c.salary = :newSalary where c.id = :fId")
//                    .setParameter("newExp", 5)
//                    .setParameter("newSalary", 55000)
//                    .setParameter("fId", 1)
//                    .executeUpdate();
//
//            session.createQuery("delete from Candidate where id = :fId")
//                    .setParameter("fId", 3)
//                    .executeUpdate();
            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}

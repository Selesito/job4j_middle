package ru.job4j.hql;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "job_database")
public class JobDatabase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Job> jobs = new ArrayList<>();

    public static JobDatabase of(String name) {
        JobDatabase jobDatabase = new JobDatabase();
        jobDatabase.username = name;
        return jobDatabase;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void addJob(Job job) {
        this.jobs.add(job);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JobDatabase that = (JobDatabase) o;
        return id == that.id && Objects.equals(username, that.username) && Objects.equals(jobs, that.jobs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, jobs);
    }

    @Override
    public String toString() {
        return String.format("JobDatabase: id=%s, username=%s, jobs=%s",
                id, username, jobs);
    }
}

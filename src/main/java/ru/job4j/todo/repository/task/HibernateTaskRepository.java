package ru.job4j.todo.repository.task;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateTaskRepository implements TaskRepository {
    private final SessionFactory sf;

    @Override
    public Task save(Task task) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.save(task);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return task;

    }

    @Override
    public boolean deleteById(int id) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            Query query = session.createQuery("DELETE Task WHERE id = :fId")
                    .setParameter("fId", id);
            int affectedRows = query.executeUpdate();
            session.getTransaction().commit();
            return affectedRows > 0;
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return false;
    }

    @Override
    public boolean update(Task task) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            Query query = session.createQuery("""
                            UPDATE Task
                            SET title = :fTitle, description = :fDescription, created = :fCreated, done = :fDone 
                            WHERE id = :fId
                            """)
                    .setParameter("fTitle", task.getTitle())
                    .setParameter("fDescription", task.getDescription())
                    .setParameter("fCreated", task.getCreated())
                    .setParameter("fDone", task.isDone())
                    .setParameter("fId", task.getId());
            int affectedRows = query.executeUpdate();
            session.getTransaction().commit();
            return affectedRows > 0;
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return false;
    }

    @Override
    public boolean updateDone(int id) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            Query query = session.createQuery("""
                            UPDATE Task
                            SET done = :fDone
                            WHERE id = :fId
                            """)
                    .setParameter("fDone", true)
                    .setParameter("fId", id);
            int affectedRows = query.executeUpdate();
            session.getTransaction().commit();
            return affectedRows > 0;
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return false;
    }

    @Override
    public Optional<Task> findById(int id) {
        Optional<Task> taskOpt = Optional.empty();
        Session session = sf.openSession();
        try {
            Query<Task> query = session.createQuery("from Task WHERE id = :fId");
            query.setParameter("fId", id);
            taskOpt = query.uniqueResultOptional();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return taskOpt;
    }

    @Override
    public List<Task> findAll() {
        List<Task> tasks = new ArrayList<>();
        Session session = sf.openSession();
        try {
            Query query = session.createQuery("from Task");
            tasks = query.list();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return tasks;
    }

    @Override
    public Collection<Task> findNew() {
        List<Task> tasks = new ArrayList<>();
        Session session = sf.openSession();
        try {
            Query<Task> query = session.createQuery("from Task");
            for (Task t : query.list()) {
                if (!t.isDone()) {
                    tasks.add(t);
                }
            }
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return tasks;
    }

    @Override
    public Collection<Task> findDone() {
        List<Task> tasks = new ArrayList<>();
        Session session = sf.openSession();
        try {
            Query<Task> query = session.createQuery("from Task");
            for (Task t : query.list()) {
                if (t.isDone()) {
                    tasks.add(t);
                }
            }
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return tasks;
    }
}

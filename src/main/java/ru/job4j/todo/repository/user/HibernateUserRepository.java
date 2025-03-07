package ru.job4j.todo.repository.user;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateUserRepository implements UserRepository {

    private final SessionFactory sf;

    @Override
    public Optional<User> save(User user) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) {
        User user;
        Session session = sf.openSession();
        Query query = session.createQuery("from User WHERE login = :fLogin AND password = :f= :fPassword")
        .setParameter("fLogin", login)
        .setParameter("fPassword", password);
        user = (User) query.uniqueResult();
        session.close();
        return Optional.ofNullable(user);
    }
}

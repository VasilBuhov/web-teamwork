package com.company.web.forum.repositories;

import com.company.web.forum.exceptions.EntityNotFoundException;
import com.company.web.forum.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public UserRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<User> getAllUsers() {

        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<User> cq = cb.createQuery(User.class);
            Root<User> root = cq.from(User.class);
            cq.select(root);
            return session.createQuery(cq).list();
        } catch (Exception e) {
            // Handle exceptions  // I should think a bit more here
            return new ArrayList<>();
        }
    }


    @Override
    public User getUserById(int id) throws EntityNotFoundException {
        try (Session session = sessionFactory.openSession()) {
            User user = session.get(User.class, id);
            if (user == null) {
                throw new EntityNotFoundException("User not found",id);
            }
            return user;
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            // Handle exceptions
            return null;
        }
    }

    public User getUserByEmail(String email) throws EntityNotFoundException {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<User> cq = cb.createQuery(User.class);
            Root<User> root = cq.from(User.class);
            cq.select(root).where(cb.equal(root.get("email"), email));
            User user = session.createQuery(cq).uniqueResultOptional().orElse(null);
            if (user == null) {
                throw new EntityNotFoundException("User", "email", email);
            }
            return user;
        } catch (Exception e) {
            // Handle exceptions
            return null;
        }
    }

    @Override
    public User getUserByUsername(String username) throws EntityNotFoundException {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<User> cq = cb.createQuery(User.class);
            Root<User> root = cq.from(User.class);
            cq.select(root).where(cb.equal(root.get("username"), username));
            User user = session.createQuery(cq).uniqueResultOptional().orElse(null);
            if (user == null) {
                throw new EntityNotFoundException("User", "username", username);
            }
            return user;
        } catch (Exception e) {
            // Handle exceptions
            return null;
        }
    }

    @Override
    public void createUser(User user) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            // Handle exceptions
        }
    }
//    @Override
//    public void createUser(User user) {
//        try (Session session = sessionFactory.openSession()) {
//
//            session.save(user);
//
//        }
//    }

    @Override
    public void updateUser(User user) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            // Handle exceptions
        }
    }

    @Override
    public void deleteUser(int id) throws EntityNotFoundException {
        try (Session session = sessionFactory.openSession()) {
            User user = session.get(User.class, id);
            if (user == null) {
                throw new EntityNotFoundException("User not found",id);
            }
            session.beginTransaction();
            session.delete(user);
            session.getTransaction().commit();
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            // Handle exceptions
        }
    }
}

package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;



    @Override
    public Optional<User> findByUsername(String username) {
        TypedQuery<User> query = entityManager.createQuery(
                "SELECT u FROM User u WHERE u.username = :username", User.class);
        query.setParameter("username", username);
        return query.getResultStream().findFirst();
    }

    @Override
    public List<User> findAll() {
        entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
        return  entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    @Override
    public Optional<User> findById(Long id) {
        return  Optional.ofNullable(entityManager.find(User.class, id));
    }

    @Override
    public void save(User user) {
        entityManager.persist(user);
    }

    @Override // что бы праильно удалит надо найти сеачала по id
    public void deleteById(Long id) {
        User user  =  entityManager.find(User.class, id); // находим
        if ( user != null) {
            entityManager.remove(user); // удаляем
        }
    }

    @Override
    public void update(User user) {
        entityManager.merge(user);
    }
}

package ru.javawebinar.topjava.repository.jpa;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class JpaMealRepositoryImpl implements MealRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        System.out.println( "save "+meal.getUser() );
        System.out.println( "save "+userId );
        if (meal.isNew() ) {
            meal.setUser( em.getReference(User.class, userId) );
            System.out.println( "persist "+meal.getUser() );
            System.out.println( "persist "+userId );
            em.persist(meal);
            return meal;
        } else {
            if ( get(meal.getId(), userId) == null ) {
                return null;
            }
            meal.setUser( em.getReference(User.class, userId) );
            System.out.println( "merge "+meal.getUser() );
            System.out.println( "merge "+userId );
            return em.merge(meal);
        }
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        Meal m = get( id, userId );
        if (m != null) {
            em.remove(m);
            return true;
        }
        return false;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = em.find(Meal.class, id);
        return (meal != null) && (meal.getUser() != null) && (meal.getUser().getId() == userId) ? meal : null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return em.createNamedQuery( Meal.ALL_SORTED, Meal.class )
                .setParameter( "user_id", userId )
                .getResultList();
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return null;
    }
}
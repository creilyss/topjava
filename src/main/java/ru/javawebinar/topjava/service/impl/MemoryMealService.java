package ru.javawebinar.topjava.service.impl;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MemoryMealService implements MealService {

    private static volatile int generatedID = 1;

    private static synchronized int generateNextID() {
        return generatedID++;
    }

    private static final List<Meal> meals = Collections.synchronizedList(new ArrayList<>());
    static {
        meals.add(new Meal(generateNextID(), LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        meals.add(new Meal(generateNextID(), LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        meals.add(new Meal(generateNextID(), LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        meals.add(new Meal(generateNextID(), LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        meals.add(new Meal(generateNextID(), LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        meals.add(new Meal(generateNextID(), LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
    }

    @Override
    public List<Meal> list() {
        return meals;
    }


    @Override
    public Meal get(int id) {
        synchronized (meals) {
            for (Meal meal : meals) {
                if (meal.getId() == id) {
                    return meal;
                }
            }
        }
        return null;
    }



    @Override
    public void add(Meal meal) {
        if ( meal.getId() == 0 ) {
            meal.setId( generateNextID() );
        }
         meals.add( meal );
    }


    @Override
    public void update(Meal meal) {
        synchronized (meals) {
            for (int i = 0; i < meals.size(); i++) {
                if (meals.get(i).getId() == meal.getId()) {
                    meals.set(i, meal);
                    break;
                }
            }
        }
    }

    @Override
    public void delete(int id) {
        synchronized (meals) {
            for (int i = 0; i < meals.size(); i++) {
                if (meals.get(i).getId() == id) {
                    meals.remove(i);
                    break;
                }
            }
        }
    }


}

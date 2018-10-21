package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;


@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

  static {
    SLF4JBridgeHandler.install();
  }

  @Autowired
  private MealService service;

  @Test
  public void get() {
    Meal meal = service.get(USER_MEAL_ID_2, USER_ID);
    assertMatch(meal, USER_MEAL_2);
  }

  @Test(expected = NotFoundException.class)
  public void getNotFound() {
    service.get(USER_MEAL_ID_2, ADMIN_ID);
  }

  @Test
  public void delete() {
    service.delete(USER_MEAL_ID_3, USER_ID);
    assertMatch(service.getAll(USER_ID), USER_MEAL_2, USER_MEAL_1);
  }

  @Test(expected = NotFoundException.class)
  public void deleteNotFound() {
    service.get(USER_MEAL_ID_2, ADMIN_ID);
  }

  @Test
  public void getBetweenDates() {
    List<Meal> meals = service.getBetweenDates(LocalDateTime.of(2018, Month.OCTOBER, 20, 0, 0).toLocalDate(), LocalDateTime.of(2018, Month.OCTOBER, 20, 0, 0).toLocalDate(), USER_ID);
    assertMatch(meals, USER_MEAL_2, USER_MEAL_1);
  }

  @Test
  public void getBetweenDateTimes() {
    List<Meal> meals = service.getBetweenDateTimes(LocalDateTime.of(2018, Month.OCTOBER, 20, 14, 0), LocalDateTime.of(2018, Month.OCTOBER, 21, 9, 0), USER_ID);
    assertMatch(meals, USER_MEAL_3, USER_MEAL_2);
  }

  @Test
  public void getAll() {
    List<Meal> meals = service.getAll(USER_ID);
    assertMatch(meals, USER_MEAL_3, USER_MEAL_2, USER_MEAL_1);
  }

  @Test
  public void update() {
    Meal updated = service.get(USER_MEAL_ID_1, USER_ID);
    updated.setCalories(220);
    updated.setDescription("яблочко");
    service.update(updated, USER_ID);
    assertMatch(service.get(USER_MEAL_ID_1, USER_ID), updated);
  }


  @Test(expected = NotFoundException.class)
  public void updateNotFound() {
    Meal updated = service.get(USER_MEAL_ID_1, USER_ID);
    updated.setCalories(220);
    updated.setDescription("яблочко");
    service.update(updated, ADMIN_ID);
  }

  @Test
  public void create() {
    Meal meal = new Meal(LocalDateTime.now(), "тортик", 1800);
    service.create(meal, ADMIN_ID);
    assertMatch(service.getAll(ADMIN_ID), meal, ADMIN_MEAL_1);
  }

  @Test(expected = DataAccessException.class)
  public void createDuplicateDatetimeMeal() {
    Meal meal = new Meal(ADMIN_MEAL_1.getDateTime(), "тортик", 1800);
    service.create(meal, ADMIN_ID);
  }


}
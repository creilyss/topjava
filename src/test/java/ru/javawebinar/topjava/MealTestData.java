package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {

  public static final int USER_MEAL_ID_1 = START_SEQ + 2;
  public static final int USER_MEAL_ID_2 = USER_MEAL_ID_1 + 1;
  public static final int USER_MEAL_ID_3 = USER_MEAL_ID_2 + 1;
  public static final int ADMIN_MEAL_ID_1 = USER_MEAL_ID_3 + 1;

  public static final Meal USER_MEAL_1 = new Meal(USER_MEAL_ID_1, LocalDateTime.of(2018, Month.OCTOBER, 20, 9, 0), "завтрак", 500);
  public static final Meal USER_MEAL_2 = new Meal(USER_MEAL_ID_2, LocalDateTime.of(2018, Month.OCTOBER, 20, 14, 0), "обед", 1000);
  public static final Meal USER_MEAL_3 = new Meal(USER_MEAL_ID_3, LocalDateTime.of(2018, Month.OCTOBER, 21, 9, 0), "перекус", 150);
  public static final Meal ADMIN_MEAL_1 = new Meal(ADMIN_MEAL_ID_1, LocalDateTime.of(2018, Month.OCTOBER, 21, 10, 0), "вкусный завтрак", 500);

  public static void assertMatch(Meal actual, Meal expected) {
    assertThat(actual).isEqualTo(expected);
  }

  public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
    assertThat(actual).isEqualTo(expected);
  }

  public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
    assertMatch(actual, Arrays.asList(expected));
  }

}

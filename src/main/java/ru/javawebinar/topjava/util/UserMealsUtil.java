package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> dayTotalCaloriesMap = new HashMap<>();
        for (UserMeal userMeal : mealList) {
            dayTotalCaloriesMap.merge(userMeal.getDateTime().toLocalDate(), userMeal.getCalories(), Integer::sum);
        }

        List<UserMealWithExceed> resultList = new ArrayList<>();
        for (UserMeal userMeal : mealList) {
            if (userMeal.getDateTime().toLocalTime().isAfter(startTime) && userMeal.getDateTime().toLocalTime().isBefore(endTime)) {
                resultList.add(new UserMealWithExceed(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(),
                        (dayTotalCaloriesMap.getOrDefault(userMeal.getDateTime().toLocalDate(), 0) > caloriesPerDay)));
            }
        }

        return resultList;
    }


    public static List<UserMealWithExceed> getFilteredWithExceededByStream(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> dayTotalCaloriesMap = mealList.stream().collect(Collectors.toMap(n -> n.getDateTime().toLocalDate(), UserMeal::getCalories, Integer::sum));

        return mealList.stream()
                .filter(v -> v.getDateTime().toLocalTime().isAfter(startTime) && v.getDateTime().toLocalTime().isBefore(endTime))
                .map(v -> new UserMealWithExceed(v.getDateTime(), v.getDescription(), v.getCalories(),
                        (dayTotalCaloriesMap.getOrDefault(v.getDateTime().toLocalDate(), 0) > caloriesPerDay))).collect(Collectors.toList());
    }

}

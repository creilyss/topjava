package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class JdbcMealRepositoryImpl implements MealRepository {

  private static final BeanPropertyRowMapper<Meal> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Meal.class);

  private final JdbcTemplate jdbcTemplate;

  private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  private final SimpleJdbcInsert insertMeal;


  @Autowired
  public JdbcMealRepositoryImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
    this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    this.insertMeal = new SimpleJdbcInsert(jdbcTemplate)
            .withTableName("meals")
            .usingGeneratedKeyColumns("id");
  }

  @Override
  public Meal save(Meal meal, int userId) {
    MapSqlParameterSource map = new MapSqlParameterSource()
            .addValue("id", meal.getId())
            .addValue("userid", userId)
            .addValue("description", meal.getDescription())
            .addValue("datetime", meal.getDateTime())
            .addValue("calories", meal.getCalories());

    if (meal.isNew()) {
      Number newKey = insertMeal.executeAndReturnKey(map);
      meal.setId(newKey.intValue());
    } else if (namedParameterJdbcTemplate.update("update meals set description=:description, calories=:calories, datetime=:datetime where id=:id and userid=:userid", map) == 0) {
      return null;
    }
    return meal;
  }

  @Override
  public boolean delete(int id, int userId) {
    return (jdbcTemplate.update("delete from meals where id=? and userid=?", id, userId) != 0);
  }

  @Override
  public Meal get(int id, int userId) {
    List<Meal> meals = jdbcTemplate.query("select * from meals where id=? and userid=?", ROW_MAPPER, id, userId);
    return DataAccessUtils.singleResult(meals);
  }

  @Override
  public List<Meal> getAll(int userId) {
    return jdbcTemplate.query("select * from meals where userid=? order by datetime desc", ROW_MAPPER, userId);
  }

  @Override
  public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
    return jdbcTemplate.query("select * from meals where userid=? and datetime between ? and ? order by datetime desc", ROW_MAPPER, userId, startDate, endDate);
  }
}

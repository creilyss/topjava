package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.impl.MemoryMealService;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {

    private static final Logger log = getLogger(MealServlet.class);
    private MealService mealService;

    @Override
    public void init() throws ServletException {
        super.init();
        mealService = new MemoryMealService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.debug("post request");

        if ( request.getParameter("id") != null ) {
            int mealId = Integer.parseInt(request.getParameter("id"));
            if ( mealId > 0 ) {
                mealService.update( new Meal( mealId, LocalDateTime.parse(request.getParameter("mealTime")), request.getParameter("description"), Integer.parseInt(request.getParameter("calories"))) );
            } else {
                mealService.add( new Meal( LocalDateTime.parse(request.getParameter("mealTime")), request.getParameter("description"), Integer.parseInt(request.getParameter("calories"))) );
            }
        }
        response.sendRedirect("meals" );
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("get request "+getServletContext().getContextPath());

        if ( request.getParameter("action") != null ) {
            log.debug("action detected "+request.getParameter("action") );
            if ( request.getParameter("action").equals("new") ) {
                Meal meal = new Meal(LocalDateTime.now(),"", 0);
                request.setAttribute( "meal",  meal );
                request.getRequestDispatcher("meals_edit_form.jsp").forward(request, response);
            } else if (( request.getParameter("action").equals("edit") ) && ( request.getParameter("id") != null ) ) {
                request.setAttribute( "meal", mealService.get(Integer.parseInt(request.getParameter("id")) ) );
                request.getRequestDispatcher("meals_edit_form.jsp").forward(request, response);
            } else if (( request.getParameter("action").equals("del") ) && ( request.getParameter("id") != null ) ) {
                mealService.delete( Integer.parseInt( request.getParameter("id") ) );
                response.sendRedirect("meals" );
            } else {
                showList(request, response);
            }
        } else {
            showList(request, response);
        }

    }

    private void showList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Meal> meals = mealService.list();
        List<MealWithExceed> mealsList = MealsUtil.getFilteredWithExceeded( meals, LocalTime.MIN, LocalTime.MAX, 2000);
        request.setAttribute("mealsList", mealsList );

        request.getRequestDispatcher("meals.jsp").forward(request, response);

    }
}

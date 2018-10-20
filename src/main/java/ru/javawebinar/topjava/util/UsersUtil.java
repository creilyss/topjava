package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.Arrays;
import java.util.List;

public class UsersUtil {
    public static final List<User> USERS = Arrays.asList(
            new User(null, "test", "test@test.com", "test", Role.ROLE_USER, Role.ROLE_ADMIN ),
            new User(null, "ztest", "ztest@test.com", "test", Role.ROLE_USER ),
            new User(null, "atest", "atest@test.com", "test", Role.ROLE_ADMIN )
    );
}

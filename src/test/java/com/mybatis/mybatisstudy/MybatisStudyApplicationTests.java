package com.mybatis.mybatisstudy;

import com.mybatis.mybatisstudy.entity.User;
import com.mybatis.mybatisstudy.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class MybatisStudyApplicationTests {
    // 最大循环次数
    private static final int MAXCOUNT = 100000;

    @Autowired
    UserService userService;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    DataSource dataSource;


    static List<User> userList = new ArrayList<>();

    @BeforeAll
    public static void init() {
        for (int i = 0; i < MAXCOUNT; i++) {
            User user = new User();
            user.setName("test:" + i);
            user.setPassWord("123456");
            userList.add(user);
        }
    }

    @Test
    public void test() throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            System.out.println("autoCommit = " + conn.getAutoCommit());
        }
    }

    @Test
    public void test01() {
        List<User> users = userService.listAllUsers();
        System.out.println(users.size());
    }

    @Test
    public void test02() {
        System.out.println(jdbcTemplate.getDataSource().getClass());
    }

    @Test
    public void insertBatch() {
        userService.insertBatch(userList);
    }

    @Test
    public void insertForEach() {
        long start = System.currentTimeMillis();
        userService.insertForEach(userList);
        long end = System.currentTimeMillis();
        System.out.println("insertForEach时间：" + (end - start));
    }

    @Test
    public void listUserByCursor() {
        userService.listUserByCursor();
    }
}

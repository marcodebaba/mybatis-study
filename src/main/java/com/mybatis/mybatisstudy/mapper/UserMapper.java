package com.mybatis.mybatisstudy.mapper;

import com.mybatis.mybatisstudy.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.mapping.ResultSetType;

import java.util.List;

/**
 * @author：marco.pan
 * @ClassName：UserMapper
 * @Description：
 * @date: 2022年05月23日 21:24
 */
@Mapper
public interface UserMapper {

    List<User> listAllUsers();

    User getUserById(Long id);

    boolean insertForEach(List<User> list);

    boolean insert(User user);

    @Options(fetchSize = 1000, resultSetType = ResultSetType.FORWARD_ONLY)
    Cursor<User> listUserByCursor();
}

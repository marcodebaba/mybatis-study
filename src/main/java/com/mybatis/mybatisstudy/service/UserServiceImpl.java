package com.mybatis.mybatisstudy.service;

import com.mybatis.mybatisstudy.entity.User;
import com.mybatis.mybatisstudy.mapper.UserMapper;
import com.mybatis.mybatisstudy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * @author：marco.pan
 * @ClassName：IUserServiceImpl
 * @Description：
 * @date: 2025年08月16日 18:17
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final SqlSessionFactory sqlSessionFactory;

    @Override
    public void insertBatch(List<User> userList) {
        long startTime = System.currentTimeMillis();
        try (SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false)) {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            userList.forEach(mapper::insert);
            sqlSession.commit();
        }
        long endTime = System.currentTimeMillis();
        log.info(">>> insertBatch总耗时：{}", endTime - startTime);

        //userList.parallelStream().forEach(userMapper::insert);
    }

    @Override
    public void insertForEach(List<User> userList) {
        userMapper.insertForEach(userList);
    }

    @Override
    public User getUserById(Long id) {
        Objects.requireNonNull(id, "id must not be null");
        return userRepository.getUserById(id);
    }

    @Override
    public List<User> listAllUsers() {
        return userMapper.listAllUsers();
    }

    @Override
    @Transactional(readOnly = true)
    public void listUserByCursor() {
        userRepository.scanAllUsersByCursor(1000, (dataList, batchNo, totalSoFar) -> {
            log.info(">>> 批次 {} 完成：本批 {} 条，累计 {} 条", batchNo, dataList.size(), totalSoFar);
        });

        log.info(">>> 所有批次处理完成");
    }
}

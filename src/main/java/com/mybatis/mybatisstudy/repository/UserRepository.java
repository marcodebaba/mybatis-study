package com.mybatis.mybatisstudy.repository;

import com.mybatis.mybatisstudy.entity.User;

public interface UserRepository {
    /**
     * 根据id查询用户
     * @param id 用户id
     * @return 用户对象，不存在返回null
     */
    User getUserById(Long id);

    /**
     * 以批处理方式扫描所有用户，不泄漏游标。
     * @param batchSize 批大小，建议1000~5000
     * @param handler   每批处理回调（在同一事务内串行执行）
     */
    void scanAllUsersByCursor(int batchSize, BatchHandler<User> handler);
}

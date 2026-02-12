package com.mybatis.mybatisstudy.repository;

import com.mybatis.mybatisstudy.entity.User;
import com.mybatis.mybatisstudy.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cursor.Cursor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author：marco.pan
 * @ClassName：UserRepository
 * @Description：
 * @date: 2025年11月12日 12:07
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepositoryImpl implements UserRepository {
    private final UserMapper userMapper;

    @Override
    public User getUserById(Long id) {
        return userMapper.getUserById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public void scanAllUsersByCursor(int batchSize, BatchHandler<User> handler) {
        if (batchSize <= 0) {
            throw new IllegalArgumentException("batchSize must be > 0");
        }

        Objects.requireNonNull(handler, "handler must not be null");

        long total = 0L;
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("流式查询");

        List<User> dataList = new ArrayList<>(batchSize);
        try (Cursor<User> cursor = userMapper.listUserByCursor()) {
            int inBatch = 0;    // 当前批内条数
            int batchNo = 1;    // 批次编号（从1开始）
            for (User u : cursor) {
                dataList.add(u);
                total++;
                inBatch++;

                // 每batchSize条打印一次分隔线
                if (inBatch == batchSize) {
                    handler.handle(dataList, batchNo, total);
                    batchNo++;
                    inBatch = 0;   // 重置批内计数
                    dataList.clear();
                }
            }
            // 收尾：处理最后不满batchSize的残批
            if (inBatch > 0) {
                handler.handle(dataList, batchNo, total);
            }
        } catch (IOException e) {
            log.error("Cursor scan failed after processing {} records", total, e);
            throw new RuntimeException("Export users failed", e);
        } finally {
            stopWatch.stop();
            log.info(">>> cursor 用时(ms): {}, total= {}", stopWatch.getLastTaskTimeMillis(), total);
        }
    }
}
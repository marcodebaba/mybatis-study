package com.mybatis.mybatisstudy.repository;

import java.util.List;

@FunctionalInterface
public interface BatchHandler<T> {
    void handle(List<T> dataList, int batchNo, long totalSoFar);
}


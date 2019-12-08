package com.exce.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.math.BigInteger;
import java.util.List;

@NoRepositoryBean
public interface OpenCodeBaseRepository<T> extends PagingAndSortingRepository<T, BigInteger> {

    T findById(BigInteger id);

    List<T> findByExpectIn(List<String> except);

    List<T> findFirst5ByOrderByExpectDesc();

    T findFirstByOrderByExpectDesc();
}

package com.exce.service;

import com.exce.dto.OpenCodeVideo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface OpenCodeService<T> {

    Optional<T> findOne(BigInteger id);
    Optional<T> findLastOpened();
    Page<T> findAll(Pageable pageable);
    List<T> findFirst5ByOrderByExpectDesc();

    OpenCodeVideo getOpenCodeVideo(Boolean renew);
}

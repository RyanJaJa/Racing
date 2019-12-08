package com.exce.service.impl;

import com.exce.dto.OpenCodeVideo;
import com.exce.model.OpenCodeCqssc;
import com.exce.repository.OpenCodeCqsscRepository;
import com.exce.service.OpenCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service("OpenCodeCqsscService")
@Scope("prototype")
@Transactional
public class OpenCodeCqsscServiceImpl implements OpenCodeService<OpenCodeCqssc> {

    @Autowired
    OpenCodeCqsscRepository openCodeCqsscRepository;

    @Override
    public Optional<OpenCodeCqssc> findOne(BigInteger id) {
        return Optional.ofNullable(openCodeCqsscRepository.findOne(id));
    }

    @Override
    public Optional<OpenCodeCqssc> findLastOpened() {
        return Optional.of(openCodeCqsscRepository.findFirstByOrderByExpectDesc());
    }

    @Override
    public Page<OpenCodeCqssc> findAll(Pageable pageable) {
        return openCodeCqsscRepository.findAll(pageable);
    }

    @Override
    public List<OpenCodeCqssc> findFirst5ByOrderByExpectDesc() {
        return openCodeCqsscRepository.findFirst5ByOrderByExpectDesc();
    }

    @Override
    public OpenCodeVideo getOpenCodeVideo(Boolean renew) {
        return null;
    }
}

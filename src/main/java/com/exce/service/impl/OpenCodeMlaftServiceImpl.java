package com.exce.service.impl;

import com.exce.dto.OpenCodeVideo;
import com.exce.model.OpenCodeMlaft;
import com.exce.repository.OpenCodeMlaftRepository;
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

@Service("OpenCodeMlaftService")
@Scope("prototype")
@Transactional
public class OpenCodeMlaftServiceImpl implements OpenCodeService<OpenCodeMlaft> {

    @Autowired
    OpenCodeMlaftRepository openCodeMlaftRepository;

    @Override
    public Optional<OpenCodeMlaft> findOne(BigInteger id) {
        return Optional.ofNullable(openCodeMlaftRepository.findOne(id));
    }

    @Override
    public Optional<OpenCodeMlaft> findLastOpened() {
        return Optional.of(openCodeMlaftRepository.findFirstByOrderByExpectDesc());
    }

    @Override
    public Page<OpenCodeMlaft> findAll(Pageable pageable) {
        return openCodeMlaftRepository.findAll(pageable);
    }

    @Override
    public List<OpenCodeMlaft> findFirst5ByOrderByExpectDesc() {
        return openCodeMlaftRepository.findFirst5ByOrderByExpectDesc();
    }

    @Override
    public OpenCodeVideo getOpenCodeVideo(Boolean renew) {
        return null;
    }

}

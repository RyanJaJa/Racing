package com.exce.service.impl;

import com.exce.dto.OpenCodeVideo;
import com.exce.model.OpenCodeBjpk10;
import com.exce.repository.OpenCodeBjpk10Repository;
import com.exce.service.OpenCodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service("OpenCodeBjpk10Service")
@Scope("prototype")
@Transactional
public class OpenCodeBjpk10ServiceImpl implements OpenCodeService<OpenCodeBjpk10> {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static OpenCodeVideo openCodeBjpk10Video;
    @Autowired
    OpenCodeBjpk10Repository openCodeBjpk10Repository;

    @Override
    public Optional<OpenCodeBjpk10> findOne(BigInteger id) {
        return Optional.ofNullable(openCodeBjpk10Repository.findOne(id));
    }

    @Override
    public Optional<OpenCodeBjpk10> findLastOpened() {
        return Optional.of(openCodeBjpk10Repository.findFirstByOrderByExpectDesc());
    }

    @Override
    public Page<OpenCodeBjpk10> findAll(Pageable pageable) {
        return openCodeBjpk10Repository.findAll(pageable);
    }

    @Override
    public List<OpenCodeBjpk10> findFirst5ByOrderByExpectDesc() {
        return openCodeBjpk10Repository.findFirst5ByOrderByExpectDesc();
    }

    @Override
    public OpenCodeVideo getOpenCodeVideo(Boolean renew) {
        if (openCodeBjpk10Video == null || renew) {
            openCodeBjpk10Video = openCodeBjpk10Video == null ? new OpenCodeVideo() : openCodeBjpk10Video;
            Optional<OpenCodeBjpk10> openCodeBjpk10Optional = this.findLastOpened();
            if (openCodeBjpk10Optional.isPresent()) {
                OpenCodeBjpk10 openCodeBjpk10 = openCodeBjpk10Optional.get();
                //Optional<OpenCodeBjpk10> nextOptional = openCodeBjpk10Service.findOne(openCodeBjpk10.getId().add(BigInteger.ONE));
                //if (nextOptional.isPresent()) {
                openCodeBjpk10Video.current.qihao = Integer.valueOf(openCodeBjpk10.getExpect());
                openCodeBjpk10Video.current.awardTime = openCodeBjpk10.getOpenTime();
                openCodeBjpk10Video.current.awardNumbers = openCodeBjpk10.getOpenCode();

                openCodeBjpk10Video.next.qihao = openCodeBjpk10Video.current.qihao + 1;
                //}
            }
        }
        openCodeBjpk10Video.next.awardTime = (Calendar) openCodeBjpk10Video.current.awardTime.clone();
        openCodeBjpk10Video.next.awardTime.add(Calendar.MINUTE, 5);
        openCodeBjpk10Video.next.awardTimeInterval = Long.valueOf(openCodeBjpk10Video.next.awardTime.getTimeInMillis() - Calendar.getInstance().getTimeInMillis()).intValue();
        //TODO TimeZone issue, should remove after using real data.
        openCodeBjpk10Video.next.awardTimeInterval -= 8 * 60 * 60 * 1000;
        return openCodeBjpk10Video;
    }


}

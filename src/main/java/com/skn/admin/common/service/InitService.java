package com.skn.admin.common.service;

import com.skn.admin.base.etc.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class InitService {
    public Page createParamMapAndReturnPaging(
            Map<String, Object> reqMap,
            int page,
            int pageSize
    ) {

        String sdate = Optional.ofNullable(reqMap.get("sdate"))
                .orElse(DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDate.now()))
                .toString();
        String edate = Optional.ofNullable(reqMap.get("edate"))
                .orElse(DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDate.now()))
                .toString();

        reqMap.put("sdate", sdate);
        reqMap.put("edate", edate);

        Page paging = new Page(Page.PAGE_BLOCK_SIZE, page, pageSize);

        reqMap.put("limit", paging.getPageListSize());
        reqMap.put("offset", paging.getOffset());

        return paging;

    }
}

package com.skn.admin.site.service;

import com.skn.admin.site.mapper.AdminGroupPermissionMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminGroupPermissionService {
	private final AdminGroupPermissionMapper adminGroupPermissionMapper;
}

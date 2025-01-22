package com.skn.admin.sample.service;

import java.util.List;

import com.skn.admin.customer.dto.Inquiry;
import org.springframework.stereotype.Service;

import com.skn.admin.sample.dto.DecodeValue;
import com.skn.admin.sample.mapper.TestMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TestService {

	private final TestMapper testMapper;
	
	public List<DecodeValue> getList() {
		return testMapper.getList();
	}

	public List<DecodeValue> getList2() {
		return testMapper.getList2();
	}

	public Integer insert(DecodeValue decode) {
		return testMapper.insert(decode);
	}

	public Integer insertInquiry(Inquiry inquiry) {
		return testMapper.insertInquiry(inquiry);
	}

	public List<DecodeValue> selectInquiryOld() {
		return testMapper.selectInquiryOld();
	}
	
	
}

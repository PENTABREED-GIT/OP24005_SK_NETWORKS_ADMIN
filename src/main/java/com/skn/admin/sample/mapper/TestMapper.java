package com.skn.admin.sample.mapper;

import java.util.List;

import com.skn.admin.customer.dto.Inquiry;
import org.apache.ibatis.annotations.Mapper;

import com.skn.admin.sample.dto.DecodeValue;

@Mapper
public interface TestMapper {

	List<DecodeValue> getList();
	List<DecodeValue> getList2();

	Integer insert(DecodeValue decode);

	Integer insertInquiry(Inquiry inquiry);
	List<DecodeValue> selectInquiryOld();
	
}

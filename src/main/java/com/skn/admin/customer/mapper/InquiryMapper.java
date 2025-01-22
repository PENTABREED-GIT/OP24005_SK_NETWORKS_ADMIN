package com.skn.admin.customer.mapper;

import com.skn.admin.customer.dto.Inquiry;
import com.skn.admin.customer.dto.request.InquirySearchParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface InquiryMapper {
    List<Inquiry> selectInquiryList(InquirySearchParam param);
    int selectInquiryListCount(InquirySearchParam param);
    int insertAnswer(Inquiry data);
    int deleteInquiry(Inquiry data);
    int deleteOver1yearInquiry();
    int updateInquiry(Inquiry data);
    int updateInquiryStatus(Inquiry data);
    Inquiry selectInquiry(String uid);
    List<Inquiry> selectInquiryAnswer(String uid);
}

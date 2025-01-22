package com.skn.admin.esg.mapper;

import com.skn.admin.esg.dto.ReportsAndPolicies;
import com.skn.admin.esg.dto.request.ReportsAndPoliciesSearchParam;

import java.util.List;

public interface ReportsAndPoliciesMapper {
    List<ReportsAndPolicies> selectReportsAndPoliciesList(ReportsAndPoliciesSearchParam param);
    int selectReportsAndPoliciesListCount(ReportsAndPoliciesSearchParam param);
    int insertReportsAndPolicies(ReportsAndPolicies data);
    int deleteReportsAndPolicies(ReportsAndPolicies data);
    int updateReportsAndPolicies(ReportsAndPolicies data);
    ReportsAndPolicies selectReportsAndPolicies(String uid);
}

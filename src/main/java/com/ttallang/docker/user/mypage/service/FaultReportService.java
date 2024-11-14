package com.ttallang.docker.user.mypage.service;

import com.ttallang.docker.user.commomRepository.FaultReportRepository;
import com.ttallang.docker.user.commonModel.FaultReport;
import com.ttallang.docker.user.mypage.model.JoinFault;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class FaultReportService {

    private final FaultReportRepository faultReportRepository;

    public FaultReportService(FaultReportRepository faultReportRepository) {
        this.faultReportRepository = faultReportRepository;
    }

    // 신고 내역,카테고리 조회
    public List<JoinFault> findByFaultReport(int customerId) {
        return faultReportRepository.findByFaultId(customerId);
    }

    // 신고 내역 삭제
    public FaultReport deleteFaultReport(int reportId) {
        FaultReport faultReport = faultReportRepository.findById(reportId).orElse(null);
        faultReport.setFaultStatus("0");
        return faultReportRepository.save(faultReport);
    }
}

package com.ttallang.docker.user.rental.controller;

import com.ttallang.docker.user.commonModel.Bicycle;
import com.ttallang.docker.user.commonModel.Branch;
import com.ttallang.docker.user.commonModel.FaultCategory;
import com.ttallang.docker.user.commonModel.Rental;
import com.ttallang.docker.user.rental.model.UseRental;
import com.ttallang.docker.user.rental.service.BranchService;
import com.ttallang.docker.user.rental.service.RentalsService;
import com.ttallang.docker.user.rental.service.ReportService;
import com.ttallang.docker.user.security.config.auth.PrincipalDetails;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/map")
public class BranchRestController {

    private final BranchService branchService;
    private final RentalsService rentalsService;
    private final ReportService reportService;

    @Autowired
    public BranchRestController(BranchService branchService, RentalsService rentalsService,
        ReportService reportService) {
        this.branchService = branchService;
        this.rentalsService = rentalsService;
        this.reportService = reportService;
    }

    @GetMapping("/branches")
    public List<Branch> getBranches() {
        return branchService.getActiveBranches();
    }

    @GetMapping("/available/bikes/location")
    public Integer getAvailableBikesAtLocation(@RequestParam("latitude") double latitude,
        @RequestParam("longitude") double longitude) {
        return branchService.getAvailableBikesAtLocation(latitude, longitude);
    }

    @GetMapping("/available/bikes")
    public List<Bicycle> getAvailableBikes(@RequestParam("latitude") double latitude,
        @RequestParam("longitude") double longitude) {
        return branchService.getAvailableBikesList(latitude, longitude);
    }

    @GetMapping("/rental/status")
    public Map<String, Object> getRentalStatus() {
        PrincipalDetails pds = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int loginId = pds.getCustomerID();
        return rentalsService.getRentalStatusForCustomer(loginId);
    }

    @PostMapping("/rent/bicycle")
    public Map<String, Object> rentBicycle(@RequestParam int bicycleId, @RequestParam String rentalBranch) {
        PrincipalDetails pds = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int loginId = pds.getCustomerID();
        return rentalsService.rentBicycle(bicycleId, rentalBranch, loginId);
    }

    @PostMapping("/return/bicycle")
    public Rental returnBicycle(@RequestParam double returnLatitude, @RequestParam double returnLongitude,
        @RequestParam boolean isCustomLocation, @RequestParam String returnBranchName) {
        PrincipalDetails pds = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int loginId = pds.getCustomerID();
        return rentalsService.returnBicycle(returnLatitude, returnLongitude, isCustomLocation, returnBranchName, loginId);
    }

    // 현황판에서 반납 후 결제 처리
    @PostMapping("/return/bicycle/status")
    public Map<String, Object> returnBicycleWithPayment(
        @RequestParam double returnLatitude,
        @RequestParam double returnLongitude,
        @RequestParam boolean isCustomLocation,
        @RequestParam String returnBranchName) {

        PrincipalDetails pds = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int loginId = pds.getCustomerID();

        return rentalsService.completeReturn(returnLatitude, returnLongitude, isCustomLocation, returnBranchName, loginId);
    }

    @GetMapping("/check-rental-status")
    public Bicycle checkRentalStatus() {
        PrincipalDetails pds = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int loginId = pds.getCustomerID();
        return rentalsService.getCurrentRentalByCustomerId(loginId).orElse(null);
    }

    @GetMapping("/current-rentals")
    public UseRental getCurrentRentals() {
        PrincipalDetails pds = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int loginId = pds.getCustomerID();
        return rentalsService.getCurrentRentalsByCustomerId(loginId);
    }

    @GetMapping("/report-categories")
    public List<FaultCategory> getReportCategories() {
        return reportService.getAllFaultCategories();
    }

    @PostMapping("/report-issue")
    public Map<String, Object> reportIssue(@RequestParam int bicycleId, @RequestParam int categoryId,
        @RequestParam String reportDetails) {
        PrincipalDetails pds = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int loginId = pds.getCustomerID();
        return reportService.reportIssue(loginId, bicycleId, categoryId, reportDetails);
    }

    @PostMapping("/report-and-return")
    public Map<String, Object> reportAndReturn(
        @RequestParam int bicycleId,
        @RequestParam int categoryId,
        @RequestParam String reportDetails,
        @RequestParam String returnBranchName,
        @RequestParam double returnLatitude,
        @RequestParam double returnLongitude) {

        System.out.println("Controller - Latitude: " + returnLatitude);
        System.out.println("Controller - Longitude: " + returnLongitude);

        PrincipalDetails pds = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int loginId = pds.getCustomerID();

        return reportService.reportAndReturn(loginId, bicycleId, categoryId, reportDetails, returnBranchName, returnLatitude, returnLongitude);
    }

    // 근처 대여소 이름 가져오는 API 추가
    @GetMapping("/nearby-branch")
    public String getNearbyBranch(@RequestParam double latitude, @RequestParam double longitude) {
        String branchName = branchService.getNearbyBranchName(latitude, longitude);
        return branchName != null ? branchName : "기타"; // "기타"로 기본값 처리
    }
}

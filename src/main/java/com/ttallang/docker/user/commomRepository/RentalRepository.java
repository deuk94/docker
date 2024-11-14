package com.ttallang.docker.user.commomRepository;

import com.ttallang.docker.user.commonModel.Rental;
import com.ttallang.docker.user.mypage.model.JoinBicycle;
import com.ttallang.docker.user.rental.model.UseRental;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RentalRepository extends JpaRepository<Rental, Integer> {

    // 이용 내역 조회
    @Query("SELECT new com.ttallang.docker.user.mypage.model.JoinBicycle(b.bicycleName, " +
        "r.rentalBranch, r.rentalStartDate, r.returnBranch, r.rentalEndDate) " +
        "FROM Rental r JOIN Bicycle b ON b.bicycleId = r.bicycleId " +
        "WHERE r.customerId = :customerId " +
        "ORDER BY r.rentalStartDate DESC")
    List<JoinBicycle> getByRentalId(@Param("customerId") int customerId);

    List<Rental> findByCustomerIdAndRentalEndDateIsNull(int customerId);

    @Query("SELECT new com.ttallang.docker.user.rental.model.UseRental(b.bicycleId, b.bicycleName, r.rentalBranch, r.rentalStartDate) " +
        "FROM Rental r JOIN Bicycle b ON b.bicycleId = r.bicycleId " +
        "WHERE r.customerId = :customerId AND r.rentalEndDate IS NULL AND b.rentalStatus = '0' ")
    UseRental findCustomerIdAndRentalStatus(@Param("customerId") int customerId);

    // 새로운 메서드: 대여 중인 자전거 조회 (rentalEndDate가 NULL일 경우)
    @Query("SELECT r FROM Rental r WHERE r.customerId = :customerId AND r.rentalEndDate IS NULL")
    Optional<Rental> findActiveRental(@Param("customerId") int customerId);

}

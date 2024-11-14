package com.ttallang.docker.user.mypage.service;

import com.ttallang.docker.user.commomRepository.RentalRepository;
import com.ttallang.docker.user.mypage.model.JoinBicycle;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class RentalService {

    private final RentalRepository rentalRepository;

    public RentalService(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    // 이용 내역 조회
    public List<JoinBicycle> getByRental(int customerId) {
        return rentalRepository.getByRentalId(customerId);
    }
}

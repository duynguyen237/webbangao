package com.company.webbangao.repository;

import com.company.webbangao.entity.ThanhToan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ThanhToanRepository extends JpaRepository<ThanhToan, Long> {

    Optional<ThanhToan> findByHoaDon_HoaDonID(Long hoaDonID);

    Optional<ThanhToan> findByMaGiaoDich(String maGiaoDich);
}

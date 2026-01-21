package com.company.webbangao.repository;

import com.company.webbangao.entity.HoaDon;
import com.company.webbangao.entity.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface HoaDonRepository extends JpaRepository<HoaDon, Long> {
    // Lấy danh sách hóa đơn và sắp xếp cái mới nhất lên đầu
    List<HoaDon> findByKhachHangOrderByNgayDatDesc(KhachHang khachHang);
}
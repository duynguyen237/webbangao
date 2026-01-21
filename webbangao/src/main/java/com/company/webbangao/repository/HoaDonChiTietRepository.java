package com.company.webbangao.repository;

import com.company.webbangao.entity.HoaDonChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HoaDonChiTietRepository extends JpaRepository<HoaDonChiTiet, Long> {

    // Chi tiết theo hóa đơn
    List<HoaDonChiTiet> findByHoaDon_HoaDonID(Long hoaDonID);

    // Chi tiết theo sản phẩm
    List<HoaDonChiTiet> findBySanPham_SanPhamID(Long sanPhamID);
}

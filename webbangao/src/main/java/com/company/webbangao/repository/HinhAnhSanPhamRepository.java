package com.company.webbangao.repository;

import com.company.webbangao.entity.HinhAnhSanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HinhAnhSanPhamRepository extends JpaRepository<HinhAnhSanPham, Long> {

    // Câu lệnh này tương đương: "SELECT * FROM hinh_anh_san_pham WHERE san_phamid = ?"
    // Chú ý: 'sanPham' là tên biến trong Entity HinhAnhSanPham
    // 'SanPhamID' là tên biến ID trong Entity SanPham
    List<HinhAnhSanPham> findBySanPham_SanPhamID(Long sanPhamID);
}
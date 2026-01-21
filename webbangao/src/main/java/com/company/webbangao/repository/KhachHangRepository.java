package com.company.webbangao.repository;

import com.company.webbangao.entity.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KhachHangRepository extends JpaRepository<KhachHang, Long> {

    // Spring sẽ tự hiểu: Tìm KhachHang có TaiKhoan sở hữu Username tương ứng
    KhachHang findByTaiKhoan_Username(String username);
}
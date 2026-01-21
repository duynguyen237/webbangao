package com.company.webbangao.repository;

import com.company.webbangao.entity.DanhMuc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DanhMucRepository extends JpaRepository<DanhMuc, Long> {
    // Để trống cũng được, Spring tự lo hết
}
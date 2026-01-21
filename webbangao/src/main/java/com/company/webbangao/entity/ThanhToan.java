package com.company.webbangao.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "ThanhToan")
public class ThanhToan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long thanhToanID;

    private String phuongThuc;
    private String trangThai;
    private String maGiaoDich;
    private LocalDateTime ngayThanhToan;

    @OneToOne
    @JoinColumn(name = "HoaDonID")
    private HoaDon hoaDon;
}

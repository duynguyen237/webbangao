package com.company.webbangao.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "HoaDonChiTiet")
public class HoaDonChiTiet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hoaDonChiTietID;

    private Integer soLuong;
    private Double donGia;
    private Double thanhTien;

    @ManyToOne
    @JoinColumn(name = "HoaDonID")
    private HoaDon hoaDon;

    @ManyToOne
    @JoinColumn(name = "SanPhamID")
    private SanPham sanPham;
}

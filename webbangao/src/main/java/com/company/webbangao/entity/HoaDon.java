package com.company.webbangao.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "hoa_don")
public class HoaDon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hoaDonID;

    private LocalDate ngayDat;
    private Double tongTien;
    private String trangThai;

    @ManyToOne
    @JoinColumn(name = "khach_hang_id")
    private KhachHang khachHang;

    // --- BẮT BUỘC PHẢI CÓ CÁC PHƯƠNG THỨC NÀY ---

    public Long getHoaDonID() { return hoaDonID; }
    public void setHoaDonID(Long hoaDonID) { this.hoaDonID = hoaDonID; }

    public LocalDate getNgayDat() { return ngayDat; }
    public void setNgayDat(LocalDate ngayDat) { this.ngayDat = ngayDat; }

    public Double getTongTien() { return tongTien; }
    public void setTongTien(Double tongTien) { this.tongTien = tongTien; }

    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }

    // Phương thức gây lỗi nếu thiếu:
    public KhachHang getKhachHang() { return khachHang; }
    public void setKhachHang(KhachHang khachHang) { this.khachHang = khachHang; }
}
package com.company.webbangao.entity;

import jakarta.persistence.*;
import java.util.List; // BẮT BUỘC: Phải có dòng này mới dùng được List

@Entity
@Table(name = "san_pham")
public class SanPham {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "san_phamid")
    private Long sanPhamID;

    @Column(name = "ten_san_pham")
    private String tenSanPham;

    private Double gia;

    @Column(name = "so_luong_ton")
    private Integer soLuongTon;

    @Column(name = "image_url") // Đây là ảnh đại diện chính (hiển thị ở trang danh sách)
    private String imageUrl;


    @Column(name = "mo_ta", columnDefinition = "nvarchar(max)")
    private String moTa;

    @Column(name = "imagegb")
    private String imagegb;

    @ManyToOne
    @JoinColumn(name = "danh_mucid") // Liên kết với bảng DanhMuc
    private DanhMuc danhMuc;
    // --- QUAN TRỌNG: Liên kết 1-Nhiều với bảng hình ảnh ---
    // mappedBy = "sanPham": Trùng tên với biến private SanPham sanPham; bên file HinhAnhSanPham
    @OneToMany(mappedBy = "sanPham", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<HinhAnhSanPham> listHinhAnh;

    // ================== GETTER & SETTER ==================

    public Long getSanPhamID() {
        return sanPhamID;
    }

    public void setSanPhamID(Long sanPhamID) {
        this.sanPhamID = sanPhamID;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public Double getGia() {
        return gia;
    }

    public void setGia(Double gia) {
        this.gia = gia;
    }

    public Integer getSoLuongTon() {
        return soLuongTon;
    }

    public void setSoLuongTon(Integer soLuongTon) {
        this.soLuongTon = soLuongTon;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getImagegb() {
        return imagegb;
    }

    public void setImagegb(String imagegb) {
        this.imagegb = imagegb;
    }

    public List<HinhAnhSanPham> getListHinhAnh() {
        return listHinhAnh;
    }

    public void setListHinhAnh(List<HinhAnhSanPham> listHinhAnh) {
        this.listHinhAnh = listHinhAnh;
    }
    public DanhMuc getDanhMuc() {
        return danhMuc;
    }

    public void setDanhMuc(DanhMuc danhMuc) {
        this.danhMuc = danhMuc;
    }
}
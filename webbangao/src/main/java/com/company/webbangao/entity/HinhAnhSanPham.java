package com.company.webbangao.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "hinh_anh_san_pham") // Tên bảng trong SQL
public class HinhAnhSanPham {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ten_anh") // Tên file ảnh hoặc URL
    private String tenAnh;

    // QUAN TRỌNG: Liên kết ngược lại với bảng SanPham
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "san_phamid") // Phải khớp với tên cột khóa chính bên bảng SanPham của bạn
    private SanPham sanPham;

    // --- Getter và Setter ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTenAnh() {
        return tenAnh;
    }

    public void setTenAnh(String tenAnh) {
        this.tenAnh = tenAnh;
    }

    public SanPham getSanPham() {
        return sanPham;
    }

    public void setSanPham(SanPham sanPham) {
        this.sanPham = sanPham;
    }
}
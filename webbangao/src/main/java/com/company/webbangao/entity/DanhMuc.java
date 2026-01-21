package com.company.webbangao.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "danh_muc") // Tên bảng trong Database sẽ là 'danh_muc'
public class DanhMuc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "danh_mucid")
    private Long id;

    @Column(name = "ten_danh_muc")
    private String tenDanhMuc; // Ví dụ: Gạo, Nước mắm, Đồ khô...

    // Một danh mục có nhiều sản phẩm
    @OneToMany(mappedBy = "danhMuc", cascade = CascadeType.ALL)
    private List<SanPham> listSanPham;

    // --- GETTER & SETTER ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTenDanhMuc() { return tenDanhMuc; }
    public void setTenDanhMuc(String tenDanhMuc) { this.tenDanhMuc = tenDanhMuc; }

    public List<SanPham> getListSanPham() { return listSanPham; }
    public void setListSanPham(List<SanPham> listSanPham) { this.listSanPham = listSanPham; }
}
package com.company.webbangao.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "khach_hang")
public class KhachHang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "khach_hangid")
    private Long khachHangID;

    @Column(name = "ten_khach_hang")
    private String tenKhachHang;

    @OneToOne
    @JoinColumn(name = "tai_khoan_id")
    private TaiKhoan taiKhoan;
}
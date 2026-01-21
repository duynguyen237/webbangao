package com.company.webbangao.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tai_khoan")
public class TaiKhoan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tai_khoanid")
    private Long taiKhoanID;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    // ROLE_ADMIN | ROLE_CUSTOMER
    @Column(nullable = false)
    private String role;
}

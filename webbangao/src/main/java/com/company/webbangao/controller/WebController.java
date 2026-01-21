package com.company.webbangao.controller;

import com.company.webbangao.entity.KhachHang;
import com.company.webbangao.entity.TaiKhoan;
import com.company.webbangao.repository.KhachHangRepository;
import com.company.webbangao.repository.TaiKhoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/web")
public class WebController {

    @Autowired
    private TaiKhoanRepository taiKhoanRepository;

    @Autowired
    private KhachHangRepository khachHangRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping({"/", "/index"})
    public String home() {
        return "index";
    }

    // LOGIN
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // REGISTER
    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String handleRegister(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String tenKhachHang
    ) {

        if (taiKhoanRepository.findByUsername(username).isPresent()) {
            return "redirect:/web/register?error";
        }

        TaiKhoan tk = new TaiKhoan();
        tk.setUsername(username);
        tk.setPassword(passwordEncoder.encode(password)); // BCrypt
        tk.setRole("ROLE_CUSTOMER");
        taiKhoanRepository.save(tk);

        KhachHang kh = new KhachHang();
        kh.setTenKhachHang(tenKhachHang);
        kh.setTaiKhoan(tk);
        khachHangRepository.save(kh);

        return "redirect:/web/login?success";
    }
}

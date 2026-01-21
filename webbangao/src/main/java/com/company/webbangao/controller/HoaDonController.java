package com.company.webbangao.controller;

import com.company.webbangao.entity.HoaDon;
import com.company.webbangao.entity.KhachHang;
import com.company.webbangao.repository.HoaDonRepository;
import com.company.webbangao.repository.KhachHangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class HoaDonController {

    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Autowired
    private KhachHangRepository khachHangRepository;

    @GetMapping("/lich-su-hoa-don")
    public String xemLichSu(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/web/login";
        }

        // 1. Lấy thông tin khách hàng đang login
        String username = principal.getName();
        KhachHang kh = khachHangRepository.findByTaiKhoan_Username(username);

        if (kh != null) {
            // 2. Lấy danh sách hóa đơn của khách hàng đó
            List<HoaDon> dsHoaDon = hoaDonRepository.findByKhachHangOrderByNgayDatDesc(kh);

            model.addAttribute("khachHang", kh); // Để hiển thị tên trên Header
            model.addAttribute("dsHoaDon", dsHoaDon);
        }

        return "hoadon"; // Trả về file lichsuhoadon.html
    }
}
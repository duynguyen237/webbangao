package com.company.webbangao.controller;

import com.company.webbangao.entity.KhachHang;
import com.company.webbangao.entity.SanPham;
import com.company.webbangao.repository.KhachHangRepository;
import com.company.webbangao.repository.SanPhamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    @Autowired
    private KhachHangRepository khachHangRepository;

    @Autowired
    private SanPhamRepository sanPhamRepository;

    @GetMapping("/")
    public String home(Model model, Principal principal) {
        // 1. Lấy thông tin khách hàng (giữ nguyên logic cũ)
        if (principal != null) {
            KhachHang kh = khachHangRepository.findByTaiKhoan_Username(principal.getName());
            model.addAttribute("khachHang", kh);
        }

        // 2. Lấy 3 sản phẩm đầu tiên để làm Slideshow
        List<SanPham> tatCaSP = sanPhamRepository.findAll();
        List<SanPham> spSlideshow = tatCaSP.stream().limit(3).toList();
        model.addAttribute("listSlide", spSlideshow);

        // 3. Lấy 4 sản phẩm tiếp theo để giới thiệu mục bên dưới (nếu muốn)
        List<SanPham> spGioiThieu = tatCaSP.stream().skip(0).limit(4).toList();
        model.addAttribute("listSP", spGioiThieu);

        return "index";
    }
}
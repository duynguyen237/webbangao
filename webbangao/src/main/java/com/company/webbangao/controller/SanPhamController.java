package com.company.webbangao.controller;

import com.company.webbangao.entity.KhachHang;
import com.company.webbangao.entity.SanPham;
import com.company.webbangao.entity.HinhAnhSanPham;
import com.company.webbangao.repository.KhachHangRepository;
import com.company.webbangao.repository.SanPhamRepository;
import com.company.webbangao.repository.HinhAnhSanPhamRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.security.core.Authentication;

import java.security.Principal;
import java.util.List;

@Controller
public class SanPhamController {

    private final SanPhamRepository sanPhamRepository;
    private final KhachHangRepository khachHangRepository;
    private final HinhAnhSanPhamRepository hinhAnhRepo;

    public SanPhamController(SanPhamRepository sanPhamRepository,
                             KhachHangRepository khachHangRepository,
                             HinhAnhSanPhamRepository hinhAnhRepo) {
        this.sanPhamRepository = sanPhamRepository;
        this.khachHangRepository = khachHangRepository;
        this.hinhAnhRepo = hinhAnhRepo;
    }

    // === 1. TRANG DANH SÁCH SẢN PHẨM ===
    @GetMapping("/sanpham")
    public String danhSachSanPham(Model model, Principal principal) {
        xyLyThongTinDangNhap(model, principal); // Gọi hàm xử lý chung bên dưới cho gọn

        model.addAttribute("sanPhams", sanPhamRepository.findAll());
        return "sanpham";
    }

    // === 2. TRANG CHI TIẾT SẢN PHẨM ===
    // (Lúc nãy bạn bị nhầm chỗ này thành danhSachSanPham)
    @GetMapping("/san-pham/{id}")
    public String xemChiTiet(@PathVariable("id") Long id, Model model, Principal principal) {
        // 1. Tìm sản phẩm
        SanPham sp = sanPhamRepository.findById(id).orElse(null);

        if (sp != null) {
            // 2. Xử lý thông tin đăng nhập (Admin hay Khách)
            xyLyThongTinDangNhap(model, principal);

            // 3. Lấy danh sách ảnh phụ (Gallery)
            List<HinhAnhSanPham> listAnh = hinhAnhRepo.findBySanPham_SanPhamID(id);

            // 4. Đẩy dữ liệu ra HTML
            model.addAttribute("product", sp);
            model.addAttribute("listAnh", listAnh);

            return "chitietsanpham";
        }
        return "redirect:/sanpham";
    }

    // === HÀM HỖ TRỢ: XỬ LÝ KIỂM TRA ADMIN/KHÁCH HÀNG ===
    // (Viết tách ra để dùng chung cho cả 2 trang trên, đỡ phải copy paste)
    private void xyLyThongTinDangNhap(Model model, Principal principal) {
        if (principal != null) {
            Authentication auth = (Authentication) principal;
            boolean isAdmin = auth.getAuthorities().stream()
                    .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));

            model.addAttribute("isAdmin", isAdmin);

            // Nếu không phải Admin thì mới đi tìm thông tin Khách Hàng để hiện tên
            if (!isAdmin) {
                KhachHang kh = khachHangRepository.findByTaiKhoan_Username(principal.getName());
                model.addAttribute("khachHang", kh);
            }
        }
    }
}
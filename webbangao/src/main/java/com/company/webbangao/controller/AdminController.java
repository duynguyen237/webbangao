package com.company.webbangao.controller;

import com.company.webbangao.entity.DanhMuc;
import com.company.webbangao.entity.SanPham;
import com.company.webbangao.repository.DanhMucRepository;
import com.company.webbangao.repository.SanPhamRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final SanPhamRepository sanPhamRepo;
    private final DanhMucRepository danhMucRepo;

    // Định nghĩa đường dẫn lưu ảnh (Trỏ thẳng vào thư mục source code để dễ quản lý)
    // System.getProperty("user.dir") sẽ lấy đường dẫn gốc của dự án
    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/src/main/resources/static/images";

    public AdminController(SanPhamRepository sanPhamRepo, DanhMucRepository danhMucRepo) {
        this.sanPhamRepo = sanPhamRepo;
        this.danhMucRepo = danhMucRepo;
    }

    // === 1. QUẢN LÝ DANH MỤC ===
    @GetMapping("/them-danh-muc")
    public String formThemDanhMuc(Model model) {
        model.addAttribute("danhMuc", new DanhMuc());
        return "admin/them-danh-muc";
    }

    @PostMapping("/them-danh-muc")
    public String luuDanhMuc(@ModelAttribute("danhMuc") DanhMuc danhMuc) {
        danhMucRepo.save(danhMuc);
        return "redirect:/admin/them-san-pham";
    }

    // === 2. QUẢN LÝ SẢN PHẨM (CÓ UPLOAD ẢNH) ===
    @GetMapping("/them-san-pham")
    public String formThemSanPham(Model model) {
        model.addAttribute("sanPham", new SanPham());
        model.addAttribute("listDanhMuc", danhMucRepo.findAll());
        return "admin/them-san-pham";
    }

    @PostMapping("/them-san-pham")
    public String luuSanPham(@ModelAttribute("sanPham") SanPham sanPham,
                             @RequestParam("imageFile") MultipartFile imageFile) {
        try {
            // 1. Kiểm tra nếu người dùng có chọn ảnh
            if (!imageFile.isEmpty()) {
                String originalFilename = imageFile.getOriginalFilename();

                // Tạo đường dẫn file đích
                Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, originalFilename);

                // Kiểm tra thư mục images có tồn tại chưa, chưa thì tạo mới
                if (!Files.exists(fileNameAndPath.getParent())) {
                    Files.createDirectories(fileNameAndPath.getParent());
                }

                // Lưu file ảnh từ bộ nhớ ra ổ cứng
                Files.copy(imageFile.getInputStream(), fileNameAndPath, StandardCopyOption.REPLACE_EXISTING);

                // Lưu tên file vào database (Ví dụ: "gao-st25.jpg")
                sanPham.setImageUrl(originalFilename);
            }

            // 2. Lưu thông tin sản phẩm
            sanPhamRepo.save(sanPham);

        } catch (IOException e) {
            e.printStackTrace(); // In lỗi ra console nếu có sự cố lưu file
        }

        return "redirect:/sanpham"; // Quay về trang danh sách
    }
}
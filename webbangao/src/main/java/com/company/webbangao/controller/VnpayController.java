package com.company.webbangao.controller;

import com.company.webbangao.entity.HoaDon;
import com.company.webbangao.entity.KhachHang;
import com.company.webbangao.repository.HoaDonRepository;
import com.company.webbangao.repository.KhachHangRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Controller
public class VnpayController {

    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Autowired
    private KhachHangRepository khachHangRepository;

    @Value("${vnpay.tmnCode}")
    private String tmnCode;

    @Value("${vnpay.hashSecret}")
    private String hashSecret;

    @Value("${vnpay.url}")
    private String vnpUrl;

    @Value("${vnpay.returnUrl}")
    private String returnUrl;

    @GetMapping("/vnpay/pay")
    public String pay(@RequestParam("amount") long amount,
                      @RequestParam("khachHangId") Long khachHangId,
                      HttpServletRequest request) throws Exception {

        long amountVND = amount * 100;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", "2.1.0");
        vnp_Params.put("vnp_Command", "pay");
        vnp_Params.put("vnp_TmnCode", tmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amountVND));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_TxnRef", String.valueOf(System.currentTimeMillis()));

        // QUAN TRỌNG: Gửi ID khách hàng kèm vào OrderInfo
        vnp_Params.put("vnp_OrderInfo", "KH_ID:" + khachHangId);

        vnp_Params.put("vnp_OrderType", "other");
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", returnUrl);
        vnp_Params.put("vnp_IpAddr", request.getRemoteAddr());

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        vnp_Params.put("vnp_CreateDate", formatter.format(cld.getTime()));

        cld.add(Calendar.MINUTE, 15);
        vnp_Params.put("vnp_ExpireDate", formatter.format(cld.getTime()));

        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        for (String fieldName : fieldNames) {
            String fieldValue = vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                hashData.append(fieldName).append('=').append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString())).append('=').append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                hashData.append('&');
                query.append('&');
            }
        }

        String queryUrl = query.substring(0, query.length() - 1);
        String vnp_SecureHash = hmacSHA512(hashSecret, hashData.substring(0, hashData.length() - 1));
        return "redirect:" + vnpUrl + "?" + queryUrl + "&vnp_SecureHash=" + vnp_SecureHash;
    }

    @GetMapping("/vnpay-return")
    public String vnpayReturn(HttpServletRequest request, Model model) {
        String responseCode = request.getParameter("vnp_ResponseCode");
        String orderInfo = request.getParameter("vnp_OrderInfo");
        String vnpAmount = request.getParameter("vnp_Amount");

        // Tính toán số tiền để hiển thị
        Double amount = (vnpAmount != null) ? Double.parseDouble(vnpAmount) / 100 : 0.0;
        model.addAttribute("amount", amount);

        if ("00".equals(responseCode)) {
            Long khId = null;
            if (orderInfo != null && orderInfo.contains("KH_ID:")) {
                try {
                    khId = Long.parseLong(orderInfo.split(":")[1]);
                } catch (Exception e) {
                    System.out.println("Lỗi bóc tách ID: " + e.getMessage());
                }
            }

            HoaDon hd = new HoaDon();
            hd.setNgayDat(LocalDate.now());
            hd.setTongTien(amount);
            hd.setTrangThai("Đã thanh toán qua VNPay");

            if (khId != null) {
                khachHangRepository.findById(khId).ifPresent(kh -> {
                    hd.setKhachHang(kh);
                    // QUAN TRỌNG: Truyền đối tượng khách hàng ra giao diện
                    model.addAttribute("khachHang", kh);
                });
            }

            hoaDonRepository.save(hd);
            model.addAttribute("message", "Thanh toán thành công! 🎉");
        } else {
            model.addAttribute("message", "Thanh toán thất bại! ❌");
        }
        return "vnpay-result";
    }

    private String hmacSHA512(String key, String data) throws Exception {
        Mac hmac = Mac.getInstance("HmacSHA512");
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
        hmac.init(secretKey);
        byte[] hashBytes = hmac.doFinal(data.getBytes(StandardCharsets.UTF_8));
        StringBuilder hash = new StringBuilder();
        for (byte b : hashBytes) {
            hash.append(String.format("%02x", b));
        }
        return hash.toString();
    }
}
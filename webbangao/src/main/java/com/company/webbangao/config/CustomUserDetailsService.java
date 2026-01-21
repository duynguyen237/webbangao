package com.company.webbangao.config;

import com.company.webbangao.entity.TaiKhoan;
import com.company.webbangao.repository.TaiKhoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private TaiKhoanRepository taiKhoanRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        TaiKhoan tk = taiKhoanRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Không tìm thấy tài khoản!!"));

        return new User(
                tk.getUsername(),
                tk.getPassword(),
                List.of(new SimpleGrantedAuthority(tk.getRole()))
        );
    }
}

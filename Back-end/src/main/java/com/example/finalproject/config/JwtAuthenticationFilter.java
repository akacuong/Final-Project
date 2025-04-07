package com.example.finalproject.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    public JwtAuthenticationFilter(JwtUtils jwtUtils) { // ✅ Constructor injection
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String token = request.getHeader("Authorization");

        // Nếu không có token hoặc token không hợp lệ (không phải Bearer)
        if (token == null || !token.startsWith("Bearer ")) {
            chain.doFilter(request, response);  // Tiếp tục chuỗi lọc nếu không có token
            return;
        }

        token = token.substring(7);  // Loại bỏ "Bearer " khỏi token

        if (jwtUtils.isValidToken(token)) {  // Kiểm tra tính hợp lệ của token
            String username = jwtUtils.extractUsername(token);  // Trích xuất tên người dùng từ token
            String role = jwtUtils.extractRole(token);  // Trích xuất vai trò từ token

            // Tạo danh sách quyền (Authorities)
            List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role));

            // Tạo đối tượng UserDetails với username và quyền
            UserDetails userDetails = new User(username, "", authorities);

            // Lưu thông tin người dùng vào SecurityContext để có thể xác thực
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(userDetails, null, authorities)
            );
        }

        chain.doFilter(request, response);  // Tiếp tục chuỗi lọc
    }
}
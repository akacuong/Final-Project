package com.example.finalproject.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class JwtUtils {

    private static final String SECRET_KEY = "Cuong"; // 🔥 Đổi sang key bí mật thực tế
    private static final long EXPIRATION_TIME = 30 * 60 * 1000; // 30 phút

    // Tạo token JWT
    public String generateToken(String username, String role) {
        return JWT.create()
                .withSubject(username)
                .withClaim("role", role)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC256(SECRET_KEY));
    }

    // Kiểm tra và giải mã token
    public DecodedJWT verifyToken(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET_KEY)).build();
        return verifier.verify(token);  // Xác minh token
    }

    // Trích xuất tên người dùng từ token
    public String extractUsername(String token) {
        return verifyToken(token).getSubject();
    }

    // Trích xuất vai trò từ token
    public String extractRole(String token) {
        return verifyToken(token).getClaim("role").asString();
    }

    // Kiểm tra tính hợp lệ của token (bao gồm hết hạn hay không)
    public boolean isValidToken(String token) {
        try {
            DecodedJWT decodedJWT = verifyToken(token);
            // Kiểm tra nếu token đã hết hạn
            return !decodedJWT.getExpiresAt().before(new Date());
        } catch (Exception e) {
            return false; // Nếu token không hợp lệ hoặc bị lỗi
        }
    }
}

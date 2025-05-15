package deu.movietalk.jwt;

import deu.movietalk.domain.Member;
import deu.movietalk.dto.CustomMemberDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JWTFilter extends OncePerRequestFilter {
    private final JWTUtil jwtUtil;

    public JWTFilter(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return path.equals("/login") || path.equals("/signup") || path.equals("/api/friend")
                || path.startsWith("/api/movie") || path.startsWith("/api/playlist/view") || path.startsWith(("/api/community/posts"))
                || path.equals("/api/reviews/view");
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = null;
        String authorization = request.getHeader("Authorization");

        if (authorization != null && authorization.startsWith("Bearer ")) {
            token = authorization.substring(7);
        } else {
            // 쿠키에서 JWT 찾기
            if (request.getCookies() != null) {
                for (Cookie cookie : request.getCookies()) {
                    if ("Authorization".equals(cookie.getName())) {
                        token = cookie.getValue();
                        break;
                    }
                }
            }
        }

        if (token == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"error\": \"로그인이 필요합니다.\"}");
            return;
        }

        try {
            if (jwtUtil.isExpired(token)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write("{\"error\": \"토큰이 만료되었습니다. 다시 로그인 해주세요.\"}");
                return;
            }

            String username = jwtUtil.getUsername(token);
            String role = jwtUtil.getRole(token);

            Member member = new Member();
            member.setMemberId(username);
            member.setPassword("temppassword");
            member.setRole(role);

            CustomMemberDetails customUserDetails = new CustomMemberDetails(member);
            Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authToken);

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"error\": \"유효하지 않은 토큰입니다. 다시 로그인 해주세요.\"}");
            return;
        }

        filterChain.doFilter(request, response);
    }

}
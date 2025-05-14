package deu.movietalk.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import deu.movietalk.dto.CustomMemberDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    public LoginFilter(AuthenticationManager authenticationManager,JWTUtil jwtUtil ) {
        this.jwtUtil=jwtUtil;
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl("/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            // JSON Body에서 username과 password를 추출
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, String> authenticationData = objectMapper.readValue(request.getInputStream(), Map.class);

            String username = authenticationData.get("username");
            String password = authenticationData.get("password");

            System.out.println("username = " + username);
            System.out.println("password = " + password);

            // username과 password로 UsernamePasswordAuthenticationToken 생성
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password, null);

            // authenticationManager를 통해 인증 요청을 처리
            return authenticationManager.authenticate(authToken);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read the login credentials", e);
        }
    }

    //로그인 성공시 실행하는 메소드 (여기서 JWT를 발급하면 됨)
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, JsonProcessingException {
        CustomMemberDetails customMemberDetails = (CustomMemberDetails) authentication.getPrincipal();

        String username = customMemberDetails.getUsername();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();

        String role = auth.getAuthority();

        String token = jwtUtil.createJwt(username, role, 60*60*1000L);

        // JSON 응답 생성
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("token", token);  // JWT 토큰을 JSON 응답 본문에 추가

        // 응답을 JSON 형식으로 반환
        response.setContentType("application/json");
        // ObjectMapper의 writeValueAsString을 사용해 JSON 문자열로 변환 후 응답 본문에 작성
        response.getWriter().write(new ObjectMapper().writeValueAsString(responseBody));
    }



    //로그인 실패시 실행하는 메소드
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        response.setStatus(401);
    }
}
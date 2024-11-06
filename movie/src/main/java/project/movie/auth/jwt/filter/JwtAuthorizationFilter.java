package project.movie.auth.jwt.filter;import io.jsonwebtoken.ExpiredJwtException;import jakarta.servlet.FilterChain;import jakarta.servlet.ServletException;import jakarta.servlet.http.Cookie;import jakarta.servlet.http.HttpServletRequest;import jakarta.servlet.http.HttpServletResponse;import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;import org.springframework.security.core.Authentication;import org.springframework.security.core.context.SecurityContextHolder;import org.springframework.web.filter.OncePerRequestFilter;import project.movie.auth.jwt.dto.CustomUserDetails;import project.movie.auth.jwt.util.JWTUtil;import project.movie.member.domain.Member;import project.movie.member.domain.MemberRole;import java.io.IOException;public class JwtAuthorizationFilter extends OncePerRequestFilter  {    private final JWTUtil jwtUtil;    public JwtAuthorizationFilter(JWTUtil jwtUtil) {        this.jwtUtil = jwtUtil;    }    @Override    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {        System.out.println("doFilterInternal 실행됨 ##############################################################################################################################");        //request에서 Authorization 헤더를 찾음        //String authorization= request.getHeader("Authorization"); [사용안함]        //cookie들을 불러온 뒤 Authorization Key에 담긴 쿠키를 찾음        String authorization = null;        Cookie[] cookies = request.getCookies();        for (Cookie cookie : cookies) {            System.out.println(cookie.getName());            if (cookie.getName().equals("Authorization")) {                authorization = cookie.getValue();            }        }        if (authorization == null) {            System.out.println("token null");            filterChain.doFilter(request, response);            return;        }        //토큰        String token = authorization;        try {            //토큰 소멸 시간 검증            if (jwtUtil.isExpired(token)) {                System.out.println("token expired (시간 만료)");                filterChain.doFilter(request, response);                //조건이 해당되면 메소드 종료 (필수)                return;            }        } catch (ExpiredJwtException e) {            System.out.println("token expired (시간 만료) 여기야???");            System.out.println(e.getMessage());            filterChain.doFilter(request, response);            return;        }        //토큰에서 username과 role 획득        String username = jwtUtil.getUsername(token);        String role = jwtUtil.getRole(token);        //Member 를 생성하여 값 set        Member member = Member.builder()                .memberId(username)                .role(MemberRole.valueOf(role))                .build();        CustomUserDetails customOAuth2User = new CustomUserDetails(member);        Authentication authToken = new UsernamePasswordAuthenticationToken(customOAuth2User, null, customOAuth2User.getAuthorities());        SecurityContextHolder.getContext().setAuthentication(authToken);        filterChain.doFilter(request, response);    }}
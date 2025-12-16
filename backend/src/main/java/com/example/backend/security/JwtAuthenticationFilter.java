package com.example.backend.security;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * JWTトークンを検証し、認証情報をSecurityContextにセットするフィルター
 * 全てのHTTPリクエストに対して1回だけ実行される
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider){
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /**
     * リクエストごとに実行される認証処理
     */
    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException{
        try{
            // 1. リクエストヘッダーからトークンを抽出
            String jwt = getJwtFromRequest(request);

            // 2. 取得したトークンが有効か判別
            if(StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt)){
                Authentication authentication = jwtTokenProvider.getAuthentication(jwt);

                // 3. 認証情報をSecurityContextにセット
                SecurityContextHolder.getContext().setAuthentication(authentication);

                // 4. ログ出力
                JwtTokenProvider.UserPrincipal principal = (JwtTokenProvider.UserPrincipal) authentication.getPrincipal();
                logger.debug("認証成功: userId={}, email={}, path={}", principal.getUserId(), principal.getEmail(), request.getRequestURI());
            } else{
                logger.debug("JWTトークンが見つからないか無効です: path={}", request.getRequestURI());
            }
        } catch(Exception ex){
            logger.error("SecurityContextに認証情報をセット出来ませんでした。", ex);
        }

        // 5. 次のフィルターにリクエストとレスポンスを渡す
        filterChain.doFilter(request, response);
    }

    /**
     * リクエストヘッダーからトークンを抽出するメソッド
     * @param request
     * @return
     */
    private String getJwtFromRequest(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");

        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            String token = bearerToken.substring(7);
            logger.debug("Authorizationヘッダーからトークンを抽出: {}", token.substring(0, Math.min(20, token.length())) + "...");
            return token;
        }

        return null;
    }
}

package com.example.backend.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.example.backend.security.JwtAuthenticationFilter;
import com.example.backend.security.JwtTokenProvider;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final JwtTokenProvider jwtTokenProvider;

    SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        // CORSを有効にする
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()))

            // CSRFを無効にする(クライアントサイドではCSRFトークンを使用しないため)
            .csrf().disable()

            // セッションを無効にする(ステートレスなAPIサーバーのため)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            // 認証が必要なURLを設定
            .authorizeHttpRequests(auth -> auth
            .requestMatchers("/admin/user/login", "/admin/user/signup", "/client/user/login", "/client/user/signup").permitAll()
            .requestMatchers("/admin/**").hasRole("ADMIN")
            .requestMatchers("/client/**").authenticated()
            .anyRequest().authenticated()
            )
            // JWT認証フィルターを追加
            .addFilterBefore(
                new JwtAuthenticationFilter(jwtTokenProvider),
                UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // CORSを有効にするメソッド
    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();

        // 許可するオリジンを設定
        configuration.setAllowedOrigins(Arrays.asList(
            "http://localhost:3000",
            "http://localhost:3001"
        ));

        // 許可するメソッドを設定
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // 許可するヘッダーを設定
        configuration.setAllowedHeaders(Arrays.asList("*"));

        // クレデンシャル(クッキーなど)を許可
        configuration.setAllowCredentials(true);

        // キャッシュの有効期限を設定
        configuration.setMaxAge(3600L);

        // CORSの設定を適用するURLパターンを設定
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        // CORSの設定を返す
        return source;
    }
}

package com.linkedin.com.user_service.handler;

import com.linkedin.com.user_service.entity.User;
import com.linkedin.com.user_service.repository.UserRepository;
import com.linkedin.com.user_service.service.JwtService;
import com.linkedin.com.user_service.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        DefaultOAuth2User oAuth2User = (DefaultOAuth2User) token.getPrincipal();

        String email = oAuth2User.getAttribute("email");
        System.out.println(oAuth2User);
        System.out.println(token);
        System.out.println(authentication.getCredentials()+" "+((OAuth2AuthenticationToken) authentication).getAuthorizedClientRegistrationId());

//        User user = userRepository.findByEmail(email).orElseThrow(()->new RuntimeException("User not found"));


            User newUser = User.builder()
                    .name(oAuth2User.getAttribute("name"))
                    .email(email)
                    .build();
            userRepository.save(newUser);


        String accessToken = jwtService.generateAccessToken(newUser);
        String refreshToken = jwtService.generateRefreshToken(newUser);

        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
//        cookie.setSecure("production".equals(deployEnv));
        response.addCookie(cookie);

        System.out.println(cookie);

        String frontEndUrl = "http://localhost:5173/login";

//        getRedirectStrategy().sendRedirect(request, response, frontEndUrl);

        response.sendRedirect(frontEndUrl);
    }

}

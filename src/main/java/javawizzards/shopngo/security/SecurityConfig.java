package javawizzards.shopngo.security;

//@EnableWebSecurity
public class SecurityConfig {

//    private final UserService userService;
//
//    public SecurityConfig(UserService userService) {
//        this.userService = userService;
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/cart/**").authenticated()  // Use 'requestMatchers' instead of 'antMatchers'
//                        .requestMatchers("/products/**").permitAll()   // Public access to products
//                )
//                .oauth2Login(oauth2 -> oauth2
//                        .defaultSuccessUrl("/cart") // Redirect after successful login
//                        .userInfoEndpoint(userInfo -> userInfo
//                                .userService(oAuth2UserService())
//                        )
//                );
//        return http.build();
//    }
//
//    @Bean
//    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService() {
//        return userRequest -> {
//            OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);
//
//            // After OAuth login, process the user (save if new)
//            userService.processOAuthPostLogin(oAuth2User);
//
//            return oAuth2User;
//        };
//    }
}

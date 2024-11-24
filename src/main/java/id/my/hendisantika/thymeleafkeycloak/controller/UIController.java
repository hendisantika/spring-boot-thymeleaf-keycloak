package id.my.hendisantika.thymeleafkeycloak.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Objects;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-thymeleaf-keycloak
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 25/11/24
 * Time: 06.36
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class UIController {
    @GetMapping("/")
    public String getIndex(Model model, Authentication auth) {
        model.addAttribute(
                "name",
                auth instanceof OAuth2AuthenticationToken oauth && oauth.getPrincipal() instanceof OidcUser oidc ?
                        oidc.getPreferredUsername() :
                        "");
        model.addAttribute("isAuthenticated", auth != null && auth.isAuthenticated());
        model.addAttribute("isNice", auth != null && auth.getAuthorities().stream().anyMatch(authority -> Objects.equals("NICE", authority.getAuthority())));
        return "index.html";
    }
}

package id.my.hendisantika.thymeleafkeycloak.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-thymeleaf-keycloak
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 25/11/24
 * Time: 06.34
 * To change this template use File | Settings | File Templates.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    interface AuthoritiesConverter extends Converter<Map<String, Object>, Collection<GrantedAuthority>> {
    }

}

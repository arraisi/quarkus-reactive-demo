package au.com.geekseat.helper;

import au.com.geekseat.dto.Principal;
import io.smallrye.jwt.build.Jwt;
import org.eclipse.microprofile.jwt.Claims;
import au.com.geekseat.model.Person;
import au.com.geekseat.model.Role;

import javax.enterprise.context.RequestScoped;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequestScoped
public class JWTUtils {

    public final static Long DURATION = 1800l;
    public final static String ISSUER = "geekseat";

    public static Principal generate(Person person) {
        Set<String> roles = person.getRoles().stream().map(Role::getName).collect(Collectors.toSet());
        if (roles.size() == 0) {
            return null;
        }
        Principal principal = new Principal(person.getId(), person.getName(), person.getEmail(), "");
        String token = Jwt
                .issuer(ISSUER)
                .upn(person.getEmail())
                .expiresIn(DURATION)
                .claim("principal", Utility.gson.toJson(principal))
                .groups(roles)
                .sign();
        principal.setToken(token);
        return principal;
    }
}

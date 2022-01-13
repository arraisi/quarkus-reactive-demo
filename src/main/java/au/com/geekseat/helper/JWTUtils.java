package au.com.geekseat.helper;

import au.com.geekseat.security.Principal;
import io.smallrye.jwt.build.Jwt;
import au.com.geekseat.model.Person;
import au.com.geekseat.model.Role;

import javax.enterprise.context.RequestScoped;
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
        Principal principal = new Principal(person.getId(), person.getName(), person.getEmail(),
                roles, null, roles.stream().anyMatch(s -> s.equals("admin")));
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

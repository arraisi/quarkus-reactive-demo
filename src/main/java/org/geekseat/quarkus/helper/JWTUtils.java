package org.geekseat.quarkus.helper;

import io.smallrye.jwt.build.Jwt;
import org.eclipse.microprofile.jwt.Claims;
import org.geekseat.quarkus.dto.Principal;
import org.geekseat.quarkus.model.Person;
import org.geekseat.quarkus.model.Role;

import javax.enterprise.context.RequestScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequestScoped
public class JWTUtils {

    public final static Long DURATION = 1800l;
    public final static String ISSUER = "geekseat";

    public static Principal generate(Person person) {
        List<String> roles =
                person.getRoles().stream().map(Role::getName).collect(Collectors.toList());
        roles = new ArrayList<>();
        roles.add("admin");
        if (roles.size() == 0) {
            return null;
        }
        Principal principal = new Principal(person.getId(), person.getName(), person.getEmail(), "");
        String token = Jwt
                .issuer(ISSUER)
                .upn(person.getEmail())
                .expiresIn(DURATION)
                .claim(Claims.groups, roles)
                .claim("principal", Utility.gson.toJson(principal))
                .sign();
        principal.setToken(token);
        return principal;
    }
}

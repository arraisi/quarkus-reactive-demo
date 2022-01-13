package au.com.geekseat.controller;

import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;
import au.com.geekseat.controller.request.AuthRequest;
import au.com.geekseat.helper.JWTUtils;
import au.com.geekseat.helper.PasswordEncoder;
import au.com.geekseat.helper.Utility;
import au.com.geekseat.service.PersonService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;


@Path("/auth")
@RequestScoped
public class AuthenticationController {

    private PersonService personService;
    private PasswordEncoder pwdEncoder;
    private JWTUtils jwtUtils;

    public AuthenticationController(PersonService personService,
                                    PasswordEncoder pwdEncoder,
                                    JWTUtils jwtUtils) {
        this.personService = personService;
        this.pwdEncoder = pwdEncoder;
        this.jwtUtils = jwtUtils;
    }

    @POST
    @Path("/login")
    public Uni<Response> personById(AuthRequest request) {
        return personService.findByUsername(request.getEmail())
                .map(person -> {
                    if (person == null || !Utility.checkPassword(request.getPassword(), person.getPassword())) {
                        Log.warn("User not found or incorrect password");
                        return Response.status(Response.Status.UNAUTHORIZED);
                    }
                    if (!person.getActiveFlag()) {
                        Log.warn("Login Failed user is inactive");
                        return Response.status(Response.Status.UNAUTHORIZED);
                    }
                    try {
                        return Response.ok(JWTUtils.generate(personService.fromDecorator.decorate(person)));
                    } catch (Exception e) {
                        Log.error("Error loggin in: {}");
                        return Response.status(Response.Status.BAD_REQUEST);
                    }
                }).map(Response.ResponseBuilder::build);
    }

}

package au.com.geekseat.controller;

import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;
import au.com.geekseat.controller.request.AuthRequest;
import au.com.geekseat.helper.JWTUtils;
import au.com.geekseat.helper.PasswordEncoder;
import au.com.geekseat.helper.Utility;
import au.com.geekseat.service.PersonService;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import static au.com.geekseat.service.PersonService.fromDecorator;

@Path("/auth")
@RequestScoped
public class AuthenticationController {

    private final PersonService personService;

    public AuthenticationController(PersonService personService,
                                    PasswordEncoder pwdEncoder,
                                    JWTUtils jwtUtils) {
        this.personService = personService;
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
                    if (!person.getActive()) {
                        Log.warn("Login Failed user is inactive");
                        return Response.status(Response.Status.UNAUTHORIZED);
                    }
                    try {
                        return Response.ok(JWTUtils.generate(fromDecorator.decorate(person)));
                    } catch (Exception e) {
                        Log.error("Error loggin in: {}");
                        return Response.status(Response.Status.BAD_REQUEST);
                    }
                }).map(Response.ResponseBuilder::build);
    }

}

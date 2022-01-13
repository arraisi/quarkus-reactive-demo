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

    @Inject
    private PersonService personService;

    @Inject
    private PasswordEncoder pwdEncoder;

    @POST
    @Path("/login")
    public Uni<Response> personById(AuthRequest request) {
        System.out.println(Utility.gson.toJson(request));
         return personService.findByUsername(request.getEmail())
                .map(person -> {
                    if (person != null && Utility.checkPassword(request.getPassword(), person.getPassword())) {
                        if (person.getActive()) {
                            try {
                                return Response.ok(JWTUtils.generate(personService.fromDecorator.decorate(person)));
                            } catch (Exception e) {
                                Log.error("Error loggin in: {}" );
                                return Response.status(Response.Status.BAD_REQUEST);
                            }
                        } else {
                            Log.warn("User not found or incorrect password");
                            return Response.status(Response.Status.UNAUTHORIZED);
                        }
                    } else {
                        Log.warn("User not found or incorrect password");
                        return Response.status(Response.Status.UNAUTHORIZED);
                    }
                }).map(Response.ResponseBuilder::build);
    }

}

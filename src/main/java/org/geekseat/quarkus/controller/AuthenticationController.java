package org.geekseat.quarkus.controller;

import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;
import org.geekseat.quarkus.controller.request.AuthRequest;
import org.geekseat.quarkus.helper.JWTUtils;
import org.geekseat.quarkus.helper.PasswordEncoder;
import org.geekseat.quarkus.service.PersonService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
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
    public Uni<Response> personById(@Valid AuthRequest request) {
         return personService.findByUsername(request.getEmail())
                .map(person -> {
                    if (person == null ||
                            !person.getPassword().equals(pwdEncoder.encode(request.getPassword()))) {
                        Log.warn("User not found or incorrect password");
                        return Response.status(Response.Status.UNAUTHORIZED);
                    } else {
                        try {
                            return Response.ok(JWTUtils.generate(personService.fromDecorator.decorate(person)));
                        } catch (Exception e) {
                            Log.error("Error loggin in: {}" );
                            return Response.status(Response.Status.UNAUTHORIZED);
                        }
                    }
                }).map(Response.ResponseBuilder::build);
    }

}

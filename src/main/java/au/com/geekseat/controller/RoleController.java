package au.com.geekseat.controller;

import au.com.geekseat.model.Role;
import au.com.geekseat.service.RoleService;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/role")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RoleController {
    @Inject
    RoleService roleService;

    @GET
    @Path("/{id}")
    public Uni<Role> roleById(Long id) {
        return roleService.findById(id);
    }
}

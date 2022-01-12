package au.com.geekseat.service;

import au.com.geekseat.model.Role;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RoleService implements PanacheRepository<Role> {
}

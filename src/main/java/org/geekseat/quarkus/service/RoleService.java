package org.geekseat.quarkus.service;

import org.geekseat.quarkus.model.Role;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RoleService implements PanacheRepository<Role> {
}

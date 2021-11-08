package io.arraisi.service;

import io.arraisi.model.Role;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RoleService implements PanacheRepository<Role> {
}

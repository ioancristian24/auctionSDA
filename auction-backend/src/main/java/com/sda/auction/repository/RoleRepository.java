package com.sda.auction.repository;

import com.sda.auction.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.Id;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findByRoleName(String roleName);

}

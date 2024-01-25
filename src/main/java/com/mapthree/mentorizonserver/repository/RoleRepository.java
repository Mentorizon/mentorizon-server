package com.mapthree.mentorizonserver.repository;

import com.mapthree.mentorizonserver.model.Role;
import com.mapthree.mentorizonserver.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, RoleName>  {
}

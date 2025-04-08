package org.wp2.medsys.repositories;

import org.wp2.medsys.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}

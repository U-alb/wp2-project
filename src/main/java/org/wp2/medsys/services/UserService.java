package org.wp2.medsys.services;

import org.wp2.medsys.domain.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    void save(User user);
}

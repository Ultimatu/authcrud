package com.ultimatum.authcrud.services;

import com.ultimatum.authcrud.models.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    public String signup(User user);

    public User getByEmailAndPassword(String email, String password);

    public List<User> getAll();

    public User getById(Integer id);


    String deleteById(Integer id);

    boolean getByEmail(String email);
    public User getDataByEmail(String email);

    public String update(User user);
}

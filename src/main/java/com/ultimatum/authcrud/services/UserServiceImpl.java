package com.ultimatum.authcrud.services;

import com.ultimatum.authcrud.models.User;
import com.ultimatum.authcrud.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class UserServiceImpl implements UserService{
    
    @Autowired
    private UserRepository userRepository;


    @Override
    public String signup(User user) {
          if (userRepository.findByEmail(user.getEmail()).isPresent()){
              return "utilisateur existant";
          }
          userRepository.save(user);
          return "ok";
    }

    @Override
    public User getByEmailAndPassword(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmailAndPassword(email, password);
        // handle the case when the user is not found
        return userOptional.orElse(null);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public String deleteById(Integer id) {
        if (userRepository.existsById(id)){
            userRepository.deleteById(id);
            return "ok";
        }
        return "utilisateur inexistant";
    }

    @Override
    public boolean getByEmail(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        return userOptional.isPresent();
    }

    @Override
    public User getDataByEmail(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        return userOptional.orElse(null);
    }

    @Override
    public User getById(Integer id) {
        Optional<User> userOptional = userRepository.findById(id);
        // handle the case when the user is not found
        return userOptional.orElse(null);
    }
    @Override
    public String update(User user) {
        Optional<User> userOptional = userRepository.findById(user.getId());
        if (userOptional.isPresent()) {
            String email = user.getEmail();

            // Vérifier si l'e-mail appartient à un autre utilisateur
            if (userRepository.existsByEmailAndIdNot(email, user.getId())) {
                return "ce mail appartient à un autre utilisateur";
            }

            User existingUser = userOptional.get();
            existingUser.setNom(user.getNom());
            existingUser.setPrenom(user.getPrenom());
            existingUser.setEmail(email);
            existingUser.setPassword(user.getPassword());

            userRepository.save(existingUser);
            return "ok";
        } else {
            return "utilisateur inexistant";
        }
    }
}

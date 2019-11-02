package taborski.kleczek.User.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import taborski.kleczek.User.exceptions.BadCredentialsException;
import taborski.kleczek.User.exceptions.ResourceAlreadyExistsException;
import taborski.kleczek.User.exceptions.ResourceNotFoundException;
import taborski.kleczek.User.model.User;
import taborski.kleczek.User.repository.IUserRepository;
import taborski.kleczek.User.service.IUserService;

@Service
public class UserService implements IUserService {
    private final IUserRepository userRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public User createUser(User user) throws ResourceAlreadyExistsException {
        User toSave = new User();
        if (userRepository.existsByName(user.getName())) {
            throw new ResourceAlreadyExistsException();
        }

        toSave.setName(user.getName());
        toSave.setPassword(passwordEncoder().encode(user.getPassword()));

        return userRepository.save(toSave);
    }

    @Override
    public User getUserById(Long id) throws ResourceNotFoundException {
        return userRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public User loginUser(User user) throws BadCredentialsException {
        User userFound = userRepository.findByName(user.getName()).orElseThrow(BadCredentialsException::new);
        if (passwordEncoder().matches(user.getPassword(), userFound.getPassword())) {
            return user;
        } else {
            throw new BadCredentialsException();
        }
    }
}

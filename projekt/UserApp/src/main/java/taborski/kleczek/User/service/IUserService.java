package taborski.kleczek.User.service;

import taborski.kleczek.User.exceptions.BadCredentialsException;
import taborski.kleczek.User.exceptions.ResourceAlreadyExistsException;
import taborski.kleczek.User.exceptions.ResourceNotFoundException;
import taborski.kleczek.User.model.User;

public interface IUserService {
    User createUser(User user) throws ResourceAlreadyExistsException;
    User getUserById(Long id) throws ResourceNotFoundException;
    User loginUser(User user) throws BadCredentialsException;
}

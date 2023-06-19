package by.epam.training.homework27.security;

import by.epam.training.homework27.model.User;
import by.epam.training.homework27.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        User user = userService.getUserByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " not found"));
        return CustomUserDetails.fromUserToCustomUserDetails(user);
    }
}

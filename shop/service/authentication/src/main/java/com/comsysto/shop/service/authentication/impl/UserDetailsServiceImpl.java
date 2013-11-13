package com.comsysto.shop.service.authentication.impl;

import com.comsysto.shop.repository.user.api.UserRepository;
import com.comsysto.shop.repository.user.model.Role;
import com.comsysto.shop.repository.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author zutherb
 */
@Component("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user != null){
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRoles(user));
        }
        throw new UsernameNotFoundException(String.format("Username '%s' not found", username));
    }

    private Collection<? extends GrantedAuthority> mapRoles(User user) {
        Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
        for(Role role : user.getRoles()){
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return grantedAuthorities;
    }
}

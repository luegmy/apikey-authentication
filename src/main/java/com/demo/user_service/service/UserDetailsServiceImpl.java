package com.demo.user_service.service;

import com.demo.user_service.exception.NotFoundUsernameException;
import com.demo.user_service.persistence.entity.UserEntity;
import com.demo.user_service.persistence.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundUsernameException( HttpStatus.NOT_FOUND,"The user was not found"));
        return new User(userEntity.getUsername(),
                userEntity.getPassword(),
                userEntity.isEnabled(),
                userEntity.isAccountNoExpired(),
                userEntity.isCredentialsNoExpired(),
                userEntity.isAccountNoLocked(),
                authorityListRolAndPermission(userEntity));
    }

    private List<SimpleGrantedAuthority> authorityListRolAndPermission(UserEntity userEntity) {
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        userEntity.getRoles().forEach(rol -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(rol.getRol().name()))));

        userEntity.getRoles().stream()
                .flatMap(rol -> rol.getPermissions().stream())
                .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getPermission())));
        return authorityList;
    }
}

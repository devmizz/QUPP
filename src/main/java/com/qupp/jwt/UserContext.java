package com.qupp.jwt;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;


import java.util.Set;
import java.util.stream.Collectors;

@Getter
@JsonIncludeProperties({"id", "email", "nickName", "authorities"})
public class UserContext extends User {
    private final long id;
    private final String email;
    private final String nickName;
    private final Set<GrantedAuthority> authorities;

    public UserContext(com.qupp.user.repository.User user) {
        super(user.getNickname(), "", user.getAuthorities());

        id = user.getId();
        nickName = user.getNickname();
        email = user.getEmail();
        authorities = user.getAuthorities().stream().collect(Collectors.toSet());
    }

}

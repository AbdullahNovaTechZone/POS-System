package com.novatechzone.pos.domain.security.domain;

import com.novatechzone.pos.domain.security.entity.Owner;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OwnerMapper {
    public UserDetails mapToOwnerDetails(Owner owner) {
        OwnerData ownerData = new OwnerData();
        ownerData.setUsername(owner.getUsername());
        ownerData.setPassword(owner.getPassword());

        SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority("Owner");

        ownerData.setAuthorities(List.of(grantedAuthority));
        return ownerData;
    }
}

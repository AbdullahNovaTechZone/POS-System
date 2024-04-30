package com.novatechzone.pos.domain.owner;

import com.novatechzone.pos.domain.security.entity.Owner;
import com.novatechzone.pos.domain.security.repos.OwnerRepository;
import com.novatechzone.pos.exception.ApplicationCustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class OwnerService {
    private final OwnerRepository ownerRepository;

    public Owner findByUsername(String username) {
        Optional<Owner> optionalOwner = ownerRepository.findByUsername(username);
        if (optionalOwner.isPresent()) {
            return optionalOwner.get();
        } else {
            throw new ApplicationCustomException(HttpStatus.UNAUTHORIZED, "USER_NOT_FOUND", "User not found");
        }
    }
}

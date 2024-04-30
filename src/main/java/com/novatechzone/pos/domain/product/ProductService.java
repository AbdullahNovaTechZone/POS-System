package com.novatechzone.pos.domain.product;

import com.novatechzone.pos.domain.security.entity.Owner;
import com.novatechzone.pos.domain.security.repos.OwnerRepository;
import com.novatechzone.pos.domain.security.service.AuthService;
import com.novatechzone.pos.dto.ApplicationResponseDTO;
import com.novatechzone.pos.exception.ApplicationCustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final OwnerRepository ownerRepository;

    public ApplicationResponseDTO create(ProductDTO productDTO) {
        String username = AuthService.getCurrentUser();
        Optional<Owner> optionalOwner = ownerRepository.findByUsername(username);
        if (optionalOwner.isEmpty()) {
            throw new ApplicationCustomException(HttpStatus.BAD_REQUEST, "INVALID_OWNER", "Invalid Owner!");
        }

        Owner owner = optionalOwner.get();

        productRepository.save(
                Product.builder()
                        .name(productDTO.getName())
                        .amount(productDTO.getAmount())
                        .ownerId(owner.getId())
                        .categoryId(productDTO.getCategoryId())
                        .unitId(productDTO.getUnitId())
                        .build()
        );

        return new ApplicationResponseDTO(HttpStatus.CREATED, "PRODUCT_REGISTERED_SUCCESSFULLY", "Product Registered Successfully!");
    }
}

package com.novatechzone.pos.domain.category;

import com.novatechzone.pos.dto.ApplicationResponseDTO;
import com.novatechzone.pos.exception.ApplicationCustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public ApplicationResponseDTO create(CategoryDTO categoryDTO) {
        if (categoryRepository.findByName(categoryDTO.getName()).isPresent()) {
            throw new ApplicationCustomException(HttpStatus.BAD_REQUEST, "CATEGORY_ALREADY_EXIST", "Category Already Exist");
        }
        categoryRepository.save(Category.builder().name(categoryDTO.getName()).build());
        return new ApplicationResponseDTO(HttpStatus.CREATED, "CATEGORY_CREATED", "Category Created");
    }
}

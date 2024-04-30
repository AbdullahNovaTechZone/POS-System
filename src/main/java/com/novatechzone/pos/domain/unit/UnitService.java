package com.novatechzone.pos.domain.unit;

import com.novatechzone.pos.dto.ApplicationResponseDTO;
import com.novatechzone.pos.exception.ApplicationCustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UnitService {
    private final UnitRepository unitRepository;
    public ApplicationResponseDTO create(UnitDTO unitDTO) {
        if (unitRepository.findByName(unitDTO.getName()).isPresent()) {
            throw new ApplicationCustomException(HttpStatus.BAD_REQUEST, "UNIT_ALREADY_EXIST", "Unit Already Exist");
        }
        unitRepository.save(Unit.builder().name(unitDTO.getName()).build());
        return new ApplicationResponseDTO(HttpStatus.CREATED, "UNIT_CREATED", "Unit Created");
    }
}

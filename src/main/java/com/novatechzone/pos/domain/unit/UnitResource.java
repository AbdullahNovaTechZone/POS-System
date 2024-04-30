package com.novatechzone.pos.domain.unit;

import com.novatechzone.pos.dto.ApplicationResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/unit")
public class UnitResource {
    private final UnitService unitService;
    @PostMapping("/create")
    public ResponseEntity<ApplicationResponseDTO> create(@RequestBody UnitDTO unitDTO){
        return ResponseEntity.ok(unitService.create(unitDTO));
    }
}

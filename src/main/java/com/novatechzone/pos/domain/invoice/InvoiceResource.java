package com.novatechzone.pos.domain.invoice;

import com.novatechzone.pos.dto.ApplicationResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/invoice")
public class InvoiceResource {
    private final InvoiceService invoiceService;
    @PostMapping("/create")
    public ResponseEntity<ApplicationResponseDTO> create(@RequestBody InvoiceDTO invoiceDTO){
        return ResponseEntity.ok(invoiceService.createInvoice(invoiceDTO));
    }
}

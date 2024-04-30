package com.novatechzone.pos.domain.invoice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class InvoiceDTO {
    private Long invoiceId;
    private Long productId;
    private Integer quantity;
    private Double total;
}

package com.novatechzone.pos.domain.product;

import com.novatechzone.pos.domain.category.Category;
import com.novatechzone.pos.domain.security.entity.Owner;
import com.novatechzone.pos.domain.unit.Unit;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "\"Product\"")
public class Product {
    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(
            name = "primary_sequence",
            sequenceName = "primary_sequence",
            allocationSize = 1,
            initialValue = 10000
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "primary_sequence"
    )
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "amount", nullable = false)
    private Double amount;
    @Column(name = "owner_id", nullable = false)
    private Long ownerId;
    @Column(name = "category_id", nullable = false)
    private Long categoryId;
    @Column(name = "unit_id", nullable = false)
    private Long unitId;
    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Owner owner;
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Category category;
    @ManyToOne
    @JoinColumn(name = "unit_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Unit unit;
}

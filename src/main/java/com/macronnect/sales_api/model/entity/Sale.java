package com.macronnect.sales_api.model.entity;

import com.macronnect.sales_api.model.enums.SaleStatus;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;


@Entity
@Table(name = "sales")
public class Sale extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private Long invoice_number;

    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    private SaleStatus status;

    @Column(precision = 10, scale = 2)
    private BigDecimal total;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToMany(mappedBy = "sale",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<SaleDetail> details = new ArrayList<>();


}

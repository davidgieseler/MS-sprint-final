package br.com.pb.msorder.domain.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @CPF(message = "${invalid.cpf}")
    @Length(min = 11, max = 11, message = "${lenght.cpf}")
    private String cpf;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Item> items;

    private BigDecimal totalValue;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;
}

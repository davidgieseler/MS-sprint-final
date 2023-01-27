package br.com.pb.msorder.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderProducer {

    private Long orderId;
    private BigDecimal totalValue;
}

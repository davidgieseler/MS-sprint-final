package br.com.pb.mshistory.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;

@Document
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class History {

    private String id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate eventDateTime;

    private Long orderId;

    private BigDecimal totalValue;
}

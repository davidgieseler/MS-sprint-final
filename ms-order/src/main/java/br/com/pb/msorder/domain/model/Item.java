package br.com.pb.msorder.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Campo nome não pode estar em branco!")
    @Pattern(regexp = "^([a-zA-ZãÃéÉíÍóÓêÊôÔáÁ\\s])+$", message = "Campo nome deve conter apenas letras!")
    private String name;

    @NotNull(message = "Nenhuma das datas pode estar em branco!")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate creationDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @NotNull(message = "Nenhuma das datas pode estar em branco!")
    private LocalDate expirationDate;

    @Positive(message = "O valor precisa ser positivo!")
    private BigDecimal value;

    @NotBlank(message = "Campo descrição não pode estar em branco!")
    private String description;
}

package br.com.pb.msorder.domain.dto;

import br.com.pb.msorder.domain.model.Address;
import br.com.pb.msorder.domain.model.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDTO {

    @Length(min = 11, max = 11, message = "${lenght.cpf}")
    private String cpf;
    private List<Item> items;
    private BigDecimal totalValue;
    private Address address;
}

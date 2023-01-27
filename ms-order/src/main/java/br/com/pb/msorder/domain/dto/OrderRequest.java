package br.com.pb.msorder.domain.dto;

import br.com.pb.msorder.domain.model.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequest {

    @Length(min = 11, max = 11, message = "${lenght.cpf}")
    @CPF(message = "${invalid.cpf}")
    private String cpf;
    private List<Item> items;
    private AddressRequest address;
}

package br.com.pb.msorder.domain.dto;

import java.util.List;

import br.com.pb.msorder.domain.model.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PageableDTO {

    private Integer numberOfElements;
    private Long totalElements;
    private Integer totalPages;
    private List<Order> orderList;
}

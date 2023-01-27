package br.com.pb.msorder.application.ports.in;

import br.com.pb.msorder.domain.dto.OrderRequest;
import br.com.pb.msorder.domain.dto.OrderDTO;
import br.com.pb.msorder.domain.dto.PageableDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public interface OrderUseCase {
    OrderDTO create(OrderRequest request) throws JsonProcessingException;

    PageableDTO findAll(String cpf, BigDecimal totalValue, Pageable pageable);

    OrderDTO findById(Long id);

    OrderDTO update(Long id, OrderRequest request);

    void delete(Long id);
}

package br.com.pb.msorder.framework.adapter.in;

import br.com.pb.msorder.application.ports.in.OrderUseCase;
import br.com.pb.msorder.domain.dto.OrderRequest;
import br.com.pb.msorder.domain.dto.OrderDTO;
import br.com.pb.msorder.domain.dto.PageableDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Tag(name = "Order")
@RequestMapping(value = "/orders")
@RequiredArgsConstructor
@RestController
@CrossOrigin("*")
public class OrderController {

    private final OrderUseCase orderService;

    @Operation(summary = "Cadastrar pedido")
    @PostMapping
    public ResponseEntity<OrderDTO> create(@RequestBody @Valid OrderRequest request) throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.create(request));
    }

    @Operation(summary = "Listar pedidos")
    @GetMapping
    public PageableDTO findAll(@RequestParam(required = false) String cpf, @RequestParam(required = false) BigDecimal totalValue, Pageable pageable) {
        return orderService.findAll(cpf, totalValue, pageable);
    }

    @Operation(summary = "Buscar pedido por ID")
    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.findById(id));
    }

    @Operation(summary = "Atualizar pedido por ID")
    @PutMapping("/{id}")
    public ResponseEntity<OrderDTO> update(@PathVariable Long id, @RequestBody @Valid OrderRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.update(id, request));
    }

    @Operation(summary = "Excluir pedido")
    @DeleteMapping("/{id}")
    public ResponseEntity<OrderDTO> delete(@PathVariable Long id) {
        orderService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

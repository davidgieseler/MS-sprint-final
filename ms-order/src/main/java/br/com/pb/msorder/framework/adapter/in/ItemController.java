package br.com.pb.msorder.framework.adapter.in;

import br.com.pb.msorder.application.ports.in.ItemUseCase;
import br.com.pb.msorder.domain.dto.ItemDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Item")
@RequestMapping("/item")
@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class ItemController {

    private final ItemUseCase itemService;

    @Operation(summary = "Atualizar item")
    @PatchMapping("/{id}")
    public ResponseEntity<ItemDTO> patch(@PathVariable Long id, @RequestBody @Valid ItemDTO request) {
        return ResponseEntity.status(HttpStatus.OK).body(itemService.patch(id, request));
    }
}

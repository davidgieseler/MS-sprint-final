package br.com.pb.mshistory.framework.adapter.in;

import br.com.pb.mshistory.application.service.HistoryService;
import br.com.pb.mshistory.domain.dto.PageableDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Tag(name = "Order")
@RestController
@RequiredArgsConstructor
@RequestMapping("/history")
@CrossOrigin("*")
public class HistoryController {

    private final HistoryService service;

    @Operation(summary = "Listar")
    @GetMapping
    public PageableDTO findAll(@RequestParam(required = false) @DateTimeFormat(pattern="dd-MM-yyyy") LocalDate date, Pageable pageable) {
        return service.findAll(date, pageable);
    }
}

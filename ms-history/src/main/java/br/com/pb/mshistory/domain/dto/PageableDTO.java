package br.com.pb.mshistory.domain.dto;

import br.com.pb.mshistory.domain.model.History;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PageableDTO {

    private Integer numberOfElements;
    private Long totalElements;
    private Integer totalPages;
    private List<History> historyList;
}
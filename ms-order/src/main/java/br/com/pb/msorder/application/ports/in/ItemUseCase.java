package br.com.pb.msorder.application.ports.in;

import br.com.pb.msorder.domain.dto.ItemDTO;

public interface ItemUseCase {
    ItemDTO patch(Long id, ItemDTO request);
}

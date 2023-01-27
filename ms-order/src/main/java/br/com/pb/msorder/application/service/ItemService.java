package br.com.pb.msorder.application.service;

import br.com.pb.msorder.application.ports.in.ItemUseCase;
import br.com.pb.msorder.domain.dto.ItemDTO;
import br.com.pb.msorder.domain.model.Item;
import br.com.pb.msorder.domain.model.Order;
import br.com.pb.msorder.framework.adapter.out.repository.ItemRepository;
import br.com.pb.msorder.framework.adapter.out.repository.OrderRepository;
import br.com.pb.msorder.framework.exception.GenericException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService implements ItemUseCase {

    private final ItemRepository repository;

    private final OrderRepository orderRepository;

    private final ModelMapper modelMapper;

    @Override
    public ItemDTO patch(Long id, ItemDTO request) {
        Item item = repository.findById(id).orElseThrow(() -> new GenericException(HttpStatus.BAD_REQUEST, "Id não encontrado!"));
        checkDates(item);

        if (request.getCreationDate() != null) {
            item.setCreationDate(request.getCreationDate());
        }
        if (request.getExpirationDate() != null) {
            item.setExpirationDate(request.getExpirationDate());
        }
        if (request.getValue() != null) {
            item.setValue(request.getValue());
        }
        if (request.getDescription() != null) {
            item.setDescription(request.getDescription());
        }

        repository.save(item);
        updateOrderTotalValue(item);
        return modelMapper.map(item, ItemDTO.class);
    }

    private BigDecimal calculateTotalValue(Order order) {
        List<Item> items = order.getItems();
        return items.stream().map(Item::getValue).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void updateOrderTotalValue(Item item) {
        Order order = orderRepository.findByItemsContains(item);
        order.setTotalValue(calculateTotalValue(order));
        orderRepository.save(order);
    }

    private void checkDates(Item item) {
        if (item.getCreationDate() == null || item.getExpirationDate() == null || item.getExpirationDate().isBefore(item.getCreationDate())) {
            throw new GenericException(HttpStatus.BAD_REQUEST, "A data de expiração não pode ser nula ou anterior à data de criação!");
        }
    }
}

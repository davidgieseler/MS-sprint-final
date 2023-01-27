package br.com.pb.msorder.application.service;

import br.com.pb.msorder.application.ports.in.OrderUseCase;
import br.com.pb.msorder.domain.dto.OrderRequest;
import br.com.pb.msorder.domain.dto.OrderDTO;
import br.com.pb.msorder.domain.dto.PageableDTO;
import br.com.pb.msorder.domain.model.Address;
import br.com.pb.msorder.domain.model.Item;
import br.com.pb.msorder.domain.model.Order;
import br.com.pb.msorder.framework.adapter.out.event.TopicProducer;
import br.com.pb.msorder.framework.adapter.out.viacep.ViaCep;
import br.com.pb.msorder.framework.adapter.out.repository.OrderRepository;
import br.com.pb.msorder.framework.exception.GenericException;
import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class OrderService implements OrderUseCase {

    private final OrderRepository orderRepository;

    private final ModelMapper modelMapper;

    private final ViaCep cepService;

    private final TopicProducer topicProducer;

    private final ObjectMapper objectMapper;

    @Override
    public OrderDTO create(OrderRequest request) throws JsonProcessingException {
        checkDate(request);
        Order order = modelMapper.map(request, Order.class);
        order.setAddress(getAddress(request.getAddress().getCep(), request.getAddress().getNumero(), request.getAddress().getLogradouro(), request.getAddress().getBairro()));
        checkCep(order);
        order.setTotalValue(amount(order));
        orderRepository.save(order);

        Order orderSend = modelMapper.map(order, Order.class);
        String topicSend = objectMapper.writeValueAsString(orderSend);
        topicProducer.sendMessage(topicSend);

        return modelMapper.map(order, OrderDTO.class);
    }

    @Override
    public PageableDTO findAll(String cpf, BigDecimal totalValue, Pageable pageable) {
        Page<Order> page;
        if (cpf == null || cpf.trim().length() == 0) {
            page = orderRepository.findAll(pageable);
        } else {
            page = orderRepository.findByCpf((cpf.trim()), pageable);
            if (page.isEmpty()) {
                throw new GenericException(HttpStatus.BAD_REQUEST, "Sem pedidos neste CPF!");
            }
        }

        if (totalValue == null) {
            page = orderRepository.findAll(pageable);
        } else {
            page = orderRepository.findByTotalValue(totalValue, pageable);
            if (page.isEmpty()) {
                throw new GenericException(HttpStatus.BAD_REQUEST, "Nenhum pedido encontrado com esse valor.");
            }
        }

        List<Order> orders = page.getContent();

        return PageableDTO
                .builder()
                .numberOfElements(page.getNumberOfElements())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .orderList(orders)
                .build();
    }

    @Override
    public OrderDTO findById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new GenericException(HttpStatus.BAD_REQUEST, "ID inexistente!"));
        return modelMapper.map(order, OrderDTO.class);
    }

    @Override
    public OrderDTO update(Long id, OrderRequest request) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new GenericException(HttpStatus.BAD_REQUEST, "ID inexistente!"));

        order.setCpf(request.getCpf());
        order.setAddress(getAddress(request.getAddress().getCep(), request.getAddress().getNumero(), request.getAddress().getLogradouro(), request.getAddress().getBairro()));

        orderRepository.save(order);
        return modelMapper.map(order, OrderDTO.class);
    }

    @Override
    public void delete(Long id) {
        checkIfIdExists(id);
        orderRepository.deleteById(id);
    }

    private void checkIfIdExists(Long id) {
        orderRepository.findById(id).orElseThrow(() -> new GenericException(HttpStatus.BAD_REQUEST, "Id não encontrado!"));
    }

    private BigDecimal amount(Order order) {
        List<Item> items = order.getItems();
        BigDecimal total = new BigDecimal(0);
        for (Item item : items) {
            total = total.add(item.getValue());
        }
        return total;
    }

    private Address getAddress(String cep, String number, String street, String district) {
        Address request = cepService.findAddressByCep(cep);
        Address address = new Address();
        address.setCep(request.getCep());
        address.setStreet(street);
        address.setNumber(number);
        address.setDistrict(district);
        address.setLocation(request.getLocation());
        address.setUf(request.getUf());
        return address;
    }

    private void checkDate(OrderRequest request) {
        request.getItems().forEach(data -> {
            if (data.getCreationDate() == null || data.getExpirationDate() == null ||data.getCreationDate().isAfter(data.getExpirationDate())) {
                throw new GenericException(HttpStatus.BAD_REQUEST, "A data de expiração não pode ser nula ou anterior à data de criação!");
            }
        });
    }
    private void checkCep(Order order) {
        if (order.getAddress().getUf() == null) {
            throw new GenericException(HttpStatus.BAD_REQUEST, "CEP inexistente/invalido");
        }
    }
}
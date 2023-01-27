package br.com.pb.msorder.application.service;

import br.com.pb.msorder.domain.dto.AddressRequest;
import br.com.pb.msorder.domain.dto.OrderRequest;
import br.com.pb.msorder.domain.dto.OrderDTO;
import br.com.pb.msorder.domain.dto.PageableDTO;
import br.com.pb.msorder.domain.model.Item;
import br.com.pb.msorder.domain.model.Order;
import br.com.pb.msorder.framework.adapter.out.viacep.ViaCep;
import br.com.pb.msorder.framework.adapter.out.repository.OrderRepository;
import br.com.pb.msorder.framework.exception.GenericException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @InjectMocks
    private OrderService service;

    @Mock
    private OrderRepository repository;

    @MockBean
    private ViaCep cepService;

    @Spy
    private ModelMapper modelMapper;

    @Test
    void findAllReturnSuccess() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Order> orders = Arrays.asList(new Order());
        Page<Order> page = new PageImpl<>(orders);
        when(repository.findAll(pageable)).thenReturn(page);

        PageableDTO result = service.findAll(null, null, pageable);
        assertNotNull(result);
        assertEquals(1, result.getNumberOfElements());
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        assertEquals(orders, result.getOrderList());
    }


    @Test
    void invalidCpfThrowsException() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Order> page = new PageImpl<>(Collections.emptyList());
        when(repository.findByCpf("04043674023", pageable)).thenReturn(page);

        assertThrows(GenericException.class, () ->
            service.findAll("04043674023", null, pageable));
    }

    @Test
    void returnPageableDTObyTotal() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Order> orders = Arrays.asList(new Order());
        Page<Order> page = new PageImpl<>(orders);

        when(repository.findByTotalValue(new BigDecimal(10), pageable)).thenReturn(page);

        PageableDTO result = service.findAll(null, new BigDecimal(10), pageable);

        assertEquals(1, result.getNumberOfElements());
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        assertEquals(orders, result.getOrderList());
    }

    @Test
    void returnsCorrectPageableDTO() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Order> orders = Arrays.asList(new Order());
        Page<Order> page = new PageImpl<>(orders);
        when(repository.findByTotalValue(new BigDecimal(10), pageable)).thenReturn(page);

        PageableDTO result = service.findAll(null, new BigDecimal(10), pageable);
        assertEquals(1, result.getNumberOfElements());
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        assertEquals(orders, result.getOrderList());
    }


    @Test
    void findOrderById() {
        Order order = new Order();
        OrderDTO orderDTO = new OrderDTO();
        when(repository.findById(anyLong())).thenReturn(Optional.of(order));
        when(modelMapper.map(order, OrderDTO.class)).thenReturn(orderDTO);

        OrderDTO result = service.findById(anyLong());

        assertEquals(orderDTO, result);
    }

    @Test
    void invalidIdThrowsException() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(GenericException.class, () ->
            service.findById(anyLong()));
    }

    @Test
    void invalidId_throwsException() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        OrderRequest request = new OrderRequest();
        assertThrows(GenericException.class, () ->
            service.update(anyLong(), request));
    }

    @Test
    void deleteOrder() {
        Order order = new Order();
        when(repository.findById(anyLong())).thenReturn(Optional.of(order));

        service.delete(anyLong());

        verify(repository).deleteById(anyLong());
    }

    @Test
    public void invalidIdDeleteThrowException() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(GenericException.class, () ->
                service.delete(anyLong()));
    }
}
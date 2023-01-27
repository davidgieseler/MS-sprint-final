package br.com.pb.msorder.framework.adapter.out.rest;

import br.com.pb.msorder.application.ports.out.OrderPortOut;
import br.com.pb.msorder.framework.adapter.out.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderAdapterOutImpl implements OrderPortOut {

    private final OrderRepository repository;

}

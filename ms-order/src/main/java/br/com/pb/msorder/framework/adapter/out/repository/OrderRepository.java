package br.com.pb.msorder.framework.adapter.out.repository;

import br.com.pb.msorder.domain.model.Item;
import br.com.pb.msorder.domain.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findAll(Pageable pageable);

    Page<Order> findByCpf(String cpf, Pageable pageable);

    Page<Order> findByTotalValue(BigDecimal totalValue, Pageable pageable);

    Order findByItemsContains(Item item);
}

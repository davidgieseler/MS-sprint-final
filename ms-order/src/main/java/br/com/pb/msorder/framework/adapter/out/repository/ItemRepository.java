package br.com.pb.msorder.framework.adapter.out.repository;

import br.com.pb.msorder.domain.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {}

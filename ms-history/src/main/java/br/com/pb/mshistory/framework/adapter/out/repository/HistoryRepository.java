package br.com.pb.mshistory.framework.adapter.out.repository;

import br.com.pb.mshistory.domain.model.History;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface HistoryRepository extends MongoRepository<History, String> {

    History save(History request);

    Page<History> findByEventDateTime(LocalDate date, Pageable pageable);
}

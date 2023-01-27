package br.com.pb.mshistory.framework.adapter.out.event;

import br.com.pb.mshistory.application.service.HistoryService;
import br.com.pb.mshistory.domain.model.History;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class TopicConsumer {

    private final HistoryService historyService;

    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "topic_history", groupId = "group_id")
    public void consume(String historic) throws JsonProcessingException {

        History history = objectMapper.readValue(historic, History.class);

        historyService.save(history);
    }
}

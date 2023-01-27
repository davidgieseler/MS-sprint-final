package br.com.pb.msorder.framework.adapter.out.event;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TopicProducer {

    @Value("topic_history")
    private String topicHistory;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String historic) {
        kafkaTemplate.send(topicHistory, historic);
    }
}

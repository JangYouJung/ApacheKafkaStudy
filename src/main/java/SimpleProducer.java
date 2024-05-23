import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class SimpleProducer {
    private final static Logger logger = LoggerFactory.getLogger(SimpleProducer.class);
    private final static String TOPIC_NAME = "test";
    private final static String BOOTSTRAP_SERVERS = "my-kafka:9092";

    public static void main(String[] args) {

        Properties configs = new Properties();

        // 필수값: bootstrap server, 메시지 키와 값 직렬화
        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // 프로듀서 인스턴스 생성
        KafkaProducer<String, String> producer = new KafkaProducer<>(configs);

        String messageValue = "testMessage";

        // [1] 레코드 생성 ( 토픽 이름, 메시지 값 )
        ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC_NAME, messageValue);

        // [2] 레코드 전송
        producer.send(record);
        logger.info("{}", record);
        producer.flush(); // accumulator 데이터 강제 전송

        // [3] 프로듀서 종료
        producer.close();

    }

}

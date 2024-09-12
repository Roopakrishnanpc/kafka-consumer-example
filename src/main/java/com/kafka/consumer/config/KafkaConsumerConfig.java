package com.kafka.consumer.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.DescribeTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.retry.backoff.BackOffPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.util.backoff.FixedBackOff;

import com.kafka.dto.Customer;

import lombok.extern.slf4j.Slf4j;

//import com.kafka.consumer.service.KafkaMessageListner;
@Configuration
@Slf4j
public class KafkaConsumerConfig {
//
	@Value("${app.topic.name}")
	private String topicName;
	
	@Bean
public NewTopic createTopic()
{
	return new NewTopic(topicName, 5,(short)1);
}
	


//    @Bean
//    public Map<String, Object> consumerConfigs() {
//        Map<String, Object> configProps = new HashMap<>();
//        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//       // configProps.put(ConsumerConfig.GROUP_ID_CONFIG, "myLeanings-JavaCustomer-Next-consumer-group-id");
//        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);//StringDeserializer.class.getName());
//        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
////        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);//ErrorHandlingDeserializer.class.getName());
////        configProps.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);//JsonDeserializer.class.getName());
//        configProps.put(JsonDeserializer.TRUSTED_PACKAGES, "com.kafka.dto");
//      //  configProps.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
//      //  configProps.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
//       // DescribeTopicsResult result = adminClient.describeTopics(List.of("Java-CustomerTopic-New-Next-topic"));
//      //  configProps.put(JsonDeserializer.VALUE_DEFAULT_TYPE, Customer.class.getName());
//
//        return configProps;
//    }
//
//    @Bean
//    public ConsumerFactory<String, Object> consumerFactory() {
//        return new DefaultKafkaConsumerFactory<>(consumerConfigs(), new StringDeserializer(),new JsonDeserializer());// new ErrorHandlingDeserializer<>(new JsonDeserializer<>()));//(Customer.class)));
//    }
//    private static final Logger logger = LoggerFactory.getLogger(KafkaMessageListner.class);
//
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory() {
//        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactory());
      //  factory.setRetryTemplate(retryTemplate());
//        factory.setCommonErrorHandler(new DefaultErrorHandler(    (record, exception) -> {
//            logger.error("Error processing record: {}, Exception: {}", record, exception.getMessage());
//            // Optionally handle or log the error
//        }
//    ));
        
//        return factory;
//    }
//    @Bean
//    public RetryTemplate retryTemplate() {
//        RetryTemplate retryTemplate = new RetryTemplate();
//        FixedBackOff backOff = new FixedBackOff(1000L, 3); // Backoff in milliseconds, and retry 3 times
//        retryTemplate.setBackOffPolicy((BackOffPolicy) backOff);
//        return retryTemplate;
//    }
//
//    @Bean
//    public Map<String, Object> consumerConfigs() {
//        Map<String, Object> configProps = new HashMap<>();
//        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//       // configProps.put(ConsumerConfig.GROUP_ID_CONFIG, "myLeanings-JavaCustomer-Next-consumer-group-id");
//        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,JsonDeserializer.class);
//       // configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
//       // configProps.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);
//        configProps.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
//       // configProps.put(JsonDeserializer.TRUSTED_PACKAGES, "com.kafka.consumer.dto");
//        //configProps.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
//       // configProps.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
//
//      //  configProps.put(JsonDeserializer.VALUE_DEFAULT_TYPE, Customer.class.getName());
//        configProps.put(JsonDeserializer.VALUE_DEFAULT_TYPE, Customer.class.getName());
//        configProps.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
//        configProps.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
//        return configProps;
//    }
//
//    @Bean
//    public ConsumerFactory<String, Object> consumerFactory() {
//        return new DefaultKafkaConsumerFactory<>(consumerConfigs());//, new StringDeserializer(), new ErrorHandlingDeserializer<>(new JsonDeserializer<>(Customer.class)));
//    }
//    private static final Logger logger = LoggerFactory.getLogger(KafkaMessageListner.class);
//
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory() {
//        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactory());
////        factory.setCommonErrorHandler(new DefaultErrorHandler(    (record, exception) -> {
////            logger.error("Error processing record: {}, Exception: {}", record, exception.getMessage());
////            // Optionally handle or log the error
////        }
////    ));
//        return factory;
//    }
}

	



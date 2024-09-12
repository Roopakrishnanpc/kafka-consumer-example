package com.kafka.consumer.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.retrytopic.TopicSuffixingStrategy;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.kafka.dto.Customer;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KafkaMessageListner {
Logger logger=LoggerFactory.getLogger(KafkaMessageListner.class);
//4instance created but who it will call is on zookeeper, if one fails another will be called

    @KafkaListener(topics = "Java-New-Next-topiec", groupId = "myLeanings-Next-consumer-group-id")
    public void consumerGroup1(String message) {
        logger.info("Consumer in group 'myLeanings-Java-consumer-group-id' consumed the message: " + message);
    }

    	           // ,backoff = @Backoff(delay = 1000, multiplier = 2), // 1 second delay, doubled each retry
    	           // retryTopic = "retry-topic", // Name of the retry topic
    	           // dltTopic = "dlt-topic" // Name of the Dead Letter Topic)
    	//@KafkaListener(topics = "Java-CustomerTopic-New-Next-topic", groupId = "MYTOPICmyLeanings-JavaCustomer-Next-consumer-group-id")
//    @RetryableTopic(attempts="4", backoff=@Backoff(delay=3000,multiplier=1.5,maxDelay=1500))
//    	    (attempts = "5")

        //,exclude= {NullPointerException.class,RuntimeException.class},
      //  backoff = @Backoff(delay = 5000, multiplier = 1.5, maxDelay = 30000)
    //)
@RetryableTopic(
        attempts = "4",
        backoff = @Backoff(delay = 1000),
        topicSuffixingStrategy =  TopicSuffixingStrategy.SUFFIX_WITH_INDEX_VALUE
    )
	@KafkaListener(topics = "${app.topic.name}", groupId = "MYTOPICmyLeanings-JavaCustomer-Next-consumer-group-id")
    	public void consumerGroupCustomer(Customer customer, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic, @Header(KafkaHeaders.OFFSET) long offset) {
    	    try {//new ObjectMapper().writeValueAsString(customer)
    	    	log.info("Consumed message: {}, from{}, offset{}", new ObjectMapper().writeValueAsString(customer),topic, offset);//.toString());
    	    	//log.info("Consumed message: {}, from{}, offset{}", customer.toString(),topic, offset);//.toString());
    	    	
    	    	//log.info("Consumed message: {}, from{}, offset{}", customer,topic, offset);//.toString());
    	        //logger.info("Consumed message: {}", customer.toString());
    	        //System.out.println("Validate if restriction of getIP ADDRESSS, Customer must not have the ip address");
    	       // List<String> restrictedIpList = Stream.of("32.241.244.236", "15.55.49.164", "81.9.95.253").collect(Collectors.toList());
    	        List<String> restrictedIpList = Stream.of("32.241.244.236", "15.55.49.164", "81.1.95.253", "126.130.43.183").collect(Collectors.toList());
    	        if (restrictedIpList.contains(customer.getIpAddress())) {
    	            throw new RuntimeException("Invalid IP address received:");
    	        }
    	        }
    	     catch (JsonProcessingException e) {
    	        e.printStackTrace();
    	        //throw e; // Important: Throw the exception to trigger retry and ultimately DLT
    	    }
//    	    catch (Exception e) {
//    	        log.error("Error processing message: {}", e.getMessage(), e);
//    	        //throw e; // Important: Throw the exception to trigger retry and ultimately DLT
//    	    }
    	}

 
	@DltHandler
	public void listenDLT(Customer customer, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic, @Header(KafkaHeaders.OFFSET) long offset) {
	    log.info("DLT Consumed message: received {}, from {}, offset {}", customer, topic, offset);
	}

@RetryableTopic(attempts = "4")// 3 topic N-1
@KafkaListener(topics = "helloeg", groupId = "new-simple-error-group") //is not working may be then check if ip address is null
//@KafkaListener(topics = "${app.topic.name}", groupId = "new-simple-error-group")
public void consumeEvents(Customer customer, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic, @Header(KafkaHeaders.OFFSET) long offset) {
    try {
       log.info("Received: {} from {} offset {}", new ObjectMapper().writeValueAsString(customer),topic, offset);
     //   log.info("Received: {} from {} offset {}", (customer),topic, offset);
        //validate restricted IP before process the records
        List<String> restrictedIpList = Stream.of("32.241.244.236", "15.55.49.164", "81.1.95.253", "126.130.43.183").collect(Collectors.toList());
        if (restrictedIpList.contains(customer.getIpAddress())) {
            throw new RuntimeException("Invalid IP Address received !");
        }}
        catch (JsonProcessingException e) { e.printStackTrace();}
//    } catch (Exception e) {
//        e.printStackTrace();
//    }
}

@DltHandler
public void listenDLT1(Customer customer, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic, @Header(KafkaHeaders.OFFSET) long offset) {//throws JsonProcessingException {
    log.info("DLT Received : {} , from {} , offset {}",customer.getFirstName(),topic,offset);
}

//i beleive what ever u send will go to partition 2
    @KafkaListener(topics = "New-Learn-Topic-Partitions", groupId = "New-Learn-Topic-Partition-group-id",topicPartitions= {@TopicPartition(topic="New-Learn-Topic-Partitions",partitions= {"2"})})
    public void consumerGroupPartition(String customer){//, Acknowledgment acknowledgment) {
        try {
            logger.info("Consumed message: {}", customer.toString());
           // acknowledgment.acknowledge(); // Commit offset after successful processing
        } catch (Exception e) {
            logger.error("Error processing message: {}", e.getMessage(), e);
        }
    }
//created many instance for same topic
    @KafkaListener(topics = "Java-New-Next-topiec", groupId = "myLeanings-Java-consumer-group-id")
    public void consumerGroup2(String message) {
        logger.info("Consumer in group 'myLeanings-Java-consumer-group-id-1' consumed the message: " + message);
    }

    @KafkaListener(topics = "Java-New-Next-topiec", groupId = "myLeanings-Java-consumer-group-id")
    public void consumeGroup3(String message) {
        logger.info("Consumer in group 'myLeanings-Java-consumer-group-id-2' consumed the message: " + message);
    }
	@KafkaListener(topics="Java-New-Next-topiec", groupId = "myLeanings-Java-consumer-group-id")
	public void consumerGroup4(String message)
	{
		logger.info("consumer consumed the message"+message);
	}
	
    @KafkaListener(topics = "Java-CustomerTopic-New-Next-topic", groupId = "myLeanings-JavaCustomer-Next-consumer-group-id")
    public void consumerGroupCustomer(Customer customer) {
        try {
            logger.info("Consumed message: {}", customer);
            // Process the customer object here
        } catch (Exception e) {
            logger.error("Error processing message: {}", e.getMessage(), e);
        }
    }
}

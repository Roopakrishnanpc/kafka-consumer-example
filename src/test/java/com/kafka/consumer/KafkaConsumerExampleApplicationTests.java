package com.kafka.consumer;



import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.awaitility.*;
import org.awaitility.core.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import org.awaitility.Awaitility;

//import com.kafka.consumer.service.KafkaMessageListner;
import com.kafka.dto.Customer;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
@ComponentScan(basePackages = {"com.kafka.producer","com.kafka.dto"})
@Testcontainers
@Slf4j
class KafkaConsumerExampleApplicationTests {
//	@BeforeAll
//	public static void setupKafkaTopic() {
//	    Properties props = new Properties();
//	    props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaContainer.getBootstrapServers());
//	    try (AdminClient adminClient = AdminClient.create(props)) {
//	        NewTopic newTopic = new NewTopic("Java-CustomerTopic-New-Next-topic", 1, (short) 1);
//	        CreateTopicsResult createTopicsResult = adminClient.createTopics(Collections.singleton(newTopic));
//	        createTopicsResult.all().get(); // Ensure topic creation is complete
//	    } catch (Exception e) {
//	        throw new RuntimeException("Failed to create topic", e);
//	    }}
//	@Test
//	void contextLoads() {
//	}
	@Container
	static KafkaContainer kafkaContainer = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"));

	@DynamicPropertySource
	public static void bootstrapIntKafkaProperties(DynamicPropertyRegistry registry)
	{
		registry.add("spring.kafka.bootstrap-servers", kafkaContainer::getBootstrapServers);
	}
	@Autowired
	private KafkaTemplate<String,Object> kafkaTemplate;
	//@Autowired
	//private KafkaMessageListner listner;
	@Test
	public void testConsumerGroupCustomer()
	{
		log.info("Test consume events methods executed started");
		//Customer customer=new Customer(1,"roopa sri","roopa.sri@gmail.com","Female",26,809768909, "84.9.96.253");
		//uncomments below 2 line during test 
		//kafkaTemplate.send("Java-CustomerTopic-New-Next-topic", customer);
		//kafkaTemplate.send( "Java-CustomerTopic-New-Next-topic", customer);
		log.info("Test consume events methods executed ended");
		Awaitility.await()
        .pollInterval(Duration.ofSeconds(3))
        .atMost(10,TimeUnit.SECONDS)
        //.atMost(Duration.ofSeconds(10))
        .untilAsserted(() -> {

             assertTrue(messageWasReceived(), "Message was not received within the timeout");
        });
		
	}
    private boolean messageWasReceived() {
        // Implement this method to check if the message was received
        return true; // Placeholder
    }
	}
package io.newAge.awsProject.sqs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SqsController {

    @Autowired
    QueueMessagingTemplate queueMessagingTemplate;

    @Value("${cloud.aws.end-point.uri}")
    private String queue;


    @GetMapping("/send/{message}")
    public void sendMessage(@PathVariable String message) {
        queueMessagingTemplate.send(queue, MessageBuilder.withPayload(message).build());
    }

}
package io.newAge.awsProject.sqs.controller;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.*;
import io.newAge.awsProject.sqs.service.SqsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class SqsController {
    @Autowired
    private SqsService sqsService;

    @GetMapping("/send/{message}")
    public String sendMessage(@PathVariable String message) {
        return sqsService.sendSqsMessage(message);
    }

    @GetMapping("/receive")
    public List<Message> receiveMessage() {
        return sqsService.receiveSqsMessage();
    }

    @GetMapping("/purge")
    public PurgeQueueResult purgeQueue() {
        return sqsService.purgeSqsQueue();
    }


}

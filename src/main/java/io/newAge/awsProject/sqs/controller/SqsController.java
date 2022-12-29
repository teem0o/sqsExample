package io.newAge.awsProject.sqs.controller;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.*;
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

    @Value("${cloud.aws.end-point.uri}")
    private String queue;

    @Autowired
    private AmazonSQS amazonSQS;

    @GetMapping("/send/{message}")
    public String sendMessage(@PathVariable String message) {
        Map<String, MessageAttributeValue> messageAttributes = new HashMap<>();
        messageAttributes.put("AttributeOne", new MessageAttributeValue()
                .withStringValue("This is an attribute")
                .withDataType("String"));

        SendMessageRequest sendMessageStandardQueue = new SendMessageRequest()
                .withQueueUrl(queue)
                .withMessageBody(message)
                .withDelaySeconds(5)
                .withMessageAttributes(messageAttributes);

        amazonSQS.sendMessage(sendMessageStandardQueue);
        return "sent";
    }

    @GetMapping("/receive")
    public List<Message> receiveMessage() {
        ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(queue)
                .withWaitTimeSeconds(10)
                .withMaxNumberOfMessages(10);

        List<Message> sqsMessages = amazonSQS.receiveMessage(receiveMessageRequest).getMessages();

        for (Message m : sqsMessages) {
            amazonSQS.deleteMessage(queue, m.getReceiptHandle());
        }

        return sqsMessages;
    }
    @GetMapping("/purge")
    public PurgeQueueResult purgeQueue() {
        return amazonSQS.purgeQueue(new PurgeQueueRequest(queue));
    }

}

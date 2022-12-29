package io.newAge.awsProject.sqs.service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SqsService {

    @Value("${cloud.aws.end-point.uri}")
    private String queue;

    @Autowired
    private AmazonSQS amazonSQS;


    public String sendSqsMessage(String message) {
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

    public List<Message> receiveSqsMessage() {
        List<Message> sqsMessages = receiveMessages();
        deleteMessages(sqsMessages);

        return sqsMessages;
    }

    public List<Message> receiveMessages() {
        ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(queue)
                .withWaitTimeSeconds(10)
                .withMaxNumberOfMessages(10);

        return amazonSQS.receiveMessage(receiveMessageRequest).getMessages();
    }

    public void deleteMessages(List<Message> sqsMessages) {
        for (Message m : sqsMessages) {
            amazonSQS.deleteMessage(queue, m.getReceiptHandle());
        }
    }

    public PurgeQueueResult purgeSqsQueue() {
        return amazonSQS.purgeQueue(new PurgeQueueRequest(queue));
    }
}

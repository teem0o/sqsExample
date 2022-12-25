package io.newAge.awsProject.sqs;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class SQSExample {

    static String ACCESS_KEY = "AKIAUJXBWFL354A23ETK";
    static String SECRET_KEY = "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";

    @Test
    public void SendReceive() {

//        Credentials
        AWSCredentials awsCredentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
        AmazonSQS sqsClient = AmazonSQSClient.builder().withRegion(Regions.US_EAST_1)
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials)).build();

//        Create Queue
//        String queueValue = createQueue(sqsClient);
        String queueValue="https://sqs.us-east-1.amazonaws.com/295752313591/QueueTest3";

//        Send Message
        sendMessage(sqsClient, queueValue);

//        Receive Message
        List<String> messages = receiveMessage(sqsClient, queueValue);
        messages.forEach(System.out::println);

//        Clear Queue
//        sqsClient.purgeQueue(new PurgeQueueRequest(queueValue));
    }


    private static String createQueue(AmazonSQS sqsClient) {
        CreateQueueRequest createQueueRequest = new CreateQueueRequest("QueueTest3");
        CreateQueueResult queueResult = sqsClient.createQueue(createQueueRequest);
        System.out.println(queueResult.getQueueUrl());
        return queueResult.getQueueUrl();
    }

    private static void sendMessage(AmazonSQS sqsClient, String queueValue) {
        SendMessageRequest request = new SendMessageRequest();
        String body = "message : " + System.currentTimeMillis();
        request.setMessageBody(body);
        request.setQueueUrl(queueValue);
        sqsClient.sendMessage(request);
    }

    private static List<String> receiveMessage(AmazonSQS sqsClient, String queueValue) {
        ReceiveMessageRequest receive = new ReceiveMessageRequest().withMaxNumberOfMessages(5)
                .withQueueUrl(queueValue);
        ReceiveMessageResult result = sqsClient.receiveMessage(receive);
        return result.getMessages().stream().map(Message::getBody).toList();
    }
}

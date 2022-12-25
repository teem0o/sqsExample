package io.newAge.awsProject.sqs;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.ListQueuesResult;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.newAge.awsProject.sqs.SqsApplication.ACCESS_KEY;
import static io.newAge.awsProject.sqs.SqsApplication.SECRET_KEY;

@SpringBootTest
class PrintAllQueues {

	@Test
	void printQueues() {
		AWSCredentials awsCredentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);

		AmazonSQS sqsClient = AmazonSQSClient.builder().withRegion(Regions.US_EAST_1)
				.withCredentials(new AWSStaticCredentialsProvider(awsCredentials)).build();

		ListQueuesResult lq_result = sqsClient.listQueues();
		System.out.println("Your SQS Queue URLs:");
		for (String url : lq_result.getQueueUrls()) {
			System.out.println(url);
		}
	}

}

/**
 * 
 */
package edu.nyu.cloud.sqs;

import java.util.List;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.ListQueuesResult;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;

/**
 * @author rahulkhanna Date:02-May-2016
 */
public class SQSListner {

	private final AWSCredentials credentials;
	private final AmazonSQS sqsInstance;

	public AWSCredentials getCredentials() {
		return credentials;
	}

	public AmazonSQS getSqsInstance() {
		return sqsInstance;
	}

	/**
	 * 
	 */
	public SQSListner() {
		credentials = new ProfileCredentialsProvider("default").getCredentials();
		sqsInstance = new AmazonSQSClient(credentials);
	}

	public void listen() {
		while (!Thread.currentThread().isInterrupted()) {
			String queueUrl = null;
			Message message = null;
			try {
				queueUrl = getSqsInstance().listQueues("RIDE_CREATION_QUEUE").getQueueUrls().get(0);
				ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(queueUrl)
						.withMaxNumberOfMessages(1);
				message = getSqsInstance().receiveMessage(receiveMessageRequest).getMessages().get(0);
				System.out.println(message.getBody());
			} catch (Exception ex) {
				System.out.printf("%s\n", ex.getMessage());
				Thread.currentThread().interrupt();
			} finally {
				if (message != null && queueUrl != null) {
					getSqsInstance().deleteMessage(queueUrl, message.getReceiptHandle());
				}
			}
		}
	}

}

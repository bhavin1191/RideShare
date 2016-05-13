/**
 * 
 */
package edu.nyu.cloud.sqs;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.google.gson.Gson;

import edu.nyu.cloud.service.beans.IncomingPoolRequest;

/**
 * @author rahulkhanna Date:03-May-2016
 */
public class SQSJobSubmitter {

	private final String accessKey;
	private final String secretKey;
	
	/**
	 * @param accessKey
	 * @param secretKey
	 */
	public SQSJobSubmitter(String accessKey, String secretKey) {
		super();
		this.accessKey = accessKey;
		this.secretKey = secretKey;
	}

	public void sendJobRequest(IncomingPoolRequest request) {
		Gson gson = new Gson();
		String poolRequest = gson.toJson(request);
        AmazonSQSClient sqs = new AmazonSQSClient(new BasicAWSCredentials(accessKey, secretKey));
        String queue = sqs.listQueues("myqueue").getQueueUrls().get(0);
		sqs.sendMessage(new SendMessageRequest()
        	    .withQueueUrl(queue)
        	    .withMessageBody(poolRequest));
	}

	public String getAccessKey() {
		return accessKey;
	}

	public String getSecretKey() {
		return secretKey;
	}

}

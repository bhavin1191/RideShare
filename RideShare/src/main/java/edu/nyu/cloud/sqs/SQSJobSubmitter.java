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

	private final String accessToken;
	private final String accessSecret;
	private final String consumerKey;
	private final String consumerSecret;

	public String getAccessToken() {
		return accessToken;
	}

	public String getAccessSecret() {
		return accessSecret;
	}

	public String getConsumerKey() {
		return consumerKey;
	}

	public String getConsumerSecret() {
		return consumerSecret;
	}

	/**
	 * Constructor
	 * 
	 * @param accessToken
	 * @param accessSecret
	 * @param consumerKey
	 * @param consumerSecret
	 */
	public SQSJobSubmitter(String accessToken, String accessSecret, String consumerKey, String consumerSecret) {
		this.accessToken = accessToken;
		this.accessSecret = accessSecret;
		this.consumerKey = consumerKey;
		this.consumerSecret = consumerSecret;
	}

	public void sendJobRequest(IncomingPoolRequest request) {
		Gson gson = new Gson();
		String poolRequest = gson.toJson(request);
        AmazonSQSClient sqs = new AmazonSQSClient(new BasicAWSCredentials("", ""));
        String queue = sqs.listQueues("myqueue").getQueueUrls().get(0);
		sqs.sendMessage(new SendMessageRequest()
        	    .withQueueUrl(queue)
        	    .withMessageBody(poolRequest));
	}

}

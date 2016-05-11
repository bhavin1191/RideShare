package edu.nyu.cloud.service.beans;
import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.simpleemail.AWSJavaMailTransport;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;
import com.amazonaws.services.simpleemail.model.ListVerifiedEmailAddressesResult;
import com.amazonaws.services.simpleemail.model.VerifyEmailAddressRequest;

	public class EmailService {

		/*
	     * Important: Be sure to fill in an email address you have access to
	     *            so that you can receive the initial confirmation email
	     *            from Amazon Simple Email Service.
	     */
	    private static final String FROM = "kirtika.suren@gmail.com";
	    private static final String SUBJECT = "Ride Info Update!";
        private static AWSCredentials credentials = null; 
	    
	    private static void authenticate()
	    {
	    	
	    	try 
	    	{
	    		credentials = new ProfileCredentialsProvider("default").getCredentials();
	    	} catch (Exception e) 
	    	{
	    		throw new AmazonClientException("Cannot load the credentials from the credential profiles file. "
	    				+ "Please make sure that your credentials file is at the correct "
	    				+ "location (C:\\Users\\User\\.aws\\credentials), and is in valid format.", e);
	    	}
	        AmazonSimpleEmailService ses = new AmazonSimpleEmailServiceClient(credentials);

	        /*
	         * Before you can send email via Amazon SES, you need to verify that you
	         * own the email address from which you’ll be sending email. This will
	         * trigger a verification email, which will contain a link that you can
	         * click on to complete the verification process.
	         */
	         verifyEmailAddress(ses, FROM);
	         /*
		         * If you've just signed up for SES, then you'll be placed in the Amazon
		         * SES sandbox, where you must also verify the email addresses you want
		         * to send mail to.
		         *
		         * You can uncomment the line below to verify the TO address in this
		         * sample.
		         *
		         * Once you have full access to Amazon SES, you will *not* be required
		         * to verify each email address you want to send mail to.
		         *
		         * You can request full access to Amazon SES here:
		         * http://aws.amazon.com/ses/fullaccessrequest
		         */
		         //verifyEmailAddress(ses, TO);
	    }
	    public void sendEmail(String toAddress,String body)
	    {
	    	authenticate();
	        Properties props = new Properties();
			props.setProperty("mail.transport.protocol", "aws");

	        /*
	         * Setting mail.aws.user and mail.aws.password are optional. Setting
	         * these will allow you to send mail using the static transport send()
	         * convince method.  It will also allow you to call connect() with no
	         * parameters. Otherwise, a user name and password must be specified
	         * in connect.
	         */
	        props.setProperty("mail.aws.user", credentials.getAWSAccessKeyId());
	        props.setProperty("mail.aws.password", credentials.getAWSSecretKey());

	        Session session = Session.getInstance(props);

	        try {
	            // Create a new Message
	            Message msg = new MimeMessage(session);
	            msg.setFrom(new InternetAddress(FROM));
	            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(toAddress));
	            msg.setSubject(SUBJECT);
	            msg.setText(body);
	            msg.saveChanges();

	            // Reuse one Transport object for sending all your messages
	            // for better performance
	            Transport t = new AWSJavaMailTransport(session, null);
	            t.connect();
	            t.sendMessage(msg, null);

	            // Close your transport when you're completely done sending
	            // all your messages
	            t.close();
	        } catch (AddressException e) {
	            e.printStackTrace();
	            System.out.println("Caught an AddressException, which means one or more of your "
	                    + "addresses are improperly formatted.");
	        } catch (MessagingException e) {
	            e.printStackTrace();
	            System.out.println("Caught a MessagingException, which means that there was a "
	                    + "problem sending your message to Amazon's E-mail Service check the "
	                    + "stack trace for more information.");
	        }
	    }
	    public static void main(String[] args) throws IOException {
	        //EmailService.SendEmail("kirtika.suren@gmail.com","Your driver is within 3 min of arrival.");
	    	
	    }

	    /**
	     * Sends a request to Amazon Simple Email Service to verify the specified
	     * email address. This triggers a verification email, which will contain a
	     * link that you can click on to complete the verification process.
	     *
	     * @param ses
	     *            The Amazon Simple Email Service client to use when making
	     *            requests to Amazon SES.
	     * @param address
	     *            The email address to verify.
	     */
	    private static void verifyEmailAddress(AmazonSimpleEmailService ses, String address) {
	        ListVerifiedEmailAddressesResult verifiedEmails = ses.listVerifiedEmailAddresses();
	        if (verifiedEmails.getVerifiedEmailAddresses().contains(address)) return;

	        ses.verifyEmailAddress(new VerifyEmailAddressRequest().withEmailAddress(address));
	        System.out.println("Please check the email address " + address + " to verify it");
	        System.exit(0);
	    }
	}


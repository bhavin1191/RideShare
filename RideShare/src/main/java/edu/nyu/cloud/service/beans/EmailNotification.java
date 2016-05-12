package edu.nyu.cloud.service.beans;

import edu.nyu.cloud.beans.UserProfile;
import edu.nyu.cloud.cache.UserCache;

public class EmailNotification {

	private final EmailService emailService;
	private final UserCache userCache;

	public EmailNotification(EmailService emailService, UserCache userCache) {
		this.emailService = emailService;
		this.userCache = userCache;
	}

	public void sendConfirmationEmail(String userId, String emailBody) {
		UserProfile userProfileObj = userCache.getUserByUserName(userId);
		String toAddress = userProfileObj.getEmailAddress();
		emailService.sendEmail(toAddress, emailBody);
	}

}

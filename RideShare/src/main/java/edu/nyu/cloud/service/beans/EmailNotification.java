package edu.nyu.cloud.service.beans;

import edu.nyu.cloud.beans.UserProfile;
import edu.nyu.cloud.user.dao.db.UserDao;
import edu.nyu.cloud.user.dao.db.hibernate.UserDaoImpl;

public class EmailNotification 
{
	private final EmailService emailService;
	private final UserDao userDao;

	public EmailNotification(EmailService emailService,UserDao userDao) {
		this.emailService = emailService;
		this.userDao = userDao; 
	}

	public void sendConfirmationEmail(String userId, String emailBody) {
	    UserProfile userProfileObj =  userDao.getUserProfileByUserId(userId); //Get User's email
		// address from UserProfile table using id.
	   String toAddress = userProfileObj.getEmailAddress();
	   emailService.sendEmail(toAddress, emailBody);

	}

}

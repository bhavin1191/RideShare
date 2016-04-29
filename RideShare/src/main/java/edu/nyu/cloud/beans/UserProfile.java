package edu.nyu.cloud.beans;

/**
 * This class is used to capture user information which will be captured during
 * sign-up.
 * 
 * @author rahul.khanna Date:28-Mar-2016
 */
public class UserProfile {

	private long id;
	private String userName;
	private String userPassword;
	private String userAddress;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String sex;
	private String aboutMe;
	private int numberOfThumbsUp;
	private int numberOfThumbsDown;

	public void setId(long id) {
		this.id = id;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public void setAboutMe(String aboutMe) {
		this.aboutMe = aboutMe;
	}

	public void setNumberOfThumbsUp(int numberOfThumbsUp) {
		this.numberOfThumbsUp = numberOfThumbsUp;
	}

	public void setNumberOfThumbsDown(int numberOfThumbsDown) {
		this.numberOfThumbsDown = numberOfThumbsDown;
	}

	/**
	 * Default constructor
	 */
	public UserProfile() {
		this(0, null, null, null, null, null, null, null,null);
	}

	/**
	 * Constructor
	 * 
	 * @param userName
	 * @param userPassword
	 * @param userAddress
	 * @param firstName
	 * @param lastName
	 * @param phoneNumber
	 */
	public UserProfile(int id, String userName, String userPassword, String userAddress, String firstName,
			String lastName, String phoneNumber, String aboutMe,String sex) {
		super();
		this.id = id;
		this.userName = userName;
		this.userPassword = userPassword;
		this.userAddress = userAddress;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.aboutMe = aboutMe;
		this.sex = sex;
	}
	

	public String getSex() {
		return sex;
	}

	public int getNumberOfThumbsUp() {
		return numberOfThumbsUp;
	}

	public int incrementNumberOfThumsUp() {
		return numberOfThumbsDown += 1;
	}

	public int getNumberOfThumbsDown() {
		return numberOfThumbsDown;
	}

	public int incrementNumberOfThumbsDown() {
		return numberOfThumbsDown -= 1;
	}

	public String getAboutMe() {
		return aboutMe;
	}

	public String getUserName() {
		return userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public String getUserAddress() {
		return userAddress;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

}

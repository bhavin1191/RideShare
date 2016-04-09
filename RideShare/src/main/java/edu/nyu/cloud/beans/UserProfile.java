package firsthibernate;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="USER_PROFILE")
public class UserProfile {

	@Id	
	int id;
	private String userName;
	private String userPassword;
	private String userAddress;
	private String firstName;
	private String lastName;
	private String phoneNumber;

/**
 * This class is used to capture user information which will be captured during
 * sign-up.
 * 
 * @author rahul.khanna Date:28-Mar-2016
 */
public class UserProfile {

	private final int id;
	private final String userName;
	private final String userPassword;
	private final String userAddress;
	private final String firstName;
	private final String lastName;
	private final String phoneNumber;
	private final String sex;
	private final String aboutMe;
	private int numberOfThumbsUp;
	private int numberOfThumbsDown;

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

	
	public UserProfile(int id,String userName, String userPassword, String userAddress, String firstName, String lastName,
			String phoneNumber) {

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

		
	}
	
	public UserProfile()
	{
		
	}


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
	public int getId() {
		return id;
	}

	
}


}


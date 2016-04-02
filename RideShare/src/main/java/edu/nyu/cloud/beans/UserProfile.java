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
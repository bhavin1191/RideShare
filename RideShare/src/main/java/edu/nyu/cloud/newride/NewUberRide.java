package edu.nyu.cloud.newride;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.util.store.AbstractDataStoreFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.uber.sdk.rides.auth.OAuth2Credentials;
import com.uber.sdk.rides.client.Response;
import com.uber.sdk.rides.client.Session;
import com.uber.sdk.rides.client.UberRidesServices;
import com.uber.sdk.rides.client.UberRidesSyncService;
import com.uber.sdk.rides.client.error.ApiException;
import com.uber.sdk.rides.client.error.NetworkException;
import com.uber.sdk.rides.client.model.Product;
import com.uber.sdk.rides.client.model.ProductsResponse;
import com.uber.sdk.rides.client.model.Ride;
import com.uber.sdk.rides.client.model.RideRequestParameters;
import com.uber.sdk.rides.client.model.SandboxRideRequestParameters;

import edu.nyu.cloud.beans.UberRide;

/**
 * Demonstrates how to authenticate the user and load their profile via the
 * command line.
 */
public class NewUberRide {
	private LocalServerReceiver localServerReceiver;
	private UberRidesSyncService uberRidesService = null;
	private Credential credential = null;
	private Session session;
	public NewUberRide() throws Exception
	{
		credential = authenticate(System.getProperty("user.name"));
		// LogManager.getLogManager().reset();
		// Create session for the user
		session = new Session.Builder().setCredential(credential).setEnvironment(Session.Environment.SANDBOX)
				.build();
		// Create the Uber API service object once the User is authenticated
		uberRidesService = getApiInstance(session);
	}

	public List<UberRide> confirmRide(String source, String destination, int no_of_person)
			throws ApiException, IOException {
		List<UberRide> uberridedetails = new ArrayList<UberRide>();
		String rideId=null;
		try {
			String[] srclatlng = source.split(",");
			String[] destlatlng = destination.split(",");

			int submittedCapacity = no_of_person;

			List<Product> products = getProductList(uberRidesService, Float.parseFloat(srclatlng[0]),
					Float.parseFloat(srclatlng[1]));
			String productId = null;
			for (Product p : products) {
				if (p.getCapacity() == submittedCapacity) {

					productId = p.getProductId();
					rideId = requestRide(productId, Float.parseFloat(srclatlng[0]),
							Float.parseFloat(srclatlng[1]), Float.parseFloat(destlatlng[0]),
							Float.parseFloat(destlatlng[1]));

					System.out.printf("Product ID %s%n", productId);
					System.out.printf("Ride ID %s%n", rideId);

					SandboxRideRequestParameters rideParameters = new SandboxRideRequestParameters.Builder()
							.setStatus("accepted").build();
					Response<Void> response = uberRidesService.updateSandboxRide(rideId, rideParameters);
					System.out.println("status:"+ response.getStatus());
					//uberridedetails.addAll(getRideDetails(rideId));
					Ride listRide = getRideStatus(rideId);
					UberRide uberride = new UberRide(p.getDisplayName(),p.getProductId(),rideId,p.getCapacity(),listRide.getDriver().getName(),listRide.getDriver().getPhoneNumber(),listRide.getEta(),listRide.getVehicle().getPictureUrl());
					uberridedetails.add(uberride);

					break;
				}
			}

			// Request an Uber ride by giving the GPS coordinates for pickup and
			// drop-off.
		} catch (Exception e) {
			e.printStackTrace();
		}
		return uberridedetails;
	}

	public List<UberRide> getRideDetails(String rideId)
	{
		List<UberRide> uberridedetails = new ArrayList<UberRide>();
		Ride listRide = getRideStatus(rideId);
		UberRide uberride = new UberRide(null,null,rideId,0,listRide.getDriver().getName(),listRide.getDriver().getPhoneNumber(),listRide.getEta(),listRide.getVehicle().getPictureUrl());
		uberridedetails.add(uberride);
		return uberridedetails;
	}

	public int cancelRequest(String rideId) throws ApiException, NetworkException
	{
		uberRidesService.cancelRide(rideId);
		SandboxRideRequestParameters rideParameters = new SandboxRideRequestParameters.Builder()
				.setStatus("reject").build();
		Response<Void> response = uberRidesService.updateSandboxRide(rideId, rideParameters);
		return response.getStatus();
	}

	private Ride getRideStatus(String rideId) {
		Response<Ride> rideStatus = null;
		try {
			rideStatus = uberRidesService.getRideDetails(rideId);

		} catch (ApiException e) {
			e.printStackTrace();
		} catch (NetworkException e) {
			e.printStackTrace();
		}
		Ride listRide = rideStatus.getBody();
		return listRide;
	}

	private String requestRide(String productId1, Float startLat, Float startLong,
			Float endLat, Float endLong) {
		RideRequestParameters rideRequestParams = new RideRequestParameters.Builder()
				.setPickupCoordinates(startLat, startLong).setProductId(productId1)
				.setDropoffCoordinates(endLat, endLong).build();
		Ride rideInfo = null;
		try {
			rideInfo = uberRidesService.requestRide(rideRequestParams).getBody();
		} catch (ApiException e) {

			e.printStackTrace();
		} catch (NetworkException e) {

			e.printStackTrace();
		}
		String rideId1 = rideInfo.getRideId();

		return rideId1;
	}

	private UberRidesSyncService getApiInstance(Session session) {
		if (uberRidesService == null) {
			uberRidesService = UberRidesServices.createSync(session);
		}
		return uberRidesService;
	}

	public List<Product> getProductList(UberRidesSyncService uberApi, Float Lat, Float Long) {
		ProductsResponse productsResponse = null;
		try {
			productsResponse = uberApi.getProducts(Lat, Long).getBody();
		} catch (ApiException e) {
			e.printStackTrace();
		} catch (NetworkException e) {
			e.printStackTrace();
		}
		List<Product> products = productsResponse.getProducts();
		return products;
	}

	/**
	 * Authenticate the given user. If you are distributing an installed
	 * application, this method should exist on your server so that the client
	 * ID and secret are not shared with the end user.
	 */
	private Credential authenticate(String userId) throws Exception {

		OAuth2Credentials oAuth2Credentials = createOAuth2Credentials();
		System.out.println(userId);
		// First try to load an existing Credential. If that credential is null,
		// authenticate the user.
		Credential credential = oAuth2Credentials.loadCredential(userId);
		if (credential == null || credential.getAccessToken() == null) {
			// Send user to authorize your application.
			System.out.printf("Add the following redirect URI to your developer.uber.com application: %s%n",
					oAuth2Credentials.getRedirectUri());
			System.out.println("Press Enter when done.");

			System.in.read();

			// Generate an authorization URL.
			String authorizationUrl = oAuth2Credentials.getAuthorizationUrl();
			System.out.printf("In your browser, navigate to: %s%n", authorizationUrl);
			System.out.println("Waiting for authentication...");

			// Wait for the authorization code.
			String authorizationCode = localServerReceiver.waitForCode();
			System.out.println("Authentication received.");
			localServerReceiver.stop();
			// Authenticate the user with the authorization code.
			credential = oAuth2Credentials.authenticate(authorizationCode, userId);
		}
		localServerReceiver.stop();
		return credential;
	}

	/**
	 * Creates an {@link OAuth2Credentials} object that can be used by any of
	 * the servlets.
	 */
	private OAuth2Credentials createOAuth2Credentials() throws Exception {
		// Load the client ID and secret from {@code
		// resources/secrets.properties}. Ideally, your
		// secrets would not be kept local. Instead, have your server accept the
		// redirect and return
		// you the accessToken for a userId.
		Properties secrets = loadSecretProperties();

		String clientId = secrets.getProperty("clientId");
		String clientSecret = secrets.getProperty("clientSecret");

		if (clientId.isEmpty() || clientSecret.isEmpty()) {
			throw new IllegalArgumentException(
					"Please enter your client ID and secret in the resources/secrets.properties file.");
		}

		// Store the users OAuth2 credentials in their home directory.
		File credentialDirectory = new File(System.getProperty("user.home") + File.separator + ".uber_credentials");
		credentialDirectory.setReadable(true, true);
		credentialDirectory.setWritable(true, true);
		// If you'd like to store them in memory or in a DB, any
		// DataStoreFactory can be used.
		AbstractDataStoreFactory dataStoreFactory = new FileDataStoreFactory(credentialDirectory);

		// Start a local server to listen for the OAuth2 redirect.
		if (localServerReceiver == null) {
			localServerReceiver = new LocalServerReceiver.Builder().setPort(8181).build();
		} else {
			localServerReceiver.stop();
			localServerReceiver = new LocalServerReceiver.Builder().setPort(8181).build();
		}
		String redirectUri = localServerReceiver.getRedirectUri();

		// Build an OAuth2Credentials object with your secrets.
		return new OAuth2Credentials.Builder().setCredentialDataStoreFactory(dataStoreFactory)
				.setRedirectUri(redirectUri).setClientSecrets(clientId, clientSecret).build();
	}

	/**
	 * Loads the application's secrets.
	 */
	private Properties loadSecretProperties() throws Exception {
		Properties properties = new Properties();
		InputStream propertiesStream = NewUberRide.class.getClassLoader().getResourceAsStream("secrets.properties");
		if (propertiesStream == null) {
			// Fallback to file access in the case of running from certain IDEs.
			File buildPropertiesFile = new File("src/main/resources/secrets.properties");
			if (buildPropertiesFile.exists()) {
				properties.load(new FileReader(buildPropertiesFile));
			} else {
				throw new IllegalStateException("Could not find secrets.properties");
			}
		} else {
			properties.load(propertiesStream);
		}
		return properties;
	}

}

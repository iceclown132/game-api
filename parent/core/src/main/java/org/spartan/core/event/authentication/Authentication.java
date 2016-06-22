package org.spartan.core.event.authentication;

import org.spartan.model.user.attribute.Credentials;
import org.spartan.model.user.attribute.Detail;
import org.spartan.model.user.attribute.Version;
import org.spartan.net.security.ISAACPair;

/**
 * The authentication block
 * 
 * @author brock
 */
public class Authentication {

	/**
	 * The request id used to identify what action the player wants to perform
	 * during handshake
	 */
	public static final int REQUEST_ID = 14;

	/**
	 * The username and password
	 */
	private Credentials credentials;

	/**
	 * The client uuid
	 */
	private int uuid;
	
	/**
	 * The ISAAC key pair
	 */
	private ISAACPair isaacPair;

	/**
	 * The detail version the user is playing on
	 */
	private Detail detail;
	
	/**
	 * Indicates the authentication is a reconnect rather than a new connect
	 */
	private boolean reconnect;

	/**
	 * The version of the client
	 */
	private Version version;

	/**
	 * @return the version
	 */
	public Version getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(Version version) {
		this.version = version;
	}

	/**
	 * @return the uuid
	 */
	public int getUuid() {
		return uuid;
	}

	/**
	 * @param uuid the uuid to set
	 */
	public void setUuid(int uuid) {
		this.uuid = uuid;
	}

	/**
	 * @return the isaacPair
	 */
	public ISAACPair getIsaacPair() {
		return isaacPair;
	}

	/**
	 * @param isaacPair the isaacPair to set
	 */
	public void setIsaacPair(ISAACPair isaacPair) {
		this.isaacPair = isaacPair;
	}

	/**
	 * @return the detail
	 */
	public Detail getDetail() {
		return detail;
	}

	/**
	 * @param detail the detail to set
	 */
	public void setDetail(Detail detail) {
		this.detail = detail;
	}

	/**
	 * @return the reconnect
	 */
	public boolean isReconnect() {
		return reconnect;
	}

	/**
	 * @param reconnect the reconnect to set
	 */
	public void setReconnect(boolean reconnect) {
		this.reconnect = reconnect;
	}

	/**
	 * @return the credentials
	 */
	public Credentials getCredentials() {
		return credentials;
	}

	/**
	 * @param credentials the credentials to set
	 */
	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}

	/**
	 * @param username
	 * @param password
	 */
	public void setCredentials(String username, String password) {
		this.setCredentials(new Credentials(username, password));
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Authentication [credentials=" + credentials + ", uuid=" + uuid + ", isaacPair=" + isaacPair
				+ ", detail=" + detail + ", reconnect=" + reconnect + ", version=" + version + "]";
	}

}
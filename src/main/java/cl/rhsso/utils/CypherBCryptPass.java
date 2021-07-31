package cl.rhsso.utils;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class CypherBCryptPass {
	
	private static int workload = 12;
	
	private CypherBCryptPass() {
		throw new IllegalStateException("CypherBCryptPass class");
	}
	
	public static String hashPassword(String passwordPlaintext) {
		String salt = BCrypt.gensalt(workload);
		String hashedPassword = BCrypt.hashpw(passwordPlaintext, salt);

		return(hashedPassword);
	}
	
	public static boolean checkPassword(String passwordPlaintext, String storedHash) {
		boolean passwordVerified = false;

		if(null == storedHash || !storedHash.startsWith("$2a$"))
			throw new java.lang.IllegalArgumentException("Invalid hash provided for comparison");

		passwordVerified = BCrypt.checkpw(passwordPlaintext, storedHash);

		return(passwordVerified);
	}	
}

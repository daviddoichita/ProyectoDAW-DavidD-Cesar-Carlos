package ies.camp.guardias.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncriptaPassword {
	
	public static String encriptarPassword(String password) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(password);
	}
}
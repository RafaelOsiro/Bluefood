package io.ucb.rafael.bluefood.util;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

public class StringUtils {
	
	public static boolean funcIsEmpty(String str) {
		if (str == null) {
			return true;
		}
		
		return str.trim().length() == 0;
	}
	
	public static String funcEncrypt(String rawString) {
		if (funcIsEmpty(rawString)) {
			return null;
		}
		
		PasswordEncoder enconder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		return enconder.encode(rawString);
	}
}

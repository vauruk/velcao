package br.com.vanderson.app;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class StringUtil {
	public static String criptografarPassword(String password)  {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			BigInteger hash = new BigInteger(1, md.digest(password.getBytes()));
			return String.format("%32x", hash);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}
}

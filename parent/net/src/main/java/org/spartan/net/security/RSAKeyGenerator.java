package org.spartan.net.security;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.util.stream.Collectors;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.spartan.cdi.util.resource.ClassPathResourceHandler;
import org.spartan.cdi.util.resource.ResourceHandler;

public class RSAKeyGenerator {

	/**
	 * Resource handler to fetch class path resources
	 */
	private static final ResourceHandler resources = new ClassPathResourceHandler();

	/**
	 * Use this to generate a new RSA key pair
	 * 
	 * @param args
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 */
	public static void main(String[] args) throws FileNotFoundException, IOException, NoSuchAlgorithmException, NoSuchProviderException {
		Security.addProvider(new BouncyCastleProvider());
		KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", "BC");
		generator.initialize(1024);
		KeyPair keyPair = generator.generateKeyPair();
		System.out.println(keyPair.getPrivate());
		System.out.println(keyPair.getPublic());
	}

	/**
	 * 
	 * @param modulus_file
	 * @param exponent_file
	 * @return
	 * @throws Exception
	 */
	public static RSA from(String modulus_file, String exponent_file) throws Exception {
		BigInteger modulus = readFile(modulus_file);
		BigInteger exponent = readFile(exponent_file);
		
		return new RSA(modulus, exponent, null);
	}

	/**
	 * 
	 * @param path
	 * @return
	 * @throws Exception
	 */
	private static BigInteger readFile(String path) throws Exception {
		return new BigInteger(Files.readAllLines(resources.get(path)).stream().collect(Collectors.joining()), 16);
	}

}

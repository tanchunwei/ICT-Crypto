package util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

public class CryptoUtil {
	public static final int READ_AMOUNT = 20000 *1024;
	
	public static void encryptFilePassword(String srcFile, String desFile, String pwd)throws Exception{
		PBEKeySpec pbeKeySpec;
	    PBEParameterSpec pbeParamSpec;
	    SecretKeyFactory keyFac;

	    // Salt
	    byte[] salt = {(byte)0x9f, (byte)0x33, (byte)0x4e, (byte)0xfe, (byte)0xd4, (byte)0xee, (byte)0x12, (byte)0x54};

	    // Iteration count
	    int count = 17;

	    // Create PBE parameter set
	    pbeParamSpec = new PBEParameterSpec(salt, count);
	    char[] password = new char [pwd.length()];
	    for(int i = 0; i < pwd.length(); i++){
	    	password[i] = pwd.charAt(i);
	    }

	    pbeKeySpec = new PBEKeySpec(password);
	    keyFac = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
	    SecretKey pbeKey = keyFac.generateSecret(pbeKeySpec);

	    // Create PBE Cipher
	    Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");

	    // Initialize PBE Cipher with key and parameters
	    pbeCipher.init(Cipher.ENCRYPT_MODE, pbeKey, pbeParamSpec);
	    //System.out.println("Encrypting file using DES/ECB/PKCS5Padding");
	    
	  

	   // System.out.println("Reading cleartext file and encrypting...");
        BufferedOutputStream outData = new BufferedOutputStream(new FileOutputStream(desFile));
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(srcFile));
        
        while (in.available() > 0) {
        	// Read the next chunk of bytes...
        	if(in.available() >= READ_AMOUNT){
	        	byte[] cleartextBytes = new byte[READ_AMOUNT];
				in.read(cleartextBytes);
					// Now, encrypt them and write them to the encrypted file...
				byte[] encryptedBytes = pbeCipher .update(cleartextBytes);
				outData.write(encryptedBytes);
        	}else{
        		byte[] cleartextBytes = new byte[in.available()];
				in.read(cleartextBytes);
					// Now, encrypt them and write them to the encrypted file...
				byte[] encryptedBytes = pbeCipher .update(cleartextBytes);
				outData.write(encryptedBytes);
        	}
/*			for(int offset = 0; offset <=  in.available(); offset += READ_AMOUNT){
				//System.out.print(in.available() + " ");
				if(in.available() >= (offset + READ_AMOUNT)){
					System.out.println("if read amount " + offset);
					byte[] cleartextBytes = new byte[READ_AMOUNT];
					in.read(cleartextBytes, offset, READ_AMOUNT  + offset);
					byte[] encryptedBytes = pbeCipher .update(cleartextBytes);
					outData.write(encryptedBytes, offset, in.available());
				}else{
					System.out.println("else read amount " + (in.available() - offset));
					byte[] cleartextBytes = new byte[in.available() - offset];
					in.read(cleartextBytes, offset, in.available() - offset);
					byte[] encryptedBytes = pbeCipher .update(cleartextBytes);
					outData.write(encryptedBytes, offset, in.available());
				}
				
			}*/

        } // while
        // Take care of any pending padding operations
        outData.write(pbeCipher .doFinal());
        in.close();
        outData.flush();
        outData.close();
        
        //System.out.println("Done!");
	}
	
	public static void decryptFilePassword(String srcFile, String desFile, String pwd)throws Exception{
		PBEKeySpec pbeKeySpec;
		PBEParameterSpec pbeParamSpec;
		SecretKeyFactory keyFac;

		// Salt
		byte[] salt = {(byte) 0x9f, (byte) 0x33, (byte) 0x4e, (byte) 0xfe, (byte) 0xd4, (byte) 0xee, (byte) 0x12, (byte) 0x54};

		// Iteration count
		int count = 17;

		// Create PBE parameter set
		pbeParamSpec = new PBEParameterSpec(salt, count);
		char[] password = new char [pwd.length()];
	    for(int i = 0; i < pwd.length(); i++){
	    	password[i] = pwd.charAt(i);
	    }

		pbeKeySpec = new PBEKeySpec(password);
		keyFac = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
		SecretKey pbeKey = keyFac.generateSecret(pbeKeySpec);

		// Create PBE Cipher
		Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");

		// Initialize PBE Cipher with key and parameters
		pbeCipher.init(Cipher.DECRYPT_MODE, pbeKey, pbeParamSpec);
		//System.out.println("Encrypting file using DES/ECB/PKCS5Padding");

		//System.out.println("Reading encrypted file and decrypting...");
		BufferedOutputStream outData = new BufferedOutputStream(new FileOutputStream(desFile));
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(srcFile));
		while (in.available() > 0) {
			if(in.available() >= READ_AMOUNT){
				// Read the next chunk of bytes...
				byte[] cleartextBytes = new byte[READ_AMOUNT];
				in.read(cleartextBytes);
				// Now, encrypt them and write them to the encrypted file...
				byte[] encryptedBytes = pbeCipher.update(cleartextBytes);
				outData.write(encryptedBytes);
			}else{
				// Read the next chunk of bytes...
				byte[] cleartextBytes = new byte[in.available()];
				in.read(cleartextBytes);
				// Now, encrypt them and write them to the encrypted file...
				byte[] encryptedBytes = pbeCipher.update(cleartextBytes);
				outData.write(encryptedBytes);
			}
		} // while
		// Take care of any pending padding operations
		outData.write(pbeCipher.doFinal());
		in.close();
		outData.flush();
		outData.close();

		//System.out.println("Done!");
	}
}

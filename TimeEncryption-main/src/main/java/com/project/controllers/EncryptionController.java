package com.project.controllers;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.time.LocalDateTime;
import java.util.Base64;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//Creating controller for the server-side, should change CrossOrigin as this is not the most secure way
@CrossOrigin(origins = "*")
@RestController
@EnableScheduling
public class EncryptionController {
	
	
	private String encryptedValue;
	
	
    //Create endpoint that returns the encrypted value, and make sure we log each request
    @GetMapping("/secret")
    public String getEncryptedValue() {
    	System.out.println("A request has been made");
        return encryptedValue;
    }
    
    //Using spring scheduled to create a fixed interval where we change the encryption value 
    @Scheduled(fixedRate = 60000)
    public void generateEncryptedValue() throws Exception {
        String date = LocalDateTime.now().toString();
        encryptedValue = encrypt(date);
        System.out.println("Current encrypted value is now: " + encryptedValue);
    }

    //Create the encryption - Note I have used 128-bit from a generator online, for the KeySpec
    private String encrypt(String data) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        SecretKeySpec secretKey = new SecretKeySpec(")J@NcRfUjXnZr4u7".getBytes(), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }
}

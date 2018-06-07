package org.voting.gateway.service;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.sql.Date;
import java.text.Format;
import java.text.SimpleDateFormat;

import org.springframework.stereotype.Component;
import org.voting.gateway.domain.RodoUser;
import org.voting.gateway.security.AESEncrypter;

@Component
public class RodoUserEncrypter {

	private final AESEncrypter encrypter;
	
	public RodoUserEncrypter(AESEncrypter encrypter)
	{
		this.encrypter = encrypter;
	}
	
	public RodoUser encryptUserData(RodoUserDTO userData)
	{
		RodoUser user = new RodoUser();

		try
		{
			user.setId(userData.getId());
			encrypter.initEncryptMode();
			user.setDocument_no(ByteBuffer.wrap(encrypter.encrypt(userData.getDocumentNo())));
			user.setDocument_type(ByteBuffer.wrap(encrypter.encrypt(userData.getDocumentType())));
			user.setEmail(ByteBuffer.wrap(encrypter.encrypt(userData.getEmail())));
			user.setName(ByteBuffer.wrap(encrypter.encrypt(userData.getName())));
			user.setPesel(ByteBuffer.wrap(encrypter.encrypt(userData.getPesel())));
			user.setSurname(ByteBuffer.wrap(encrypter.encrypt(userData.getSurname())));
			Format formatter = new SimpleDateFormat("yyyy-MM-dd");
			String s = formatter.format(userData.getBirthdate());
			user.setBirthdate(ByteBuffer.wrap(encrypter.encrypt(s)));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return user;
	}
	
	public RodoUserDTO decryptUserData(RodoUser userData)
	{
		RodoUserDTO user = new RodoUserDTO();
		
		try
		{
			user.setId(userData.getId());
			encrypter.initDecryptMode();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

			user.setBirthdate(formatter.parse(encrypter.decrypt(userData.getBirthdate().array())));
			user.setDocumentNo(encrypter.decrypt(userData.getDocument_no().array()));
			user.setDocumentType(encrypter.decrypt(userData.getDocument_type().array()));
			user.setEmail(encrypter.decrypt(userData.getEmail().array()));
			user.setName(encrypter.decrypt(userData.getName().array()));
			user.setPesel(encrypter.decrypt(userData.getPesel().array()));
			user.setSurname(encrypter.decrypt(userData.getSurname().array()));			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return user;
	}
}

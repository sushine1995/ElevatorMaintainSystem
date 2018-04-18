package com.vino.scaffold.shiro.service;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Service;

import com.vino.maintain.entity.Employee;
import com.vino.scaffold.shiro.entity.User;



/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-1-28
 * <p>Version: 1.0
 */
@Service("passwordHelper")
public class PasswordHelper {

    private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
    private String algorithmName = "md5";
    private int hashIterations = 2;//

    public void setRandomNumberGenerator(RandomNumberGenerator randomNumberGenerator) {
        this.randomNumberGenerator = randomNumberGenerator;
    }

    public void setAlgorithmName(String algorithmName) {
        this.algorithmName = algorithmName;
    }

    public void setHashIterations(int hashIterations) {
        this.hashIterations = hashIterations;
    }

    public void encryptPassword(User user) {
        user.setSalt(randomNumberGenerator.nextBytes().toHex());//
        String newPassword = new SimpleHash(
                algorithmName,
                user.getPassword(),
                ByteSource.Util.bytes(user.getSalt()),
                hashIterations).toHex();
        user.setPassword(newPassword);
    }

	public void encryptPassword(Employee employee) {
		// TODO Auto-generated method stub
		employee.setSalt(randomNumberGenerator.nextBytes().toHex());//

	        String newPassword = new SimpleHash(
	                algorithmName,
	                employee.getPassword(),
	                ByteSource.Util.bytes(employee.getSalt()),
	                hashIterations).toHex();

	        employee.setPassword(newPassword);
	}
   public boolean judgeEmployeePassword(String password,String realPassword,String salt){
	   if(password==null||realPassword==null||salt==null)
		   return false;
	   String encryptedPassword = new SimpleHash(
               algorithmName,
               password,
               ByteSource.Util.bytes(salt),
               hashIterations).toHex();
	   if(encryptedPassword.equals(realPassword))
		   return true;
	   else
		   return false;
   }
}

package com.hibo.cms.sys.shiro.util;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Component;

import com.hibo.cms.user.model.User;

/**
 * <p>标题：密码</p>
 * <p>功能：密码处理 </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年7月22日 下午5:42:37</p>
 * <p>类全名：com.hibo.cms.sys.shiro.util.PasswordHelper</p>
 * 作者：Victor
 * 初审：
 * 复审：
 */
@Component
public class PasswordHelper {

    private static RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();

    private static String algorithmName = "md5";
    private static int hashIterations = 2;

    public void setRandomNumberGenerator(RandomNumberGenerator randomNumberGenerator) {
    	PasswordHelper.randomNumberGenerator = randomNumberGenerator;
    }

    public void setAlgorithmName(String algorithmName) {
    	PasswordHelper.algorithmName = algorithmName;
    }

    public void setHashIterations(int hashIterations) {
    	PasswordHelper.hashIterations = hashIterations;
    }

    public void encryptPassword(User user) {

        user.setSalt(randomNumberGenerator.nextBytes().toHex());
    	String pass = user.getPassword();
    	String salt = user.getCredentialsSalt();
        String newPassword = new SimpleHash(algorithmName,pass,ByteSource.Util.bytes(salt),hashIterations).toHex();
        user.setPassword(newPassword);
    }
    public static String[] encryptPassword(String password,String username) {
    	String[] oo = new String[2];
        oo[0] = randomNumberGenerator.nextBytes().toHex();
        oo[1] = new SimpleHash(algorithmName,password,ByteSource.Util.bytes(username+oo[0]),hashIterations).toHex();
        return oo;
    }
    
    public static String getPasswordByPS(String password,String username,String salt) {
        return new SimpleHash(algorithmName,password,ByteSource.Util.bytes(username+salt),hashIterations).toHex();
    }
}

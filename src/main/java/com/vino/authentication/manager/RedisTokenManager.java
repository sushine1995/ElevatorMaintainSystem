package com.vino.authentication.manager;

import java.security.Key;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.stereotype.Service;

import com.vino.authentication.constant.AuthenticationConstants;
import com.vino.authentication.entity.TokenModel;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
@Service
public class RedisTokenManager implements TokenManager {
	  private Key key = AuthenticationConstants.KEY;//ÿ�β���Ӧ��key�������
	  private RedisTemplate<Long, String> redis;

	    @Autowired
	    public void setRedis(RedisTemplate<Long, String> redis) {
	        this.redis = redis;
	        //�������ó�Long�������Ķ�Ӧ�����л�����
	        redis.setKeySerializer(new JdkSerializationRedisSerializer());
	    }
	    @Override
	    public TokenModel createToken(long userId) {	
	    	//����token����ʱ��,����Ч�ڳ������ݿ��еĹ���ʱ�䣬���ݿ��еĹ���ʱ��ÿ�ν��в����󶼻��ӳ�
	    	//�����е�token����ʱ��Ϊ�����Ч������
			DateTime expirationTime=new DateTime().plusDays(AuthenticationConstants.TOKEN_EXPIRES_DAY);		
			String uuid = UUID.randomUUID().toString().replace("-", "");
			String token=Jwts.builder().setSubject(userId+"").setExpiration(expirationTime.toDate()).claim("uuid", uuid)
					.signWith(SignatureAlgorithm.HS512, key).compact();
	    		      
	        TokenModel model = new TokenModel(userId, token);
	        //�洢��redis�����ù���ʱ�� key:userId value:token
	        redis.boundValueOps(userId).set(token, AuthenticationConstants.TOKEN_EXPIRES_HOUR, TimeUnit.HOURS);
	        return model;
	    }
	    @Override
	    public TokenModel getToken(String token) {
	    	if(token==null||"".equals(token))
	    		return null;
	    	try{
	    	String userId=Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().getSubject();
	    	Date expirationTime=Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().getExpiration();
	    	if(expirationTime.before(new Date()))//�Ѿ�����
	    		return null;
	    	else
	    		return new TokenModel(Long.parseLong(userId), token);
	    	}catch(Exception e){//��token�д��ʱ��
	    		return null;
	    	}
	    	
	    }
	    @Override
	    public boolean checkToken(TokenModel model) {
	        if (model == null) {
	            return false;
	        }
	        String token = redis.boundValueOps(model.getUserId()).get();
	        if (token == null || !token.equals(model.getToken())) {
	            return false;
	        }
	        //�����֤�ɹ���˵�����û�������һ����Ч�������ӳ�token�Ĺ���ʱ��
	        redis.boundValueOps(model.getUserId()).expire(AuthenticationConstants.TOKEN_EXPIRES_HOUR, TimeUnit.HOURS);
	        return true;
	    }
	    @Override
	    public void deleteToken(long userId) {
	        redis.delete(userId);
	    }
	    
	    

}

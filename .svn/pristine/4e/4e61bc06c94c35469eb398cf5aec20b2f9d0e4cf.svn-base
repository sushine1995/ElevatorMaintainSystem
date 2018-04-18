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
	  private Key key = AuthenticationConstants.KEY;//每次部署应用key都会变了
	  private RedisTemplate<Long, String> redis;

	    @Autowired
	    public void setRedis(RedisTemplate<Long, String> redis) {
	        this.redis = redis;
	        //泛型设置成Long后必须更改对应的序列化方案
	        redis.setKeySerializer(new JdkSerializationRedisSerializer());
	    }
	    @Override
	    public TokenModel createToken(long userId) {	
	    	//设置token过期时间,该有效期长于数据库中的过期时间，数据库中的过期时间每次进行操作后都会延长
	    	//请求中的token过期时间为最多有效的天数
			DateTime expirationTime=new DateTime().plusDays(AuthenticationConstants.TOKEN_EXPIRES_DAY);		
			String uuid = UUID.randomUUID().toString().replace("-", "");
			String token=Jwts.builder().setSubject(userId+"").setExpiration(expirationTime.toDate()).claim("uuid", uuid)
					.signWith(SignatureAlgorithm.HS512, key).compact();
	    		      
	        TokenModel model = new TokenModel(userId, token);
	        //存储到redis并设置过期时间 key:userId value:token
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
	    	if(expirationTime.before(new Date()))//已经过期
	    		return null;
	    	else
	    		return new TokenModel(Long.parseLong(userId), token);
	    	}catch(Exception e){//当token有错的时候
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
	        //如果验证成功，说明此用户进行了一次有效操作，延长token的过期时间
	        redis.boundValueOps(model.getUserId()).expire(AuthenticationConstants.TOKEN_EXPIRES_HOUR, TimeUnit.HOURS);
	        return true;
	    }
	    @Override
	    public void deleteToken(long userId) {
	        redis.delete(userId);
	    }
	    
	    

}

package com.meiduimall.service.sms.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;
import java.util.ArrayList;
import java.util.List;

@Configuration
@ImportResource("classpath:applicationContext-common.xml")
public class RedisConfig {

	@Bean(name = "shardedJedisPool")
	public ShardedJedisPool shardedJedisPool(@Qualifier("jedis.config") JedisPoolConfig config,
			@Qualifier("jedisShardInfo") JedisDBShardInfo jedisShardInfo) {
		List<JedisShardInfo> list = new ArrayList<>();
		list.add(jedisShardInfo);
		return new ShardedJedisPool(config, list);
	}

	@Bean(name = "jedisShardInfo")
	public JedisDBShardInfo jedisShardInfo(@Value("${config.jedis.jedisShardInfo.host}") String host,
			@Value("${config.jedis.jedisShardInfo.port}") int port,
			@Value("${config.jedis.jedisShardInfo.password}") String password,
			@Value("${config.jedis.jedisShardInfo.database}") int database) {
		JedisDBShardInfo jedisShardInfo = new JedisDBShardInfo(host, port);
		jedisShardInfo.setPassword(password);
		jedisShardInfo.setDatabase(database);
		return jedisShardInfo;
	}

	@Bean(name = "jedis.config")
	public JedisPoolConfig jedisPoolConfig(@Value("${config.jedis.maxTotal}") int maxTotal,
			@Value("${config.jedis.maxIdle}") int maxIdle, @Value("${config.jedis.maxWaitMillis}") int maxWaitMillis,
			@Value("${config.jedis.testOnBorrow}") boolean testOnBorrow,
			@Value("${config.jedis.testOnReturn}") boolean testOnReturn) {

		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(maxTotal);
		config.setMaxIdle(maxIdle);
		config.setMaxWaitMillis(maxWaitMillis);
		return config;
	}
}
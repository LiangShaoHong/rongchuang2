package com.ruoyi.web.core.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class RedissionConfig {

	@Bean
	public RedissonClient redissonClient() throws IOException {

		Config config = Config.fromYAML(RedissionConfig.class.getClassLoader().getResource("redisson-config.yml"));
		return Redisson.create(config);
	}

}

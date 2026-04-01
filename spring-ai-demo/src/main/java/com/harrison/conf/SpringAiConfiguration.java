package com.harrison.conf;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author WangHS
 * @date 2026/4/1 22:57
 */
@Configuration
public class SpringAiConfiguration {
	@Bean
	public ChatClient chatClient(ChatClient.Builder builder) {
		return builder.build();
	}
}

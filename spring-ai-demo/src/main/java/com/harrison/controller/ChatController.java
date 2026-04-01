package com.harrison.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Map;

/**
 * @author WangHS
 * @date 2026/4/1 22:52
 */
@RestController
@RequestMapping("/openai")
public class ChatController {

	@Resource
	private ChatClient chatClient;

	@GetMapping("/chat")
	public String chat(String question) {
		return chatClient.prompt(question)
				.call().content();
	}
	@GetMapping(path = "/stream", produces = "text/html;charset=UTF-8")
	public Flux<String> stream(String question) {
		return chatClient.prompt(question)
				.stream().content();
	}

	@GetMapping("/poem")
	public Poem output(String topic) {
		BeanOutputConverter<Poem> converter = new BeanOutputConverter<>(Poem.class);
		PromptTemplate promptTemplate = new PromptTemplate("写一首关于 {topic} 的诗. {format}");
		String prompt = promptTemplate.render(Map.of("topic", topic, "format", converter.getFormat()));
		String content = chatClient.prompt(prompt)
				.call().content();
		return converter.convert(content);
	}

	record Poem(String title, String author, String content){

	}
}

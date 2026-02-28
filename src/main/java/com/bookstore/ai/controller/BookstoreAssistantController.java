package com.bookstore.ai.controller;

import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bookstore")
public class BookstoreAssistantController {

	private final OpenAiChatModel chatModel;

	public BookstoreAssistantController(OpenAiChatModel chatModel) {
		this.chatModel = chatModel;
	}

	@GetMapping("/informations")
	public ChatResponse bookstoreChat(
			@RequestParam(value = "message", defaultValue = "Quais são os livros best sellers dos ultimos anos?") String message) {
		return chatModel.call(new Prompt(message));
	}
}

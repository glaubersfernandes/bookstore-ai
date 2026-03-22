package com.bookstore.ai.controller;

import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/bookstore")
public class BookstoreAssistantController {

	private final OpenAiChatModel chatModel;

	public BookstoreAssistantController(OpenAiChatModel chatModel) {
		this.chatModel = chatModel;
	}

	/*
	 * @GetMapping("/informations") public ChatResponse bookstoreChat(
	 * 
	 * @RequestParam(value = "message", defaultValue =
	 * "Quais são os livros best sellers dos ultimos anos?") String message) {
	 * return chatModel.call(new Prompt(message)); }
	 */

	@GetMapping("/informations")
	public Generation bookstoreChatEx2(
			@RequestParam(value = "message", defaultValue = "Quais são os livros best sellers dos ultimos anos?") String message) {
		return chatModel.call(new Prompt(message)).getResult();
	}

	@GetMapping("/reviews")
	public String bookstoreReview(@RequestParam(value = "book", defaultValue = "Dom Quixote") String book) {
		PromptTemplate promptTemplate = new PromptTemplate("""
					Por favor, me forneça um breve resumo do livo {book}
					e também a biografia de seu autor.
				""");
		promptTemplate.add("book", book);

		return this.chatModel.call(promptTemplate.create()).getResult().getOutput().getText();
	}
	
	/*@GetMapping("/stream/informations")
	public Flux<String> bookstoreChatStream(
			@RequestParam(value = "message", defaultValue = "Quais são os livros best sellers dos ultimos anos?") String message) {
		return chatModel.stream(message);
	}*/
	
	@GetMapping("/stream/informations")
	public Flux<ChatResponse> bookstoreChatStream(
			@RequestParam(value = "message", defaultValue = "Quais são os livros best sellers dos ultimos anos?") String message) {
		return chatModel.stream(new Prompt(message));
	}
}

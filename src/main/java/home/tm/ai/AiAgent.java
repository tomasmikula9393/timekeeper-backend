package home.tm.ai;

import org.springframework.ai.chat.client.ChatClient;

public class AiAgent {

    private final ChatClient chatClient;
    private String systemPrompt;

    private AiAgent(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public static Builder builder(ChatClient.Builder builder) {
        return new Builder(builder);
    }

    public static class Builder {
        private final ChatClient.Builder chatClientBuilder;
        private String systemPrompt;

        public Builder(ChatClient.Builder chatClientBuilder) {
            this.chatClientBuilder = chatClientBuilder;
        }

        public Builder system(String prompt) {
            this.systemPrompt = prompt;
            return this;
        }

        public AiAgent build() {
            ChatClient client = chatClientBuilder.build();
            AiAgent agent = new AiAgent(client);
            agent.systemPrompt = systemPrompt;
            return agent;
        }
    }

    public String run(String message) {
        return chatClient
                .prompt()
                .system(systemPrompt)
                .user(message)
                .call()
                .content();
    }
}

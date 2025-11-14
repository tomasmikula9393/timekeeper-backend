package home.tm.services.impl;

import home.tm.TimeKeeper.api.models.WeeklyTrainingRequest;
import home.tm.TimeKeeper.api.models.WeeklyTrainingSummary;
import home.tm.services.AiTrainingService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class AiTrainingServiceImpl implements AiTrainingService {

    private final ChatClient chatClient;

    public AiTrainingServiceImpl(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public WeeklyTrainingSummary analyze(WeeklyTrainingRequest dto) {

        String prompt = buildPrompt(dto);
        String answer = chatClient.prompt(prompt).call().content();

        WeeklyTrainingSummary summary = new WeeklyTrainingSummary();
        summary.setSummary(answer);
        summary.setProgress(extractSection(answer, "progres"));
        summary.setRecommendations(extractSection(answer, "doporučení"));
        summary.setFatigueRisk(extractSection(answer, "únava"));
        summary.setCombatAdvice(extractSection(answer, "box"));

        return summary;
    }


    private String buildPrompt(WeeklyTrainingRequest dto) {
        return """
                Jsi profesionální kondiční trenér, silový coach a boxerský trenér.
                
                Profil sportovce:
                Váha: %.1f kg
                Cíl: zvýšit kondici, sílu, výbušnost a být lepší v boxu.
                Tréninkový týden: 2× silový trénink, 2× kondice, 1–2× box.
                
                Zhodnoť následující tréninky za týden:
                %s
                
                Urob hlubokou a expertní analýzu:
                - shrnutí týdne
                - progres
                - doporučení
                - riziko únavy / přetrénování
                - doporučení pro box (technika, výbušnost, kondice)
                """.formatted(
                dto.getBodyWeight(),
                dto.toString()
        );
    }

    private String extractSection(String text, String key) {
        // později to budeme parsovat jako JSON → zatím vrací celý output
        return text;
    }
}

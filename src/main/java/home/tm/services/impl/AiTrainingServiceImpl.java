package home.tm.services.impl;

import home.tm.TimeKeeper.api.models.BoxTrainingEntry;
import home.tm.TimeKeeper.api.models.CardioTrainingEntry;
import home.tm.TimeKeeper.api.models.ExerciseEntry;
import home.tm.TimeKeeper.api.models.StrengthTrainingEntry;
import home.tm.TimeKeeper.api.models.WeeklyTrainingRequest;
import home.tm.TimeKeeper.api.models.WeeklyTrainingSummary;
import home.tm.exceptions.ExceptionType;
import home.tm.exceptions.InternalException;
import home.tm.exceptions.SeverityEnum;
import home.tm.services.AiTrainingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import static home.tm.exceptions.ExceptionType.BUSINESS_CHYBA;
import static home.tm.exceptions.SeverityEnum.ERROR;

@Service
@Slf4j
public class AiTrainingServiceImpl implements AiTrainingService {

    private final ChatClient chatClient;

    public AiTrainingServiceImpl(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public WeeklyTrainingSummary analyze(WeeklyTrainingRequest dto) {
        String result = """
                ## 📊 Shrnutí týdne (mock result)
                
                Tento týden byl zaměřen převážně na silový výkon a základní kondici.
                
                ---
                
                ## 💪 Progres – síla, kondice, výbušnost, box
                
                ### Síla
                - Dřep **4×6 @110 kg**
                - Bench **4×8 @110 kg**
                - Deadlift **4×8 @120 kg**
                ➡ Silová úroveň roste.
                
                ### Kondice
                - Běh **30 min / 5 km**
                ➡ Stabilní základní vytrvalost.
                
                ### Výbušnost
                - Tento týden chyběla.
                ➡ Progres stagnuje.
                
                ### Box
                - Bez tréninku.
                ➡ Timing a technika stagnují.
                
                ---
                
                ## 🔥 Doporučení pro další týden
                
                ### Silový trénink
                - Zvedni zátěže:
                  - dřep +2,5 kg  
                  - bench +2,5 kg  
                  - tah +5 kg  
                - Přidej cvik na horní záda.
                
                ### Kardio
                - 1× běh 30–40 min (Zóna 2)
                - 1× intervaly HIIT (8× 60s rychle + 75s pomalu)
                
                ### Box
                - Minimálně 1–2× týdně
                - Footwork, kombinace, timing
                
                ### Výbušnost
                - 2 cviky týdně:
                  - medicinbalové hody
                  - box jumpy
                  - dynamické tlaky
                """;
        return new WeeklyTrainingSummary().summary(result);
//        String prompt = buildPrompt(dto);
//        String answer;
//        try {
//            answer = chatClient.prompt(prompt).call().content();
//        } catch (Exception e) {
//            log.error("Došly kredity na OpenAPI");
//            throw new InternalException(BUSINESS_CHYBA, "Došel kredit na OpenAI", ERROR);
//        }
//
//        WeeklyTrainingSummary summary = new WeeklyTrainingSummary();
//        summary.setSummary(answer);
//        summary.setProgress(extractSection(answer, "progres"));
//        summary.setRecommendations(extractSection(answer, "doporučení"));
//        summary.setFatigueRisk(extractSection(answer, "únava"));
//        summary.setCombatAdvice(extractSection(answer, "box"));
//
//        return summary;
    }


    private String buildPrompt(WeeklyTrainingRequest dto) {

        StringBuilder sb = new StringBuilder();

        sb.append("""
                Jsi profesionální kondiční trenér, silový kouč a boxerský trenér.
                
                Profil sportovce:
                - Váha: %s kg
                - Cíl: zvýšit kondici, sílu, výbušnost a zlepšit výkonnost v boxu.
                - Struktura týdne: 2× silový trénink, 2× kardio, 1–2× box.
                
                Zde máš přesný přehled tréninků za týden.
                
                ===========================
                SILOVÉ TRÉNINKY
                ===========================
                """.formatted(dto.getBodyWeight())
        );

        for (StrengthTrainingEntry s : dto.getStrength()) {
            sb.append("Trénink: ").append(s.getName()).append("\n");
            sb.append("Den: ").append(s.getDay()).append("  (týden ").append(s.getWeek()).append(")\n");
            if (s.getNote() != null && !s.getNote().isEmpty()) {
                sb.append("Poznámka: ").append(s.getNote()).append("\n");
            }

            sb.append("Cviky:\n");
            for (ExerciseEntry e : s.getExercises()) {
                sb.append(" - ").append(e.getName())
                        .append(": ").append(e.getSet()).append("×").append(e.getRep())
                        .append(" (").append(e.getWeight()).append(" kg)").append("\n");
            }
            sb.append("\n");
        }

        sb.append("""
                ===========================
                KARDIO TRÉNINKY
                ===========================
                """);

        for (CardioTrainingEntry c : dto.getCardio()) {
            sb.append("Aktivita: ").append(c.getName()).append("\n");
            sb.append("Den: ").append(c.getDay()).append("  (týden ").append(c.getWeek()).append(")\n");
            sb.append("Délka: ").append(c.getDuration()).append(" min\n");

            if (c.getDistance() != null) {
                sb.append("Vzdálenost: ").append(c.getDistance()).append(" km\n");
            }
            if (c.getSpeed() != null) {
                sb.append("Rychlost: ").append(c.getSpeed()).append(" km/h\n");
            }
            if (c.getNote() != null && !c.getNote().isEmpty()) {
                sb.append("Poznámka: ").append(c.getNote()).append("\n");
            }
            sb.append("\n");
        }

        sb.append("""
                ===========================
                BOX TRÉNINKY
                ===========================
                """);

        for (BoxTrainingEntry b : dto.getBoxing()) {
            sb.append("Trénink: ").append(b.getName()).append("\n");
            sb.append("Den: ").append(b.getDay()).append("  (týden ").append(b.getWeek()).append(")\n");

            if (b.getDuration() != null) {
                sb.append("Délka: ").append(b.getDuration()).append(" min\n");
            }

            if (b.getNote() != null && !b.getNote().isEmpty()) {
                sb.append("Poznámka: ").append(b.getNote()).append("\n");
            }
            sb.append("\n");
        }

        sb.append("""
                ===========================
                ÚKOL PRO TEBE
                ===========================
                
                Udělej hlubokou odbornou analýzu:
                - shrnutí týdne
                - progres (síla, kondice, výbušnost, box)
                - doporučení pro další týden
                - riziko přetížení / regenerace
                - doporučení pro box: technika, timing, výbušnost, kombinace
                - doporučení pro silový trénink: cviky, objem, postupné navyšování
                - doporučení pro kardio: intenzita, délka, intervaly
                
                Buď velmi přesný, komplexní, ale lidsky čitelný.
                """);

        return sb.toString();
    }

    private String extractSection(String text, String key) {
        // později to budeme parsovat jako JSON → zatím vrací celý output
        return text;
    }
}

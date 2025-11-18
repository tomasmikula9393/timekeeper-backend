package home.tm.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import home.tm.TimeKeeper.api.models.TrainingDto;
import home.tm.ai.AiAgent;
import home.tm.model.Training;
import home.tm.model.User;
import home.tm.model.UserProfile;
import home.tm.services.ScheduledService;
import home.tm.services.TrainingDiaryService;
import home.tm.services.UserService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduledServiceImpl implements ScheduledService {

    private final AiAgent aiAgent;
    private final UserService userService;
    private final TrainingDiaryService trainingDiaryService;
    private final ObjectMapper objectMapper;
    private final JavaMailSender javaMailSender;

    @Override
    public void trainingAnalyze() {
        List<User> users = userService.getAllUsers();

        users.forEach(user -> {
            // 1) data
            UserProfile profile = userService.getUserProfile(user.getId());
            List<TrainingDto> lastWeek = trainingDiaryService.getLastWeek(user.getId());
            List<TrainingDto> last8Weeks = trainingDiaryService.getLast8Weeks(user.getId());

            try {
                // 2) vytvoříme JSON payload
                String payload = """
                        {
                            "user_profile": %s,
                            "last_week": %s,
                            "last_8_weeks": %s
                        }
                        """.formatted(
                        objectMapper.writeValueAsString(profile),
                        objectMapper.writeValueAsString(lastWeek),
                        objectMapper.writeValueAsString(last8Weeks)
                );

//                String report = aiAgent.run(payload);
                String report = "{\n" +
                        "  \"vyhodnocení progressu\": \"Tvůj poslední týden je velmi konzistentní s předchozími 8 týdny. Držíš stabilní frekvenci 1× silový trénink, 1× běh a 1× box. Váhy na dřepu, mrtvém tahu i bench pressu jsou shodné jako dlouhodobý průměr – síla stagnuje, ale nedochází k propadu. Kondiční část (běh 5 km za ~30 min) odpovídá tvému průměru posledních týdnů. Box tréninkově patří mezi tvoje lepší týdny – 60 minut intenzity s lapami ukazuje dobré tempo. Celkově stagnuješ ve výkonu, ale držíš dobrou formu a objem tréninku.\",\n" +
                        "  \n" +
                        "  \"report\": \"Za poslední týden máš 3 kvalitní tréninky. Silový trénink byl technicky čistý, ale váhově jsi během posledních 8 týdnů nepřidal. Doporučuji drobné progresivní navyšování – dřep +2,5 kg, mrtvý tah +2,5–5 kg, bench +2,5 kg. Běh držíš stabilně, ale chce to variabilitu – jeden týden rychlostní úseky, další týden delší vytrvalost. Box byl velmi dobrý a měl největší pozitivní dopad na kondici. Pro příští týden navrhuji: 2× silový trénink (full-body), 2× běh (1× intervaly, 1× volné tempo), 1–2× box. Zaměř se na výbušnost dolních končetin a vyšší kadenci úderů v kombinacích. Pokud chceš shodit cca 3 kg tuku, doporučuji přidat jeden krátký 20–25min zone 2 běh ráno nalačno nebo po tréninku. Celkově jsi na dobré cestě a tréninky nejsou přetížené – máš prostor navyšovat intenzitu.\"\n" +
                        "}\n";
                sendEmail(user.getEmail(), "Týdenní tréninkový report", report);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void sendEmail(String email, String subject, String message) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(formatMessage(message), true);

            javaMailSender.send(mimeMessage);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private String formatMessage(String message) {
        return """
            <html>
            <body style="font-family: Arial, sans-serif; line-height: 1.6; background-color: #f7f7f7; padding: 20px;">
                
                <div style="max-width: 600px; margin: auto; background: white; padding: 25px; border-radius: 8px;
                            box-shadow: 0 2px 8px rgba(0,0,0,0.05);">
                    
                    <h2 style="color: #2b2b2b; border-bottom: 1px solid #eee; padding-bottom: 10px;">
                        Týdenní tréninkový report
                    </h2>
                    
                    <div style="font-size: 15px; color: #444; white-space: pre-line;">
                        %s
                    </div>

                    <hr style="margin: 25px 0; border: none; border-top: 1px solid #eee;" />

                    <p style="font-size: 13px; color: #888; margin: 0;">
                        Tento report byl automaticky vygenerován pomocí TimeKeeper a AI analýzy vašich tréninků.
                    </p>

                    <p style="font-size: 13px; color: #888; margin-top: 5px;">
                        S pozdravem,<br/>
                        <strong>Tým TimeKeeper</strong>
                    </p>
                </div>

            </body>
            </html>
            """.formatted(message);
    }
}
package home.tm.services.impl;

import home.tm.model.Item;
import home.tm.model.enums.Stav;
import home.tm.repositories.ItemRepository;
import home.tm.repositories.UserRepository;
import home.tm.services.TimeKeeperJobCronService;
import lombok.RequiredArgsConstructor;
import org.quartz.JobExecutionContext;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TimeKeeperJobCronServiceImpl implements TimeKeeperJobCronService {

    private final ItemRepository itemRepository;
    private final JavaMailSender javaMailSender;
    private static final String SUBJECT = "Upozornění na končící platnost! [TimeKeeper aplikace]";

    @Override
    public void checkValidation(JobExecutionContext context) {
        List<Item> expiredItems = itemRepository.findAllByValidityToIsBeforeAndStavIsNot(LocalDateTime.now(), Stav.EXPIRED.name());
        expiredItems.forEach(item -> {
            sendEmail(
                    item.getUser().getEmail(),
                    String.format("Vašemu hlídanému záznamu %s skončila platnost - %s ", item.getTitle(), item.getValidityTo())
                    );
            item.setStav(Stav.EXPIRED.name());
            itemRepository.save(item);
        });
        List<Item> itemsWeekBeforeExpiration = itemRepository.findAllByValidityToIsBetweenAndStavIsNot(LocalDateTime.now(), LocalDateTime.now().plusDays(8L), Stav.CHECKING.name());
        itemsWeekBeforeExpiration.forEach(item -> {
            sendEmail(
                    item.getUser().getEmail(),
                    String.format("Vašemu hlídanému záznamu %s brzy skončí platnost - %s ", item.getTitle(), item.getValidityTo())
                    );
            item.setStav(Stav.CHECKING.name());
            itemRepository.save(item);
        });
    }

    public void sendEmail(String email, String message) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setTo(email);
            helper.setSubject(SUBJECT);
            helper.setText(formatMessage(message), true); // true = povolit HTML obsah

            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private String formatMessage(String message) {
        return """
                <html>
                <body style="font-family: Arial, sans-serif; line-height: 1.5;">
                    <div style="padding: 20px; border: 1px solid #ddd; border-radius: 5px;">
                        <h2 style="color: #333;">Upozornění</h2>
                        <p style="font-size: 14px; color: #555;">%s</p>
                        <footer style="margin-top: 20px; font-size: 12px; color: #888;">
                            <p>S pozdravem,</p>
                            <p>Tým TimeKeeper</p>
                        </footer>
                    </div>
                </body>
                </html>
                """.formatted(message);
    }
}

package home.tm.configuration;

import home.tm.ai.AiAgent;
import home.tm.quartz.TimeKeeperJob;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;

import java.util.Properties;

@Configuration
public class AppConfiguration {
    @Value("${smtp.host}")
    private String smtpHost;

    @Value("${smtp.port}")
    private Integer smtpPort;

    @Value("${smtp.auth:true}")
    private boolean smtpAuth;

    @Value("${smtp.starttls.enable:true}")
    private boolean smtpStartTlsEnable;

    @Value("${smtp.username}")
    private String smtpUsername;

    @Value("${smtp.password}")
    private String smtpPassword;
    @Value("${timeKeeper.job.cron}")
    private String timeKeeperJobCron;
    @Value("${timeKeeper.job.cron}")
    private String weeklyTrainingEvaluationJobCron;

    @Bean
    public JobDetailFactoryBean timeKeeperJobFactory() {
        JobDetailFactoryBean factory = new JobDetailFactoryBean();
        factory.setJobClass(TimeKeeperJob.class);
        factory.setName("TimeKeeperJob");
        factory.setGroup("TimeKeeperJobGroup");
        factory.setDurability(true);
        return factory;
    }

    @Bean
    public CronTriggerFactoryBean timeKeeperTriggerFactory() {
        CronTriggerFactoryBean factory = new CronTriggerFactoryBean();
        factory.setJobDetail(timeKeeperJobFactory().getObject());
        factory.setCronExpression(timeKeeperJobCron);
        return factory;
    }

    @Bean
    public JobDetailFactoryBean weeklyTrainingEvaluationJobFactory() {
        JobDetailFactoryBean factory = new JobDetailFactoryBean();
        factory.setJobClass(TimeKeeperJob.class);
        factory.setName("WeeklyTrainingEvaluationJob");
        factory.setGroup("WeeklyTrainingEvaluationJobGroup");
        factory.setDurability(true);
        return factory;
    }

    @Bean
    public CronTriggerFactoryBean WeeklyTrainingEvaluationTriggerFactory() {
        CronTriggerFactoryBean factory = new CronTriggerFactoryBean();
        factory.setJobDetail(weeklyTrainingEvaluationJobFactory().getObject());
        factory.setCronExpression(weeklyTrainingEvaluationJobCron);
        return factory;
    }

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(smtpHost);
        if (smtpPort != null && !smtpPort.equals(0))
            mailSender.setPort(smtpPort);
        mailSender.setUsername((StringUtils.isBlank(smtpUsername) ? null : smtpUsername));
        mailSender.setPassword((StringUtils.isBlank(smtpPassword)) ? null : smtpPassword);
        mailSender.setDefaultEncoding("UTF-8");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", smtpAuth);
        props.put("mail.smtp.starttls.enable", smtpStartTlsEnable);
        // props.put("mail.smtp.connectiontimeout", 5000);
        // props.put("mail.smtp.timeout", 5000);
        props.put("mail.debug", "false");

        return mailSender;
    }

    @Bean
    public AiAgent trainingAgent(ChatClient.Builder builder) {
        return AiAgent.builder(builder)
                .system("""
                            Jsi profesionální kondiční trenér.
                        
                            Tvým úkolem je:
                            - vyhodnotit poslední týden tréninků
                            - porovnat je s posledními 8 týdny
                            - dát doporučení na příští týden
                            - vrátit textový report
                            - JSON bude strukturovaný jako:
                            {
                              "vyhodnocení progressu": "...",
                              "report": "..."
                            }
                        """)
                .build();
    }

}

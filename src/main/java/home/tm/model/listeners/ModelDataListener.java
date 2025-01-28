package home.tm.model.listeners;

import home.tm.model.User;
import home.tm.model.base.ModelData;
import home.tm.security.service.SecurityService;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@Slf4j
public class ModelDataListener {

    private static SecurityService securityService;

    public static void setSecurityService(SecurityService securityService) {
        ModelDataListener.securityService = securityService;
    }

    @PrePersist
    public void onModelCreated(ModelData model) {
        try {
            User user = securityService.getCurrentUser();
            model.setCreated(LocalDateTime.now());
            model.setCreatedBy(user.getName() + " " + user.getSurname());
            onModelUpdated(model);
        } catch (Exception e) {
            log.error("Error during @PrePersist: ", e);
        }
    }

    @PreUpdate
    public void onModelUpdated(ModelData model) {
        try {
            User user = securityService.getCurrentUser();
            model.setUpdated(LocalDateTime.now());
            model.setUpdatedBy(user.getName() + " " + user.getSurname());
        } catch (Exception e) {
            log.error("Error during @PreUpdate: ", e);
        }
    }
}

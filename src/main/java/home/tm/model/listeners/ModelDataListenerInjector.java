package home.tm.model.listeners;

import home.tm.security.service.SecurityService;
import org.springframework.stereotype.Component;

@Component
public class ModelDataListenerInjector {

    public ModelDataListenerInjector(SecurityService securityService) {
        ModelDataListener.setSecurityService(securityService);
    }
}

package home.tm.security.service;

import home.tm.model.User;

public interface SecurityService {
    /**
     * Získá aktuálně přihlášeného uživatele.
     *
     * @return aktuálně přihlášený uživatel
     * @throws RuntimeException pokud uživatel není nalezen nebo není přihlášen
     */
    User getCurrentUser();
}

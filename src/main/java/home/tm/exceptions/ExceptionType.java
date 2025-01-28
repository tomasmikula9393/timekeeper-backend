package home.tm.exceptions;

import lombok.Getter;

@Getter
public enum ExceptionType {
    NEBYLO_NALEZENO("TK001"),
    SERVER_NEODPOVIDA("TK002"),
    BUSINESS_CHYBA("TK003"),
    CHYBA_KOMUNIKACE("TK004"),
    FILENET_CHYBA("TK005"),
    PORUSENI_INTEGRITY_DAT("TK006"),
    INTERNI_CHYBA("TK007"),
    CHYBNY_VSTUP("TK008"),
    CHYBA_AUTH("TK009"),
    CHYBA_VOLANI_VIR("TK010"),
    CHYBA_ZALOZENI_DOKUMENTU("TK011"),
    ;

    final Class<?> reference;
    private final String kod;

    ExceptionType(String kod) {
        this(null, kod);
    }

    ExceptionType(Class<?> reference, String kod) {
        this.reference = reference;
        this.kod = kod;
    }
}

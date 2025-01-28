package home.tm.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ResponseErrorDto {

    private final String kod;
    private final String casVzniku;
    private final String chyboveHlaseni;
    private final String idTransakce;
    private final String idSession;
    private final String detaily;
    private final HttpStatus status;
    private final SeverityEnum severity;

    public ResponseErrorDto(TimeKeeperException exception) {
        this.kod = exception.getId().getKod();
        this.casVzniku = exception.getCasVzniku();
        this.chyboveHlaseni = exception.getChyboveHlaseni();
        this.idTransakce = exception.getIdTransakce();
        this.idSession = exception.getIdSession();
        this.detaily = exception.getDetaily();
        this.status = exception.getStatus();
        this.severity = exception.getSeverity();
    }
}

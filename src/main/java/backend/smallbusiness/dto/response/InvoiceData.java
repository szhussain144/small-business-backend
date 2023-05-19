package backend.smallbusiness.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class InvoiceData {
    private LocalDateTime date;
    private double value;
    private String name;
}

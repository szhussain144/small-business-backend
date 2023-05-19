package backend.smallbusiness.dto;

import backend.smallbusiness.model.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SaveInvoiceDTO {
    private List<Product> products;
    private String name;
    private String email;
    private String address;
    private String phoneNumber;
    private String password;
}

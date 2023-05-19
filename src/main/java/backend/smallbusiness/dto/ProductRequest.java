package backend.smallbusiness.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ProductRequest {
    private String name;
    private Double price;
    private String description;
    private Integer quantity;
    private MultipartFile image;
}

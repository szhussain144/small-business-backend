package backend.smallbusiness.controller;

import backend.smallbusiness.model.Supplier;
import backend.smallbusiness.services.ApiResponse;
import backend.smallbusiness.services.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping(path = "supplier")
@RestController
public class SupplierController {
    private final SupplierService supplierService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Supplier>>> getAllSupplier(){
        List<Supplier> suppliers = supplierService.getAllSupplier();
        if(suppliers.size() > 0)
            return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.successWithPayload(suppliers,"Suppliers fetched"));
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.failure("Suppliers not fetched"));
    }

}

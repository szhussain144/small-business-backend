package backend.smallbusiness.controller;

import backend.smallbusiness.model.Purchase;
import backend.smallbusiness.model.User;
import backend.smallbusiness.services.ApiResponse;
import backend.smallbusiness.services.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping(path = "purchase")
@RestController
public class PurchaseController {
    private final PurchaseService purchaseService;

    @PostMapping()
    public ResponseEntity<ApiResponse<Purchase>> createPurchase(@RequestBody Purchase purchase){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Purchase purchaseOrder = purchaseService.createPurchaseOrder(purchase,user);
        if(purchaseOrder != null)
            return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.successWithPayload(purchaseOrder,"Purchase order created"));
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.failure("Purchase order not created"));
    }


}

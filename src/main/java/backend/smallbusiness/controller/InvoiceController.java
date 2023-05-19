package backend.smallbusiness.controller;

import backend.smallbusiness.dto.SaveInvoiceDTO;
import backend.smallbusiness.dto.response.InvoiceData;
import backend.smallbusiness.model.Invoice;
import backend.smallbusiness.model.User;
import backend.smallbusiness.services.ApiResponse;
import backend.smallbusiness.services.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping(path = "invoice")
@RestController
public class InvoiceController {
    private final InvoiceService invoiceService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Invoice>>> getInvoices(@RequestParam(required = false,name = "id") Long id){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Invoice> invoices = invoiceService.getAllInvoices(id,user);
        if(invoices.size() > 0)
            return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.successWithPayload(invoices,"Invoices fetched"));
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.failure("Invoices not fetched"));
    }

    @GetMapping(value = "invoices-reporting")
    public ResponseEntity<ApiResponse<List<InvoiceData>>> getInvoiceData(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<InvoiceData> invoices = invoiceService.getAllInvoiceData(user);
        if(invoices.size() > 0)
            return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.successWithPayload(invoices,"Invoices fetched"));
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.failure("Invoices not fetched"));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> saveInvoice(@RequestBody SaveInvoiceDTO saveInvoiceDTO){
        boolean invoices = invoiceService.saveInvoice(saveInvoiceDTO);
        if(invoices)
            return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Invoices added"));
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.failure("Invoices not added"));
    }

}

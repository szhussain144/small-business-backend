package backend.smallbusiness.services;

import backend.smallbusiness.model.Invoice;
import backend.smallbusiness.model.Purchase;
import backend.smallbusiness.model.User;
import backend.smallbusiness.repository.InvoiceRepository;
import backend.smallbusiness.repository.PurchaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final InvoiceRepository invoiceRepository;
    public Purchase createPurchaseOrder(Purchase purchase, User user){
        try{
            purchase.setPurchaseDate(LocalDateTime.now());
            Purchase purchaseObj = purchaseRepository.save(purchase);
            Invoice invoice = new Invoice();
            invoice.setInvoiceDate(LocalDateTime.now());
            invoice.setCustomer(user);
            invoice.setAmount(purchase.getProduct().getPrice() * purchase.getQuantity());
            invoice.setProduct(purchase.getProduct());
            invoice.setSupplier(purchase.getSupplier());
            invoiceRepository.save(invoice);
            return purchaseObj;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}

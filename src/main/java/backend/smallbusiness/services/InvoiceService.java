package backend.smallbusiness.services;

import backend.smallbusiness.dto.SaveInvoiceDTO;
import backend.smallbusiness.dto.response.InvoiceData;
import backend.smallbusiness.model.*;
import backend.smallbusiness.repository.InvoiceRepository;
import backend.smallbusiness.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    public List<Invoice> getAllInvoices(Long id, User user){
        try {
            if(id == null) {
                return invoiceRepository.findAllById(user);
            }else{
                return invoiceRepository.findInvoicesById(user,id);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<InvoiceData> getAllInvoiceData(User user) {
        List<Invoice> invoices = invoiceRepository.findAllById(user);
        List<InvoiceData> invoiceDataList = new ArrayList<>();

        for (Invoice invoice : invoices) {
            InvoiceData invoiceData = new InvoiceData();
            invoiceData.setDate(invoice.getInvoiceDate());
            invoiceData.setValue(invoice.getAmount());
            invoiceData.setName(invoice.getProduct().getName());

            invoiceDataList.add(invoiceData);
        }

        return invoiceDataList;
    }

    public boolean saveInvoice(SaveInvoiceDTO saveInvoiceDTO){
        try{
            User user = new User();
            user.setName(saveInvoiceDTO.getName());
            user.setAddress(saveInvoiceDTO.getAddress());
            user.setEmail(saveInvoiceDTO.getEmail());
            user.setPassword(passwordEncoder.encode(saveInvoiceDTO.getPassword()));
            user.setPhoneNumber(saveInvoiceDTO.getPhoneNumber());
            user.setUserType(UserType.BUYER);
            userRepository.save(user);

            List<Invoice> invoices = new ArrayList<>();
            for (Product product:saveInvoiceDTO.getProducts()) {
                Invoice invoice = new Invoice();
                invoice.setInvoiceDate(LocalDateTime.now());
                invoice.setCustomer(user);
                invoice.setAmount(product.getPrice());
                invoice.setProduct(product);
                invoice.setSupplier(product.getSupplier());
                invoices.add(invoice);
            }

            invoiceRepository.saveAll(invoices);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

}

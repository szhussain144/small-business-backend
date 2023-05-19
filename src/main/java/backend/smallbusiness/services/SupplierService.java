package backend.smallbusiness.services;

import backend.smallbusiness.model.Supplier;
import backend.smallbusiness.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SupplierService {
    private final SupplierRepository supplierRepository;

    public List<Supplier> getAllSupplier(){
        try {
            return supplierRepository.findAll();
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}

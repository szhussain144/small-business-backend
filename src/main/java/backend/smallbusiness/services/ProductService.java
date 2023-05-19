package backend.smallbusiness.services;

import backend.smallbusiness.dto.ProductRequest;
import backend.smallbusiness.dto.ProductUpdateDTO;
import backend.smallbusiness.model.Product;
import backend.smallbusiness.model.Stock;
import backend.smallbusiness.model.User;
import backend.smallbusiness.repository.ProductRepository;
import backend.smallbusiness.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.apache.bcel.util.ClassPath;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final StockRepository stockRepository;

    @Value("${app-image.fileStorage}")
    private String imageFolderPath;

    public List<Product> getAllProducts(){
        try{
            return productRepository.findAll();
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    public List<Product> getAllProductsBySupplier(Long id){
        try{
            return productRepository.findAllProductsBySupplier(id);
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    public Product saveProduct(ProductRequest productRequest, User user) throws IOException {
        try{
            Product product = new Product();
            product.setName(productRequest.getName());
            product.setPrice(productRequest.getPrice());
            product.setDescription(productRequest.getDescription());
            product.setQuantity(productRequest.getQuantity());
            if (productRequest.getImage() != null && !productRequest.getImage().isEmpty()) {
                String fileName = UUID.randomUUID().toString() + ".jpg";

                String filePath = imageFolderPath+File.separator+fileName;
                File dest = new File(imageFolderPath);
                if(!dest.exists()){
                    dest.mkdir();
                }
                Files.copy(productRequest.getImage().getInputStream(), Paths.get(filePath));
                product.setImage("http://localhost:8282/product/view-images/"+fileName);
            }
            Product newProduct = productRepository.save(product);
            Stock stock = new Stock();
            stock.setProduct(newProduct);
            stock.setQuantity(productRequest.getQuantity());
            stock.setUser(user);

            stockRepository.save(stock);
            return newProduct;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public Product updateProduct(ProductUpdateDTO productRequest, Long id)  {
        try{
            Optional<Product> product = productRepository.findById(id);
            if(product.isEmpty()){
                return null;
            }
            product.get().setName(productRequest.getName()!=null?productRequest.getName():product.get().getName());
            product.get().setPrice(productRequest.getPrice() != null?productRequest.getPrice():product.get().getPrice());
            product.get().setDescription(productRequest.getDescription()!=null?productRequest.getDescription():product.get().getDescription());
            product.get().setQuantity(productRequest.getQuantity()!=null?productRequest.getQuantity():product.get().getQuantity());
            return productRepository.save(product.get());
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public Product updateProductImage(MultipartFile productRequest, Long id) throws IOException {
        try{
            Optional<Product> product = productRepository.findById(id);
            if(product.isEmpty()){
                return null;
            }
            if (productRequest != null && !productRequest.isEmpty()) {
                String fileName = UUID.randomUUID().toString() + ".jpg";

                String filePath = imageFolderPath+File.separator+fileName;
                File dest = new File(imageFolderPath);
                if(!dest.exists()){
                    dest.mkdir();
                }
                Files.copy(productRequest.getInputStream(), Paths.get(filePath));
                product.get().setImage("http://localhost:8282/product/view-images/"+fileName);
            }

            return productRepository.save(product.get());
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Product getProductById(Long productId){
        try{
            Optional<Product> product = productRepository.findById(productId);
            if(product.isEmpty()){
                return null;
            }
            return product.get();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public boolean deleteProduct(Long productId){
        try{
            Optional<Product> product = productRepository.findById(productId);
            if(product.isEmpty()){
                return false;
            }
            product.get().setDeletedRecord(true);
            productRepository.save(product.get());
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    public byte[] viewImages(String fileName) throws IOException {
        String filePath= imageFolderPath+File.separator+fileName;
        byte[] images = Files.readAllBytes(new File(filePath).toPath());
        return images;
    }
}

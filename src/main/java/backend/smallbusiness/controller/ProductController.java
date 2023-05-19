package backend.smallbusiness.controller;

import backend.smallbusiness.dto.ProductRequest;
import backend.smallbusiness.dto.ProductUpdateDTO;
import backend.smallbusiness.model.Product;
import backend.smallbusiness.model.User;
import backend.smallbusiness.services.ApiResponse;
import backend.smallbusiness.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping(path = "product")
@RestController
public class ProductController {
    private final ProductService productService;

    @GetMapping()
    public ResponseEntity<ApiResponse<List<Product>>> getAllProducts(){
        List<Product> products = productService.getAllProducts();
        if(products.size() > 0){
            return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.successWithPayload(products,"Products fetched"));
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.failure("Failed to fetch products"));
        }
    }
    @GetMapping(value="/product-by-supplier/{id}")
    public ResponseEntity<ApiResponse<List<Product>>> getAllProductsBySupplier(@PathVariable Long id){
        List<Product> products = productService.getAllProductsBySupplier(id);
        if(products.size() > 0){
            return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.successWithPayload(products,"Products fetched"));
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.failure("Failed to fetch products"));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Product>> createProduct(@ModelAttribute ProductRequest productRequest, HttpServletRequest httpServletRequest) throws IOException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Product product =productService.saveProduct(productRequest,user);
            if(product != null)
                return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.successWithPayload(product,"Product saved"));
            else
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.failure("Product not saved"));

    }

    @PostMapping(value = "{productId}")
    public ResponseEntity<ApiResponse<Product>> updateProduct(@PathVariable Long productId,@RequestBody ProductUpdateDTO productRequest) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Product product =productService.updateProduct(productRequest,productId);
        if(product != null)
            return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.successWithPayload(product,"Product update"));
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.failure("Product not updated"));
    }
    @PostMapping(value = "update-image/{productId}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<Product>> updateProductImage(@PathVariable Long productId,@RequestParam MultipartFile productImage) throws IOException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Product product =productService.updateProductImage(productImage,productId);
        if(product != null)
            return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.successWithPayload(product,"Product update"));
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.failure("Product not updated"));
    }
    @GetMapping(value = "view-images/{fileName}")
    public ResponseEntity<?> downloadImageFromFileSystem(@PathVariable String fileName) throws IOException {
        byte[] imageData=productService.viewImages(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);
    }

    @GetMapping(value = "{productId}")
    public ResponseEntity<ApiResponse<Product>> getProductById(@PathVariable Long productId){
        Product product = productService.getProductById(productId);
        if(product != null)
            return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.successWithPayload(product,"Product deleted"));
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.failure("Product not deleted"));
    }
    @DeleteMapping(value = "{productId}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId){
        if(productService.deleteProduct(productId))
            return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Product deleted"));
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.failure("Product not deleted"));
    }

}

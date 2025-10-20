
package com.pastiara.app.service;

import com.pastiara.app.dto.ProductDto;
import java.util.List;

public interface ProductService {
	
	ProductDto save(ProductDto productDto);
	
	ProductDto findById(Integer id);
	
	List<ProductDto> findAll();
	
	ProductDto update(Integer id, ProductDto productDto);
	
	void deleteById(Integer id);
	
}

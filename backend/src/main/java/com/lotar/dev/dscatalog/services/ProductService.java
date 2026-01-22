package com.lotar.dev.dscatalog.services;

import com.lotar.dev.dscatalog.dto.ProductDTO;
import com.lotar.dev.dscatalog.entities.Product;
import com.lotar.dev.dscatalog.repositories.ProductRepository;
import com.lotar.dev.dscatalog.services.exeptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProductService {

  @Autowired
  private ProductRepository ProductRepository;

  public Page<ProductDTO> findAllPaged(PageRequest pageRequest) {
    Page<Product> list = ProductRepository.findAll(pageRequest);
      return list.map(ProductDTO::new);
  }

  @Transactional(readOnly = true)
  public ProductDTO findById(Long id) {
    Optional<Product> obj = ProductRepository.findById(id);
    Product entity = obj.orElseThrow(() -> new EntityNotFoundException("Entity not found"));
    return new ProductDTO(entity, entity.getCategories());
  }

  @Transactional
  public ProductDTO insert(ProductDTO dto) {
    Product entity = new Product();
//    entity.setName(dto.getName());
    entity =  ProductRepository.save(entity);
    return new ProductDTO(entity);
  }

  @Transactional
  public ProductDTO update(Long id ,ProductDTO dto) {
    try {
      Product entity = ProductRepository.getReferenceById(id);
//      entity.setName(dto.getName());
      entity =  ProductRepository.save(entity);
      return new ProductDTO(entity);
    } catch (EntityNotFoundException e) {
      throw new EntityNotFoundException("Product not found with id " + id);
    }
  }

  @Transactional(propagation = Propagation.SUPPORTS)
  public void delete(Long id) {
    if (!ProductRepository.existsById(id)) {
      throw new ResourceNotFoundException("Product not found with id 2 " + id);
    }

    try {
      ProductRepository.deleteById(id);
    } catch (DataIntegrityViolationException e) {
      throw new DataIntegrityViolationException("Could not delete Product with id " + id);
    }
  }
}

package com.lotar.dev.dscatalog.services;

import com.lotar.dev.dscatalog.dto.CategoryDTO;
import com.lotar.dev.dscatalog.entities.Category;
import com.lotar.dev.dscatalog.repositories.CategoryRepository;
import com.lotar.dev.dscatalog.services.exeptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

  @Autowired
  private CategoryRepository categoryRepository;

  public Page<CategoryDTO> findAllPaged(Pageable pageable) {
    Page<Category> list = categoryRepository.findAll(pageable);
      return list.map(CategoryDTO::new);
  }

  public CategoryDTO findById(Long id) {
    Optional<Category> obj = categoryRepository.findById(id);
    Category entity = obj.orElseThrow(() -> new EntityNotFoundException("Entity not found"));
    return new CategoryDTO(entity);
  }

  @Transactional
  public CategoryDTO insert(CategoryDTO dto) {
    Category entity = new Category();
    entity.setName(dto.getName());
    entity =  categoryRepository.save(entity);
    return new CategoryDTO(entity);
  }

  @Transactional
  public CategoryDTO update(Long id ,CategoryDTO dto) {
    try {
      Category entity = categoryRepository.getReferenceById(id);
      entity.setName(dto.getName());
      entity =  categoryRepository.save(entity);
      return new CategoryDTO(entity);
    } catch (EntityNotFoundException e) {
      throw new EntityNotFoundException("Category not found with id " + id);
    }
  }

  @Transactional(propagation = Propagation.SUPPORTS)
  public void delete(Long id) {
    if (!categoryRepository.existsById(id)) {
      throw new ResourceNotFoundException("Category not found with id 2 " + id);
    }

    try {
      categoryRepository.deleteById(id);
    } catch (DataIntegrityViolationException e) {
      throw new DataIntegrityViolationException("Could not delete category with id " + id);
    }
  }
}

package com.lotar.dev.dscatalog.services;

import com.lotar.dev.dscatalog.dto.CategoryDTO;
import com.lotar.dev.dscatalog.entities.Category;
import com.lotar.dev.dscatalog.repositories.CategoryRepository;
import com.lotar.dev.dscatalog.services.exeptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

  @Autowired
  private CategoryRepository categoryRepository;

  public List<CategoryDTO> findAll() {
      List<Category> list = categoryRepository.findAll();
      return list.stream().map(CategoryDTO::new).toList();
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
}

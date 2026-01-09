package com.lotar.dev.dscatalog.resources;

import com.lotar.dev.dscatalog.entities.Category;
import com.lotar.dev.dscatalog.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(value = "/categories")
public class CatgoryResource {

  @Autowired
  private CategoryService categoryService;

  @GetMapping
  public ResponseEntity<List<Category>> findAll() {

    List<Category> list = categoryService.findAll();
    return ResponseEntity.ok().body(list);
  }
}

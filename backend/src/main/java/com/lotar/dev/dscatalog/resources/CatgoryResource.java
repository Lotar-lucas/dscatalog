package com.lotar.dev.dscatalog.resources;

import com.lotar.dev.dscatalog.entities.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(value = "/categories")
public class CatgoryResource {

  @GetMapping
  public ResponseEntity<List<Category>> findAll() {
    List<Category> list = new ArrayList<>();
    list.add(new Category(1L, "Books"));
    list.add(new Category(2L, "Eltronics"));
    return ResponseEntity.ok().body(list);
  }
}

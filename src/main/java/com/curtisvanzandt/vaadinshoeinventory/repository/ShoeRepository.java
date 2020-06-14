package com.curtisvanzandt.vaadinshoeinventory.repository;

import com.curtisvanzandt.vaadinshoeinventory.model.Shoe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShoeRepository extends JpaRepository<Shoe, Long> {

    List<Shoe> findByBrandStartsWithIgnoreCase(final String brand);
}

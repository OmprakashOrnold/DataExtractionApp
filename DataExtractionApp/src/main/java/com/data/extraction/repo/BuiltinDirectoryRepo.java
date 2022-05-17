package com.data.extraction.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.data.extraction.entites.BuiltinDirectory;

public interface BuiltinDirectoryRepo extends JpaRepository<BuiltinDirectory, Long>{

}

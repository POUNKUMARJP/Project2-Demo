package net.javaguides.springboot.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.javaguides.springboot.model.Word;

@Repository
public interface WordRepository extends JpaRepository<Word, Long>{

	Page<Word> findByFirstDate(String date, Pageable pageable);
	
	
}

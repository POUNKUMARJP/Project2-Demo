package net.javaguides.springboot.service;

import java.util.List;

import org.springframework.data.domain.Page;

import net.javaguides.springboot.model.Word;

public interface WordService {
	List<Word> getAllWord();
	void saveWord(Word word);
	Word getWordById(long id);
	void deleteWordById(long id);
	Page<Word> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
	Page<Word> getDate(String date);
	//List<Word> getAll();
	
}

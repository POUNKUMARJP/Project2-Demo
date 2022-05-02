package net.javaguides.springboot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import net.javaguides.springboot.model.Word;
import net.javaguides.springboot.repository.WordRepository;

@Service
public class WordServiceImpl implements WordService {

	@Autowired
	private WordRepository wordRepository;
	
	public Page<Word> getDate(String date) {
		Sort sort = "ASC".equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by("firstDate").ascending() :
			Sort.by("firstDate").descending();
		
		Pageable pageable = PageRequest.of(0, 1, sort);
		System.out.println("---"+pageable);
		return this.wordRepository.findByFirstDate(date, pageable);
	}

	@Override
	public List<Word> getAllWord() {
		return wordRepository.findAll();
	}

	@Override
	public void saveWord(Word word) {
		this.wordRepository.save(word);
	}

	@Override
	public Word getWordById(long id) {
		Optional<Word> optional = wordRepository.findById(id);
		Word word = null;
		if (optional.isPresent()) {
			word = optional.get();
		} else {
			throw new RuntimeException(" Word not found for id :: " + id);
		}
		return word;
	}

	@Override
	public void deleteWordById(long id) {
		this.wordRepository.deleteById(id);
	}

	@Override
	public Page<Word> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
			Sort.by(sortField).descending();
		
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		return this.wordRepository.findAll(pageable);
	}

}

package net.javaguides.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.javaguides.springboot.model.Word;
import net.javaguides.springboot.repository.WordRepository;
import net.javaguides.springboot.service.WordService;

@Controller    //  Automatically set path in class
//@RequestMapping(path = "/search")
public class WordController {

	@Autowired // Auto wired is used to binding the class or inject one class to another
	private WordService wordService;
	
	
	
	@GetMapping(path = "/searchDate") //entered url path is user wish 
	public String getDate(Model model, @RequestParam("keyword") String date) {
		Page<Word> page = wordService.getDate(date);
		List<Word> listWord = page.getContent();
		
		model.addAttribute("listWord", listWord);
		model.addAttribute("hasListWord", listWord.size());
        if (listWord.size() == 0) {
        	return "Error";
        }
		return "index";
	}
	

	
	// display list of Words list
	@GetMapping("/")
	public String viewHomePage(Model model) {
		return findPaginated(1, "firstDate", "asc", model);		
	}
	
	//The Model contains the request data and provides it to view page.
	@GetMapping("/showNewWordForm")
	public String showNewWordForm(Model model) {
		// create model attribute to bind form data
		//It adds all the attributes in the provided Collection into this Map.
		Word word = new Word();
		model.addAttribute("obj", word);
		return "new_word";
	}
	
	@PostMapping("/saveWord")
	public String saveWord(@ModelAttribute("obj") Word word) {
		// save employee to database
		wordService.saveWord(word);
		return "redirect:/";
	}
	
//	@PostMapping("/searchDate")
//	public String searchEmployee(@ModelAttribute("employee") Word employee) {
//		// save employee to database
//		return wordService.getEmployee(employee.getFirstName()).getLastName();
////		return "redirect:/";
//	}	
	
	@GetMapping("/showFormForUpdate/{id}")
	public String showFormForUpdate(@PathVariable ( value = "id") long id, Model model) {
		
		// get employee from the service
		Word word = wordService.getWordById(id);
		
		// set employee as a model attribute to pre-populate the form
		model.addAttribute("obj", word);
		return "update_word";
	}
	
	@GetMapping("/deleteWord/{id}")
	public String deleteWord(@PathVariable (value = "id") long id) {
		
		// call delete employee method 
		this.wordService.deleteWordById(id);
		return "redirect:/";
	}
	
	
	@GetMapping("/page/{pageNo}")
	public String findPaginated(@PathVariable (value = "pageNo") int pageNo, 
			@RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir,
			Model model) {
		int pageSize = 5;
		
		Page<Word> page = wordService.findPaginated(pageNo, pageSize, sortField, sortDir);
		//List the words from db with help of findpaginated function
		List<Word> listWord = page.getContent();
		
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		
		model.addAttribute("listWord", listWord);
		return "index";
	}
}

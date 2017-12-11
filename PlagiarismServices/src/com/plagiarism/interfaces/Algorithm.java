package com.plagiarism.interfaces;
import java.io.FileNotFoundException;

import org.springframework.web.multipart.MultipartFile;

import com.plagiarism.response.Response;

// Algorithm is an object of any class that implements Algorithm interface

// Interpretation: Algorithm represents core algorithm implemented for comparing ASTs
public interface Algorithm{

	// Runs the algorithm on the given two files
	Response run(MultipartFile f1, MultipartFile f2) throws FileNotFoundException;
	
	// Saves the results of the run
	void saveOutput();
	
}
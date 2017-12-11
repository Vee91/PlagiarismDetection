package com.plagiarism.interfaces;

// JavaParser is an object of any class that implements JavaParser interface

// Interpretation: JavaParser calls parse program provided by JavaCC library
public interface JavaParser {

	// Parse all the java files provided in all the directories of the given path 
	void parseFiles(String basePath);
}
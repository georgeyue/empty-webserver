package com.swift;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileFinder {
	private String rootDirectory;
	private String theFile;
	
	public FileFinder() {
		this.setRootDirectory(null);
		this.theFile = null;
	}

	public boolean exists() {
		Path filePath = getAbsoluteFilePath();
		return Files.exists(filePath);
	}

	public String getRootDirectory() {
		return rootDirectory;
	}

	public void setRootDirectory(String rootDirectory) {
		this.rootDirectory = rootDirectory;
	}

	public Path getAbsoluteFilePath() {
		Path pathToCheck = FileSystems.getDefault().getPath(rootDirectory, this.theFile);
		return pathToCheck;
	}

	public void setFile(String fileName) {
		this.theFile = fileName;
	}
}

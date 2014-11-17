package com.swift;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileFinder {
	private String rootDirectory;
	private String theFile;
	
	public FileFinder() {
		this.theFile = null;
		this.rootDirectory = null;
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

	public byte[] getFileContents() {
		Path pathToFile = this.getAbsoluteFilePath();
		byte[] fileContents = null;
		try {
			fileContents = Files.readAllBytes(pathToFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileContents;
	}
}

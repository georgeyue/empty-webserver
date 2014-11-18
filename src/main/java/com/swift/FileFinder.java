package com.swift;

import java.io.IOException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

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

	public byte[] getFileContents() throws IOException {
		Path pathToFile = this.getAbsoluteFilePath();
		byte[] fileContents = null;
		fileContents = Files.readAllBytes(pathToFile);

		return fileContents;
	}

	public String getFileContentsAsString() throws IOException {
		return new String(getFileContents());
	}
	
	public String getMimeType() throws java.io.IOException {
		FileNameMap fileNameMap = URLConnection.getFileNameMap();
		String type = fileNameMap.getContentTypeFor(getAbsoluteFilePath().toString());
		if(type == null)
			return "text/html";
		return type;
	}
}

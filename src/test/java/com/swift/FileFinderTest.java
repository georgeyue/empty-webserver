package com.swift;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

public class FileFinderTest {

	@Test
	public void shouldNotFindFileThatExistsWhenNotPresent() {
		String fileName = "foobar";
		FileFinder finder = new FileFinder();
		finder.setRootDirectory("/");
		finder.setFile(fileName);
		assertFalse(finder.exists());
	}

	@Test
	public void shouldReturnRootDirectoryAsString() throws Exception {
		FileFinder finder = new FileFinder();
		finder.setRootDirectory("/");
		assertEquals("/", finder.getRootDirectory());
	}
	
	@Test
	public void shouldFindFileThatExists() throws Exception {
		String fileName = "foobar";
		String rootDirectory = new File(FileFinder.class.getProtectionDomain().getCodeSource().getLocation().getPath()).toString();
		FileFinder finder = new FileFinder();
		finder.setFile(fileName);
		System.out.println(rootDirectory);
		finder.setRootDirectory(rootDirectory);
		assertTrue(finder.exists());
	}
}

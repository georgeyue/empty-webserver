package com.swift;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

public class FileFinderTest {
	
	FileFinder finder;
	
	@Before
	public void setUp() {
		finder = new FileFinder();
	}

	@Test
	public void shouldNotFindFileThatExistsWhenNotPresent() {
		File testRootDirectory = new File(FileFinder.class.getProtectionDomain().getCodeSource().getLocation().getPath());
		String rootDirectory = testRootDirectory.getParentFile().getParentFile().toString() + "/src/test/java/com/swift";
		String fileName = "foobaz";
		finder.setRootDirectory(rootDirectory);
		finder.setFile(fileName);
		assertFalse(finder.exists());
	}

	@Test
	public void shouldReturnRootDirectoryAsString() throws Exception {
		finder.setRootDirectory("/");
		assertEquals("/", finder.getRootDirectory());
	}
	
	@Test
	public void shouldFindFileThatExists() throws Exception {
		String fileName = "foobar";
		File testRootDirectory = new File(FileFinder.class.getProtectionDomain().getCodeSource().getLocation().getPath());
		String rootDirectory = testRootDirectory.getParentFile().getParentFile().toString() + "/src/test/java/com/swift";
		finder.setFile(fileName);
		finder.setRootDirectory(rootDirectory);
		assertTrue(finder.exists());
	}
}

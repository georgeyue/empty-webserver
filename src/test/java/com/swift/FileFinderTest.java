package com.swift;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Arrays;

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
	
	@Test
	public void shouldReturnFileContents() throws Exception {
		String LS = System.getProperty("line.separator");
		String fileName = "foobar";
		String fileContents = "foobar" + LS;
		byte[] fileByteContents = fileContents.getBytes();
		
		File testRootDirectory = new File(FileFinder.class.getProtectionDomain().getCodeSource().getLocation().getPath());
		String rootDirectory = testRootDirectory.getParentFile().getParentFile().toString() + "/src/test/java/com/swift";
		
		finder.setRootDirectory(rootDirectory);
		finder.setFile(fileName);
		assertTrue(Arrays.equals(fileByteContents, finder.getFileContents()));
	}
	
	@Test
	public void shouldReturnMimeType() throws Exception {
		String fileName = "foobar.txt";
		File testRootDirectory = new File(FileFinder.class.getProtectionDomain().getCodeSource().getLocation().getPath());
		String rootDirectory = testRootDirectory.getParentFile().getParentFile().toString() + "/src/test/java/com/swift";
		
		finder.setRootDirectory(rootDirectory);
		finder.setFile(fileName);
		assertEquals("text/plain", finder.getMimeType());
	}
	
	@Test
	public void shouldReturnTextPlainMimeIfNoneFound() throws Exception {
		String fileName = "foobar";		
		File testRootDirectory = new File(FileFinder.class.getProtectionDomain().getCodeSource().getLocation().getPath());
		String rootDirectory = testRootDirectory.getParentFile().getParentFile().toString() + "/src/test/java/com/swift";
		
		finder.setRootDirectory(rootDirectory);
		finder.setFile(fileName);
		assertEquals("text/plain", finder.getMimeType());
	}
}

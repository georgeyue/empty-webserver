package com.swift;

import static org.junit.Assert.*;

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
		FileFinder finder = new FileFinder();
		finder.setFile(fileName);
		finder.setRootDirectory("/Users/hothboy/Documents/tm_work/java_training/workspace/empty-webserver/src/test/java/com/swift");
		assertTrue(finder.exists());
	}
}

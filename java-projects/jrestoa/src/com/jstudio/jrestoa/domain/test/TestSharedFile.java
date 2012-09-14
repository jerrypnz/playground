package com.jstudio.jrestoa.domain.test;

import java.util.List;

import org.junit.Test;

import com.jstudio.jrestoa.domain.Department;
import com.jstudio.jrestoa.domain.SharedFile;

import static org.junit.Assert.*;

public class TestSharedFile extends DomainTestBase {

	@Test
	public void testSaveGet() {
		SharedFile file1 = new SharedFile();
		file1.setFileName("mhp2g");
		file1.setFilePath("d:\\123123.rar");
		file1.save();

		SharedFile file2 = SharedFile.findById(file1.getId());
		assertEquals("File not correctly saved", file1, file2);
	}
	
	@Test
	public void testScope() {
		Department dep = new Department();
		dep.setName("server");
		dep.save();

		SharedFile file1 = new SharedFile();
		file1.setFileName("mhp2g");
		file1.setFilePath("d:\\123123.rar");
		file1.save();
		
		SharedFile file2 = new SharedFile();
		file2.setFileName("mhp2ghaha");
		file2.setFilePath("d:\\123123haha.rar");
		file2.setDepartment(dep);
		file2.save();
		
		List<SharedFile> globals = SharedFile.listGlobalFiles();
		List<SharedFile> deps = SharedFile.listDepartmentFiles(dep);
		
		assertTrue("Share file scope error:",globals.contains(file1));
		assertFalse("Share file scope error:",globals.contains(file2));
		
		assertTrue("Share file scope error:",deps.contains(file2));
		assertFalse("Share file scope error:",deps.contains(file1));
	}

}

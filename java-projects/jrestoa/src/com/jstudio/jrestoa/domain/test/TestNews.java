package com.jstudio.jrestoa.domain.test;

import java.util.List;

import org.junit.Test;

import com.jstudio.jrestoa.domain.Department;
import com.jstudio.jrestoa.domain.News;

import static org.junit.Assert.*;

public class TestNews extends DomainTestBase {

	@Test
	public void testSaveGet() {
		News news = new News();
		news.setTitle("Hello");
		news.setDetail("Hello world");
		news.save();

		News news2 = News.findById(news.getId());
		assertEquals("Not the same news ?", news, news2);
	}
	
	@Test
	public void testNewsScope() {
		Department dep = new Department();
		dep.setName("Department");
		dep.save();
		
		News news1 = new News();
		news1.setTitle("Hello");
		news1.setDetail("Hello world");
		news1.setDepartment(dep);
		news1.save();
		
		News news2 = new News();
		news2.setTitle("Hello2");
		news2.setDetail("Hello 2 world");
		news2.save();
		
		List<News> ofGlobal = News.listGlobalNews();
		List<News> ofDep = News.listDepartmentNews(dep);
		
		assertTrue("News scope error",ofGlobal.contains(news2));
		assertTrue("News scope error",ofDep.contains(news1));
		
	}

}

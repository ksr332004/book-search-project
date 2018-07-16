package com.seran.service;

import com.seran.entity.Bookmark;
import com.seran.repository.BookmarkRepository;
import com.seran.service.impl.BookmarkServiceImpl;
import org.junit.Before;
import org.junit.Test;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BookmarkServiceTest {

	private BookmarkRepository repository;
	private BookmarkServiceImpl bookmarkService;

	@Before
	public void prepare() {
		repository = mock(BookmarkRepository.class);
		bookmarkService = new BookmarkServiceImpl(repository);
	}

	@Test
	public void findBookmarkTest() {
		List<Bookmark> bookmarkList = new ArrayList<>();

		Bookmark bookmark1 = new Bookmark();
		bookmark1.setUserId(1);
		bookmark1.setKeyBarcode("123456780");
		bookmark1.setTitle("book title");
		bookmark1.setContents("book contents");
		bookmark1.setUrl("book url");
		bookmark1.setIsbn("123456789");
		bookmark1.setCreateDate(OffsetDateTime.now());
		bookmark1.setAuthors("authors");
		bookmark1.setPublisher("publisher");
		bookmark1.setTranslators("translators");
		bookmark1.setPrice(25000);
		bookmark1.setSalePrice(22000);
		bookmark1.setSaleYN("Y");
		bookmark1.setCategory("category");
		bookmark1.setThumbnail("thumbnail");
		bookmark1.setBarcode("20000100002000");
		bookmark1.setEbookBarcode("20000100002000");
		bookmark1.setStatus("sale");
		bookmarkList.add(bookmark1);

		Bookmark bookmark2 = new Bookmark();
		bookmark2.setUserId(1);
		bookmark2.setKeyBarcode("123456780");
		bookmark2.setTitle("book title");
		bookmark2.setContents("book contents");
		bookmark2.setUrl("book url");
		bookmark2.setIsbn("123456789");
		bookmark2.setCreateDate(OffsetDateTime.now());
		bookmark2.setAuthors("authors");
		bookmark2.setPublisher("publisher");
		bookmark2.setTranslators("translators");
		bookmark2.setPrice(25000);
		bookmark2.setSalePrice(22000);
		bookmark2.setSaleYN("Y");
		bookmark2.setCategory("category");
		bookmark2.setThumbnail("thumbnail");
		bookmark2.setBarcode("20000100002000");
		bookmark2.setEbookBarcode("20000100002000");
		bookmark2.setStatus("sale");
		bookmarkList.add(bookmark2);

		when(repository.findAll()).thenReturn(bookmarkList);
	}
}

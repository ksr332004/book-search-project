package com.seran.repository;

import com.seran.entity.Bookmark;
import com.seran.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BookmarkRepositoryTest {

	@Autowired
	private BookmarkRepository bookmarkRepository;

	@Test
	public void repositorySavesBookmark() {

		Bookmark bookmark = new Bookmark();
		bookmark.setUserId(1);
		bookmark.setKeyBarcode("123456780");
		bookmark.setTitle("book title");
		bookmark.setContents("book contents");
		bookmark.setUrl("book url");
		bookmark.setIsbn("123456789");
		bookmark.setCreateDate(LocalDateTime.now().toString());
		bookmark.setAuthors("authors");
		bookmark.setPublisher("publisher");
		bookmark.setTranslators("translators");
		bookmark.setPrice(25000);
		bookmark.setSalePrice(22000);
		bookmark.setSaleYN("Y");
		bookmark.setCategory("category");
		bookmark.setThumbnail("thumbnail");
		bookmark.setBarcode("20000100002000");
		bookmark.setEbookBarcode("20000100002000");
		bookmark.setStatus("sale");
		Bookmark result = bookmarkRepository.save(bookmark);

		assertEquals(result.getKeyBarcode(), "123456780");
		assertEquals(result.getTitle(), "book title");
		assertEquals(result.getAuthors(), "authors");
	}

}

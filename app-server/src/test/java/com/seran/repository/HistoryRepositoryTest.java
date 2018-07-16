package com.seran.repository;

import com.seran.entity.History;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class HistoryRepositoryTest {

	@Autowired
	private HistoryRepository historyRepository;

	@Test
	public void repositorySavesHistory() {
		History history = new History();
		history.setNewDate(LocalDateTime.now());
		history.setSearch("search history");
		history.setUserId(1);
		History result = historyRepository.save(history);

		assertEquals(result.getSearch(), "search history");
		assertEquals(result.getUserId(), new Integer(1) );
	}
}

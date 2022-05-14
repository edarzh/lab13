package org.suai.lab13.controller;

import org.junit.jupiter.api.Test;
import org.suai.lab13.UserImitator;

import java.util.ArrayList;
import java.util.List;

class NotebookControllerTest {

	@Test
	void test() {
		List<UserImitator> users = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			users.add(new UserImitator());
		}
		for (UserImitator user : users) {
			user.run();
		}

		try {
			Thread.sleep(1000000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}
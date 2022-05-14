package org.suai.lab13;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class UserImitator implements Runnable {
	private static final String GET_NOTEBOOK_URL = "http://localhost:8080/lab13/notebook";
	private static final String ADD_USER_URL = "http://localhost:8080/lab13/notebook/add-user";

	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	@Override
	public void run() {
		scheduler.scheduleWithFixedDelay(new GetNotebook(), 5, 5, TimeUnit.SECONDS);
		scheduler.scheduleWithFixedDelay(new AddUser(), 15, 15, TimeUnit.SECONDS);
	}

	private static class GetNotebook implements Runnable {

		@Override
		public void run() {
			try {
				HttpURLConnection connection = (HttpURLConnection) new URL(GET_NOTEBOOK_URL).openConnection();
				connection.setRequestMethod("GET");

				try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
					StringBuilder response = new StringBuilder();
					String inputLine;

					while ((inputLine = in.readLine()) != null) {
						response.append(inputLine);
					}

					System.out.println(response);
				}

				connection.disconnect();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	private static class AddUser implements Runnable {

		@Override
		public void run() {
			try {
				String name = randomName();
				String parameters = "name=" + name;
				URL url = new URL(ADD_USER_URL);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();

				connection.setDoOutput(true);

				try (OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream())) {
					out.write(parameters);
					out.flush();
					connection.getInputStream();
				}

				connection.disconnect();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		public static String randomName() {
			final int LEFT_LIMIT = 'a';
			final int RIGHT_LIMIT = 'z';
			final int LENGTH = 10;
			ThreadLocalRandom random = ThreadLocalRandom.current();

			return random.ints(LEFT_LIMIT, RIGHT_LIMIT + 1)
					.limit(LENGTH)
					.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
					.toString();
		}
	}
}
package org.suai.lab13.controller;

import org.suai.lab13.repository.NotebookRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

@WebServlet("/notebook/*")
public class NotebookController extends HttpServlet {
	private static final String ADD_USER_URI = "/lab13/notebook/add-user";
	private static final String ADD_NUMBER_URI = "/lab13/notebook/add-number";

	private final NotebookRepository notebookRepository = new NotebookRepository();

	@Override
	public void init() {
		String INITIAL_NOTEBOOK_FILE = "/resources/initial-notebook.json";

		try (BufferedReader br = new BufferedReader(new FileReader(INITIAL_NOTEBOOK_FILE))) {
			String content = br.lines().collect(Collectors.joining()).replaceAll("[{}\":,\\[\\]]", "");
			StringTokenizer tokenizer = new StringTokenizer(content);

			String name = "";
			while (tokenizer.hasMoreTokens()) {
				String token = tokenizer.nextToken();

				if (!isNumber(token)) {
					name = token;
					notebookRepository.addUser(name);
				} else {
					notebookRepository.addNumber(name, token);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Set<Map.Entry<String, List<String>>> notebook = notebookRepository.getCopy().entrySet();
		request.setAttribute("notebook", notebook);
		request.getRequestDispatcher("/jsp/main.jsp").forward(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String uri = request.getRequestURI();

		switch (uri) {
			case ADD_USER_URI -> {
				String name = request.getParameter("name");
				if (name != null) {
					notebookRepository.addUser(name);
				}
				request.getRequestDispatcher("/jsp/add-user.jsp").forward(request, response);
			}
			case ADD_NUMBER_URI -> {
				String name = request.getParameter("name");
				String number = request.getParameter("number");
				if (name != null && number != null) {
					notebookRepository.addNumber(name, number);
				}
				request.getRequestDispatcher("/jsp/add-number.jsp").forward(request, response);
			}
		}
	}

	private static boolean isNumber(String s) {
		return s.chars().allMatch(Character::isDigit);
	}
}
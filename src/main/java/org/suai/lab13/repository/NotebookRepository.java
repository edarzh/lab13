package org.suai.lab13.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class NotebookRepository {
	private final Map<String, List<String>> notebook = new HashMap<>();
	private final ReadWriteLock rwl = new ReentrantReadWriteLock();
	private final Lock readLock = rwl.readLock();
	private final Lock writeLock = rwl.writeLock();

	public void addUser(String name) {
		writeLock.lock();
		try {
			if (!notebook.containsKey(name)) {
				notebook.put(name, new ArrayList<>());
			}
		} finally {
			writeLock.unlock();
		}
	}

	public void addNumber(String name, String number) {
		writeLock.lock();
		try {
			List<String> numberList = notebook.get(name);
			if (numberList != null) {
				numberList.add(number);
			}
		} finally {
			writeLock.unlock();
		}
	}

	public Map<String, List<String>> getCopy() {
		readLock.lock();
		try {
			return new HashMap<>(notebook);
		} finally {
			readLock.unlock();
		}
	}
}
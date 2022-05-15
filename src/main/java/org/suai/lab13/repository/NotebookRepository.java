package org.suai.lab13.repository;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class NotebookRepository {
	private final Map<String, Set<String>> notebook = new HashMap<>();
	private final ReadWriteLock rwl = new ReentrantReadWriteLock();
	private final Lock readLock = rwl.readLock();
	private final Lock writeLock = rwl.writeLock();

	public void addUser(String name) {
		writeLock.lock();
		try {
			notebook.putIfAbsent(name, new HashSet<>());
		} finally {
			writeLock.unlock();
		}
	}

	public void addNumber(String name, String number) {
		writeLock.lock();
		try {
			Set<String> numberList = notebook.get(name);
			if (numberList != null) {
				numberList.add(number);
			} else {
				throw new IllegalArgumentException("User doesn't exist");
			}
		} finally {
			writeLock.unlock();
		}
	}

	public Map<String, Set<String>> getCopy() {
		readLock.lock();
		try {
			return new HashMap<>(notebook);
		} finally {
			readLock.unlock();
		}
	}
}
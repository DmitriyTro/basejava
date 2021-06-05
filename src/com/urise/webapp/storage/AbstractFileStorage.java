package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
	private final File directory;

	protected AbstractFileStorage(File directory) {
		Objects.requireNonNull(directory, " directory must be not null.");
		if (!directory.isDirectory()) {
			throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory.");
		}
		if (!directory.canRead() || !directory.canWrite()) {
			throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
		}
		this.directory = directory;
	}

	@Override
	protected void doUpdate(Resume resume, File file) {
		try {
			doWrite(resume, new BufferedOutputStream(new FileOutputStream(file)));
		} catch (IOException e) {
			throw new StorageException("Write file error.", resume.getUuid(), e);
		}

	}

	protected abstract void doWrite(Resume resume, OutputStream os) throws IOException;

	@Override
	protected void doSave(Resume resume, File file) {
		try {
			file.createNewFile();
		} catch (IOException e) {
			throw new StorageException("Create file error.", file.getName(), e);
		}
		doUpdate(resume, file);
	}

	@Override
	protected void doDelete(File file) {
		if (!file.delete()) {
			throw new StorageException("Delete file error.", file.getName());
		}
	}

	@Override
	protected Resume doGet(File file) {
		try {
			return doRead(new BufferedInputStream(new FileInputStream(file)));
		} catch (IOException e) {
			throw new StorageException("Read file error.", file.getName(), e);
		}
	}

	protected abstract Resume doRead(InputStream is) throws IOException;

	@Override
	protected boolean isExist(File file) {
		return file.exists();
	}

	@Override
	protected File getSearchKey(String uuid) {
		return new File(directory, uuid);
	}

	@Override
	protected List<Resume> doCopyAll() {
		File[] files = directory.listFiles();
		if (files == null) {
			throw new StorageException("Read directory error.", null);
		}
		List<Resume> list = new ArrayList<>(files.length);
		for (File file : files) {
			list.add(doGet(file));
		}
		return list;
	}

	@Override
	public void clear() {
		File[] files = directory.listFiles();
		if (files != null) {
			for (File file : files) {
				doDelete(file);
			}
		}
	}

	@Override
	public int size() {
		String[] list = directory.list();
		if (list == null) {
			throw new StorageException("Size directory error.", null);
		}
		return list.length;
	}
}

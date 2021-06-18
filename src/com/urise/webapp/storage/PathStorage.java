package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.serializer.StreamSerializer;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {
	private final Path directory;
	private final StreamSerializer streamSerializer;

	protected PathStorage(String dir, StreamSerializer streamSerializer) {
		Objects.requireNonNull(dir, " directory must be not null.");

		this.streamSerializer = streamSerializer;
		directory = Paths.get(dir);
		if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
			throw new IllegalArgumentException(dir + " is not directory or is not writable.");
		}
	}

	@Override
	protected void doUpdate(Resume resume, Path path) {
		try {
			streamSerializer.doWrite(resume, new BufferedOutputStream(Files.newOutputStream(path)));
		} catch (IOException e) {
			throw new StorageException("Path write error.", resume.getUuid(), e);
		}
	}

	@Override
	protected void doSave(Resume resume, Path path) {
		try {
			Files.createFile(path);
		} catch (IOException e) {
			throw new StorageException("Path create error." + path, getFileName(path), e);
		}
		doUpdate(resume, path);
	}

	@Override
	protected void doDelete(Path path) {
		try {
			Files.delete(path);
		} catch (IOException e) {
			throw new StorageException("Path delete error.", getFileName(path), e);
		}
	}

	@Override
	protected Resume doGet(Path path) {
		try {
			return streamSerializer.doRead(new BufferedInputStream(Files.newInputStream(path)));
		} catch (IOException e) {
			throw new StorageException("Path read error.", getFileName(path), e);
		}
	}

	@Override
	protected boolean isExist(Path path) {
		return Files.isRegularFile(path);
	}

	@Override
	protected Path getSearchKey(String uuid) {
		return directory.resolve(uuid);
	}

	@Override
	protected List<Resume> doCopyAll() {
		return getDirectoryFiles().map(this::doGet).collect(Collectors.toList());
	}

	@Override
	public void clear() {
		getDirectoryFiles().forEach(this::doDelete);
	}

	@Override
	public int size() {
		return (int) getDirectoryFiles().count();
	}

	private String getFileName(Path path) {
		return path.getFileName().toString();
	}

	private Stream<Path> getDirectoryFiles() {
		try {
			return Files.list(directory);
		} catch (IOException e) {
			throw new StorageException("Error of directory.", e);
		}
	}
}

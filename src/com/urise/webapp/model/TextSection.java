package com.urise.webapp.model;

import java.util.Objects;

public class TextSection extends AbstractSection {
	private final String textContent;

	public TextSection(String textContent) {
		Objects.requireNonNull(textContent, "textContent must not be null");
		this.textContent = textContent;
	}

	public String getTextContent() {
		return textContent;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		TextSection that = (TextSection) o;

		return textContent.equals(that.textContent);
	}

	@Override
	public int hashCode() {
		return textContent.hashCode();
	}

	@Override
	public String toString() {
		return textContent;
	}

}

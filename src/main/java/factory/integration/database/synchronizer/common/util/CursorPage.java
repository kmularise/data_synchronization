package factory.integration.database.synchronizer.common.util;

import java.util.List;

public class CursorPage<T> {
	private final List<T> content;
	private final String nextCursor;
	private final String prevCursor;
	private final boolean hasNext;
	private final boolean hasPrev;

	public CursorPage(List<T> content, String nextCursor, String prevCursor, boolean hasNext, boolean hasPrev) {
		this.content = content;
		this.nextCursor = nextCursor;
		this.prevCursor = prevCursor;
		this.hasNext = hasNext;
		this.hasPrev = hasPrev;
	}

	public List<T> getContent() {
		return content;
	}

	public String getNextCursor() {
		return nextCursor;
	}

	public String getPrevCursor() {
		return prevCursor;
	}

	public boolean hasNext() {
		return hasNext;
	}

	public boolean hasPrev() {
		return hasPrev;
	}

	@Override
	public String toString() {
		return "CursorPage{" +
			"content=" + content +
			", nextCursor='" + nextCursor + '\'' +
			", prevCursor='" + prevCursor + '\'' +
			", hasNext=" + hasNext +
			", hasPrev=" + hasPrev +
			'}';
	}
}

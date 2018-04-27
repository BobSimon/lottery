package global.hh.algorithms.lottery.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.SerializationException;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.Validate;

public abstract class SerializationListUtils extends SerializationUtils {
	public static <T extends Serializable> List<T> deepCopy(List<T> src) {
		return deserializeList(serializeList(src));
	}

	public static <T extends Serializable> byte[] serializeList(List<T> src) {
		final ByteArrayOutputStream baos = new ByteArrayOutputStream(512);
		serializeList(src, baos);
		return baos.toByteArray();
	}

	public static <T extends Serializable> void serializeList(List<T> src, final OutputStream outputStream) {
		Validate.isTrue(outputStream != null, "The OutputStream must not be null");
		try (ObjectOutputStream out = new ObjectOutputStream(outputStream)) {
			out.writeObject(src);
		} catch (final IOException ex) {
			throw new SerializationException(ex);
		}
	}

	public static <T extends Serializable> List<T> deserializeList(final byte[] objectData) {
		Validate.isTrue(objectData != null, "The byte[] must not be null");
		return SerializationUtils.deserialize(new ByteArrayInputStream(objectData));
	}

	public static <T extends Serializable> List<T> deserializeList(final InputStream inputStream) {
		Validate.isTrue(inputStream != null, "The InputStream must not be null");
		try (ObjectInputStream in = new ObjectInputStream(inputStream)) {
			@SuppressWarnings("unchecked")
			final List<T> dest = (List<T>) in.readObject();
			return dest;
		} catch (final ClassNotFoundException | IOException ex) {
			throw new SerializationException(ex);
		}
	}
}

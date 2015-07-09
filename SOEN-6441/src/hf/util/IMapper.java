package hf.util;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * IMapper<T> interface describes a contract for implementing the loading and
 * saving of a Java InputStream/OuputStream, from and to (respectively) an application object
 * 
 * @param <T> The object type to be made persistent
 * 
 */
public interface IMapper<T> {

	/**
	 * Loading from a InputStream
	 * 
	 * @param input The inputStream
	 * @return
	 */
	public T load(InputStream input);

	/**
	 * Saving of an application object to the OutputStream
	 * 
	 * @param object The object to be made persistent
	 * @param output The OutputStream
	 */
	public void save(T object, OutputStream output);
}

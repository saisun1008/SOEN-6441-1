package hf.util;

import hf.game.GameBoard;
import hf.game.items.Player;

import java.io.InputStream;
import java.io.OutputStream;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.StreamException;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.thoughtworks.xstream.mapper.MapperWrapper;

/**
 * A skeleton class for mapping Java InputStream/OutputStream to xStream XML
 * format and applications objects
 * 
 * @param <T>
 *            Typically one of GameBoard, Player
 * @see Player
 * @see GameBoard
 * @author Sai
 * 
 */
public class XmlMapper<T> implements IMapper<T>
{

    private XStream xstream;

    /**
     * Constructor
     */
    public XmlMapper()
    {
        // ignore observers object when serialize
        this.xstream = new XStream(new StaxDriver())
        {
            @Override
            protected MapperWrapper wrapMapper(MapperWrapper next)
            {
                return new MapperWrapper(next)
                {
                    @Override
                    @SuppressWarnings("rawtypes")
                    public boolean shouldSerializeMember(Class definedIn,
                            String fieldName)
                    {
                        if (fieldName.equals("observers")
                                || fieldName.equals("entities")
                                || fieldName.equals("m_entities")
                                || fieldName.equals("tmpPlayer")
                                || fieldName.equals("wantedColor"))
                        {
                            return false;
                        }

                        return super
                                .shouldSerializeMember(definedIn, fieldName);
                    }
                };
            }
        };
        ;
    }

    /**
     * Map a Java InputStream (say, from a loaded file) into an xStream object
     * then to XML then cast to a game object
     * 
     * @return One of the Object types (current Player, World or Level)
     */
    @SuppressWarnings("unchecked")
    public T load(InputStream input)
    {
        try
        {
            return (T) xstream.fromXML(input);
        } catch (StreamException e)
        {
            return null;
        }
    }

    /**
     * Save a given application object to file via a Java OutputStream and
     * xStream
     */
    public void save(T object, OutputStream output)
    {
        xstream.toXML(object, output);
    }
}

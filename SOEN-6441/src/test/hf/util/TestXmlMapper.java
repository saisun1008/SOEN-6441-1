package test.hf.util;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.junit.Test;

import hf.util.XmlMapper;

/**
 * XmlMapper Test case in util package
 * 
 * @author Sai
 *
 */

public class TestXmlMapper
{
    private class SmallMock
    {
        public int value = 2;
        public boolean isTrue = true;
    }

    /**
     * init the xml file
     * 
     * @param value
     *            test value
     * @param isTrue
     *            test result
     * @return simple xml file
     */
    
    private String xmlMock(int value, boolean isTrue)
    {
        return "<?xml version=\"1.0\" ?><test.hf.util.TestXmlMapper_-SmallMock>"
                + "<value>"
                + value
                + "</value>"
                + "<isTrue>"
                + isTrue
                + "</isTrue><outer-class></outer-class>"
                + "</test.hf.util.TestXmlMapper_-SmallMock>";
    }

    @Test
    public void willLoadXmlFileAsObject()
    {
        ByteArrayInputStream in = new ByteArrayInputStream(xmlMock(3, false)
                .getBytes());
        SmallMock mock = new XmlMapper<SmallMock>().load(in);
        assertEquals(3, mock.value);
        assertEquals(false, mock.isTrue);
    }

    @Test
    public void willSaveObjectAsXmlFile()
    {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        new XmlMapper<SmallMock>().save(new SmallMock(), out);
        assertEquals(xmlMock(2, true), new String(out.toByteArray()));
    }
}

package hf.ui;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

/**
 * This is a logging panel for the UI. It records various events throughout the
 * application. - Should be used for debugging or showing the prof what's going
 * on in the app -
 * 
 * @author Sai
 * 
 */
public class LogView extends JPanel
{

    private static final long serialVersionUID = -2598815322167002189L;
    private JTextArea textArea;
    private JScrollPane scrollPane;

    /**
     * Constructor - Build and show this panel
     */
    public LogView()
    {
        this.textArea = new JTextArea();
        this.scrollPane = new JScrollPane(textArea);
        textArea.setEditable(false);
        scrollPane.setFocusable(true);
        scrollPane
                .setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        textArea.setFocusable(true);
        setLayout(new GridLayout(1, 1));
        add(scrollPane);
    }

    /**
     * Append a string to the log view
     * 
     * @param msg
     *            The message to display in the log text area
     */
    public void append(String msg)
    {
        log(msg);
    }

    /**
     * Append a string to the log. Keep the scroll bar to the bottom of the text
     * area and don't keep more than a specified maximum number of lines
     * 
     * @param msg
     *            The message to display in the log text area
     */
    public void log(String msg)
    {
        try
        {
            textArea.append(msg + "\n");

            // Keep the scroll bar at the bottom
            JScrollBar sb = scrollPane.getVerticalScrollBar();
            sb.setValue(sb.getMaximum());
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

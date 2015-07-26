package hf.game.views;

import hf.game.GameBoard;
import hf.game.common.ColorEnum;
import hf.game.common.GameProperties;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import junit.textui.ResultPrinter;

public class SelectionView extends JPanel
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JButton btn;
    private GameView parent;
    private GameBoard board;
    private ArrayList<JCheckBox> checkboxes;
    private JButton exchangeBtn;
    private JLabel resultLabel = new JLabel();
    private String exchangeType;
    private ColorEnum cardToGiveback;
    private ColorEnum cardToTake;

    public SelectionView(GameView gameView, GameBoard board)
    {
        setLayout(new GridLayout(1, 1));

        parent = gameView;
        this.board = board;
    }

    public void setBoard(GameBoard board)
    {
        this.board = board;
    }

    private void addCloseBtn()
    {
        btn = new JButton("Close");
        btn.setSize(100, 30);
        btn.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                showPanel(false);
                parent.getGameCanvas().requestFocus();
            }
        });
        add(btn);
    }

    private void addExchangeBtn()
    {
        exchangeBtn = new JButton("Exchange");
        exchangeBtn.setSize(100, 30);
        exchangeBtn.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (exchangeType.equals("L_TO_D"))
                {
                    if (doLanternExchange())
                    {
                        resultLabel.setText("Exchange successful");
                    } else
                    {
                        resultLabel.setText("Exchange failed");
                    }

                } else if (exchangeType.equals("F_TO_L"))
                {
                    if (doFavorExchange())
                    {
                        resultLabel.setText("Exchange successful");
                    } else
                    {
                        resultLabel.setText("Exchange failed");
                    }
                }
            }
        });
        add(exchangeBtn);
    }

    public void showPanel(boolean visible)
    {
        this.setVisible(visible);
        parent.getGameCanvas().requestFocus();
    }

    public void buildByType(String selectionType)
    {
        checkboxes = new ArrayList<JCheckBox>();
        exchangeType = selectionType;
        if (selectionType.equals("L_TO_D"))
        {
            HashMap<ColorEnum, ArrayList<Integer>> list = board
                    .getCurrentRoundPlayer().getLanternList();
            this.removeAll();
            setLayout(new GridLayout(list.size() + 4, 1));
            JTextArea l1 = new JTextArea(
                    "Exchange Dedication Token, each \ntype of exchange must be done in turn.");
            l1.setSize(this.getWidth(), this.getHeight());
            l1.setLineWrap(true);
            add(l1);

            for (ColorEnum key : list.keySet())
            {
                JCheckBox checkbox = new JCheckBox(key + ":"
                        + list.get(key).size());
                checkbox.setSelected(false);
                checkbox.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        if (checkbox.getText().split(":")[1].equals("0"))
                        {
                            checkbox.setSelected(false);
                        }
                    }
                });
                add(checkbox);
                checkboxes.add(checkbox);
            }

        } else if (selectionType.equals("F_TO_L"))
        {
            HashMap<ColorEnum, ArrayList<Integer>> list = board
                    .getCurrentRoundPlayer().getLanternList();
            this.removeAll();
            setLayout(new GridLayout(5, 0));
            JTextArea l1 = new JTextArea(
                    "Exchange Lantern Card, each \ntype of exchange must be done in turn.");
            l1.setLineWrap(true);
            l1.setSize(this.getWidth(), this.getHeight());
            add(l1);
            JPanel midPanel = new JPanel();
            midPanel.setLayout(new GridLayout(0, 2));
            JPanel m1 = new JPanel();
            m1.setLayout(new GridLayout(7, 0));
            JPanel m2 = new JPanel();
            m2.setLayout(new GridLayout(7, 0));
            ButtonGroup group1 = new ButtonGroup();
            // build ratio btns for player lantern
            for (ColorEnum key : list.keySet())
            {
                JRadioButton radionBtn = new JRadioButton(key + ":"
                        + list.get(key).size());
                radionBtn.setSelected(false);
                radionBtn.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        if (radionBtn.isSelected()
                                && Integer.parseInt(radionBtn.getText().split(
                                        ":")[1]) >= 1)
                        {
                            cardToGiveback = stringToEnum(radionBtn.getText()
                                    .split(":")[0]);
                        }
                    }
                });
                group1.add(radionBtn);
                m1.add(radionBtn);
            }

            // build ratio btns for deck
            ButtonGroup group2 = new ButtonGroup();
            for (ColorEnum key : board.getLatternDecks().keySet())
            {
                JRadioButton radionBtn = new JRadioButton(key + ":"
                        + board.getLatternDecks().get(key).size());
                radionBtn.setSelected(false);
                radionBtn.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        if (radionBtn.isSelected()
                                && Integer.parseInt(radionBtn.getText().split(
                                        ":")[1]) >= 1)
                        {
                            cardToTake = stringToEnum(radionBtn.getText()
                                    .split(":")[0]);
                        }
                    }
                });
                group2.add(radionBtn);
                m2.add(radionBtn);
            }
            midPanel.add(m1);
            midPanel.add(m2);
            add(midPanel);

        }

        addExchangeBtn();
        addCloseBtn();
        resultLabel.setSize(this.getWidth(), 30);
        add(resultLabel);
        if (board.getCurrentRoundPlayer().getFavorTokenList().size() < 2
                && selectionType.equals("F_TO_L"))
        {
            resultLabel.setText("Insuffcient Favor Token");
        }

    }

    private boolean doLanternExchange()
    {
        // first count how many kinds of lantern cards are selected
        int cnt = 0;
        boolean result = false;
        HashMap<ColorEnum, Integer> exchangeList = new HashMap<>();
        for (JCheckBox box : checkboxes)
        {
            if (box.isSelected())
            {
                cnt++;
                ColorEnum color = stringToEnum(box.getText().split(":")[0]);

                exchangeList.put(color,
                        Integer.parseInt(box.getText().split(":")[1]));
            }
        }

        if (cnt == 1)
        {
            // user is trying to do a four of a kind exchange
            result = board.exchangeFourOfKind(exchangeList);
            // update text
            if (result)
            {
                buildByType("L_TO_D");
                resultLabel.setText("Exchange Successful");
            }
        } else if (cnt == 3)
        {
            // three pair exchange
            result = board.exchangeThreePair(exchangeList);
            if (result)
            {
                buildByType("L_TO_D");
                resultLabel.setText("Exchange Successful");
            }
        } else if (cnt == 7)
        {
            // seven unique exchange
            result = board.exchangeSevenUnique(exchangeList);
            if (result)
            {
                buildByType("L_TO_D");
                resultLabel.setText("Exchange Successful");
            }
        } else
        {
            resultLabel
                    .setText("Please re-select a valid combination for exchange");
        }
        return result;
    }

    private ColorEnum stringToEnum(String color)
    {
        switch (color)
        {
        case "RED":
            return ColorEnum.RED;
        case "ORANGE":
            return ColorEnum.ORANGE;
        case "GREEN":
            return ColorEnum.GREEN;
        case "PURPLE":
            return ColorEnum.PURPLE;
        case "WHITE":
            return ColorEnum.WHITE;
        case "BLUE":
            return ColorEnum.BLUE;
        case "BLACK":
            return ColorEnum.BLACK;

        default:
            return ColorEnum.BLACK;
        }
    }

    private boolean doFavorExchange()
    {
        if (board.getCurrentRoundPlayer().getFavorTokenList().size() < 2)
        {
            return false;
        } else
        {
            boolean ret = board.useFavorToken(cardToGiveback, cardToTake);
            buildByType("F_TO_L");
            return ret;
        }
    }
}

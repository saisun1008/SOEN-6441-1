package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;







//import test.hf.controller.MatrixCalculatorTest;
import test.hf.game.TestBoardBuilder;
import test.hf.game.TestGameBoard;
import test.hf.game.common.TestCardType;
import test.hf.game.common.TestColor;
import test.hf.game.items.TestGameCard;
import test.hf.game.items.TestGamePlayer;
import test.hf.util.TestXmlMapper;

@RunWith(Suite.class)
@SuiteClasses(
{ TestBoardBuilder.class, TestXmlMapper.class, TestGameCard.class, TestGamePlayer.class,TestGameBoard.class,TestCardType.class,TestColor.class })
public class TestSuite
{

}
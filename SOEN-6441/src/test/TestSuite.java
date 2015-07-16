package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import test.hf.controller.MatrixCalculatorTest;
import test.hf.game.TestBoardBuilder;
import test.hf.util.TestXmlMapper;

@RunWith(Suite.class)
@SuiteClasses(
{ MatrixCalculatorTest.class, TestBoardBuilder.class, TestXmlMapper.class })
public class TestSuite
{

}
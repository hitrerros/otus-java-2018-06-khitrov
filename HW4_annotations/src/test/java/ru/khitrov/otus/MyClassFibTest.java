package ru.khitrov.otus;
import ru.khitrov.otus.annotations.*;

public class MyClassFibTest {

@Before
public void beforeStart()  {
    System.out.println("before Test Fibonacci number  ");
}

@Test
public void testIssue1() {
  MyClassFib myClassFib = new MyClassFib();
  TUnitCore.assertEquals(  1, myClassFib.getFibonacci( 2 ) );
  System.out.println("done");
}


@Test
public void testIssue2() {
    MyClassFib myClassFib = new MyClassFib();
    TUnitCore.assertEquals(  8, myClassFib.getFibonacci( 6 ) );
    System.out.println("done");
}

@After
public void afterEndOfTest()  {
    System.out.println("end of test Fibonacci number = " );


}


}

package ru.khitrov.otus;

import ru.khitrov.otus.annotations.After;
import ru.khitrov.otus.annotations.Before;
import ru.khitrov.otus.annotations.Test;

public class MyClassNumTest {

    @Before
    public void beforeStart()  {
        System.out.println("before Test just number  ");
    }

    @Test
    public void testIssue1() {
        MyClassNum myClassNum = new MyClassNum();
        TUnitCore.assertEquals(  3, myClassNum.getNumber() );
        System.out.println("done");
    }



    @After
    public void afterEndOfTest()  {
        System.out.println("end of test just number = " );


    }


}

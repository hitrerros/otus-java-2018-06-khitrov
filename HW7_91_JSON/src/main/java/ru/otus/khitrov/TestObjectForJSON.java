package ru.otus.khitrov;

import java.util.Objects;

public class TestObjectForJSON {

   final private int member;
   final private String str;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestObjectForJSON that = (TestObjectForJSON) o;
        return member == that.member &&
                Objects.equals(str, that.str);
    }

    @Override
    public int hashCode() {
        return Objects.hash(member, str);
    }

    TestObjectForJSON(int member, String str ){

       this.member = member;
       this.str    = str;
   }
}

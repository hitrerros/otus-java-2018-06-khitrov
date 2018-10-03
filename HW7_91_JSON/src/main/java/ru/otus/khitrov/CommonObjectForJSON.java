package ru.otus.khitrov;

import java.util.*;

public class CommonObjectForJSON {

private List<String> listInt = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommonObjectForJSON that = (CommonObjectForJSON) o;
        return counter == that.counter &&
                primitiveInt == that.primitiveInt &&
                Objects.equals(listInt, that.listInt) &&
                Objects.equals(mapObj, that.mapObj) &&
                Objects.equals(justObj, that.justObj) &&
                Arrays.equals(arrayInt, that.arrayInt) &&
                Objects.equals(listObj, that.listObj) &&
                Arrays.equals(arrayOb, that.arrayOb);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(listInt, mapObj, counter, primitiveInt, justObj, listObj);
        result = 31 * result + Arrays.hashCode(arrayInt);
        result = 31 * result + Arrays.hashCode(arrayOb);
        return result;
    }

    private Map<String,TestObjectForJSON> mapObj = new HashMap<>();

transient  private int counter = 2;
private final int primitiveInt = 5;
private TestObjectForJSON justObj = new TestObjectForJSON( counter,"just object" );
private int[] arrayInt = new int[counter];
private List<TestObjectForJSON> listObj = new ArrayList<>();
private TestObjectForJSON[] arrayOb = new TestObjectForJSON[counter];


CommonObjectForJSON(){

   for (int i=0;i < counter; i++){
       arrayInt[i] = i;

        listInt.add(String.valueOf(i));
        mapObj.put(String.valueOf(i), new TestObjectForJSON(i,"map object"));
        arrayOb[i] = new TestObjectForJSON(i,"dummy2");
        listObj.add( new TestObjectForJSON(i*2,"list object"));

   }

 }

}

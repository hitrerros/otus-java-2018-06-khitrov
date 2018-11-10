import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
public class Main {

    private static final int NUMBER_OF_THREADS = 4;

    public static void main(String...args) {
          generateArrayAndSort();
    }

    private static void generateArrayAndSort() {

        int arrSize = 1000;
        List<Integer> srcList = new ArrayList<>();
        List<Integer> oneThreadList = new ArrayList<>();

        for (int i=0;i< arrSize; i++) {
            srcList.add( ThreadLocalRandom.current().nextInt(0, 1000 ) );
        }

        oneThreadList.addAll( srcList );
        Collections.sort( oneThreadList );

        System.out.println("initial array");
        System.out.println( srcList.stream()
                            .map(s->s.toString())
                            .collect(Collectors.joining(",")) );

        List<Integer>   multiThreadList = new ArrayList<>();

        try {
            multiThreadList = SortFactory.getSortedArray( srcList, NUMBER_OF_THREADS );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("sorted array");
        System.out.println( multiThreadList.stream()
                                  .map(s->s.toString())
                                  .collect(Collectors.joining(",")) );


        System.out.println( "Lists equal: " + CollectionUtils.isEqualCollection(  oneThreadList, multiThreadList  ));



    }

}
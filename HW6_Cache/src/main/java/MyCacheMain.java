import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

public class MyCacheMain {

    public static void main(String... args) throws InterruptedException {

        int size = 10;

        MyCacheEngine<Integer, MyClassForTest> cacheEngine = new MyCacheEngineImpl<>(size, 100, 0, false);
//        MyCacheEngine<Integer,MyClassForTest> cacheEngine = new MyCacheEngineImpl<>( size, 0,0,true );

        for (int i = 0; i < size; i++) {
            cacheEngine.put(i, new MyClassForTest(i));
        }


        for (int i = 0; i < size; i++) {
            MyClassForTest element = cacheEngine.get(i);
            System.out.println("String for " + i + ": " + (element != null ? element.getIntVar() : "null"));
        }

        System.out.println("Cache hits: " + cacheEngine.getHitCount());
        System.out.println("Cache misses: " + cacheEngine.getMissCount());

        cacheEngine.dispose();


    }

}

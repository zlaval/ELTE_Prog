package hjava210520;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


class AnnotatedMethods {

    void noAnnotation() {

    }

    @FirstAnnotation
    void oneAnnotation() {

    }

    @FirstAnnotation
    @SecondAnnotation
    void twoAnnotation() {

    }

    @FirstAnnotation
    @ThirdAnnotation
    void twoAnnotationTwo() {

    }

    @FirstAnnotation
    @SecondAnnotation
    @ThirdAnnotation
    void threeAnnotation() {

    }

}

@RunWith(Parameterized.class)
public class AnnotTest {

    @Parameterized.Parameters(name = "{index}: test")
    public static Collection<Object[]> testData() {
        return Arrays.asList(new Object[][]{
                {AnnotatedMethods.class, 0, List.of("noAnnotation")},
                {AnnotatedMethods.class, 1, List.of("oneAnnotation")},
                {AnnotatedMethods.class, 2, List.of("twoAnnotation", "twoAnnotationTwo")},
        });
    }

    private Class clazz;
    private int count;
    private List<String> methodNames;

    public AnnotTest(Class clazz, int count, List<String> methodNames) {
        this.clazz = clazz;
        this.count = count;
        this.methodNames = methodNames;
    }

    @Test
    public void runtest() {
        var result = AnnotMain.methodsWithAnnotationCount.apply(clazz, count);
        for (int i = 0; i < result.size(); ++i) {
            Method m = result.get(i);
            assertEquals(methodNames.size(), result.size());
            assertTrue(methodNames.contains(m.getName()));
        }

    }


}

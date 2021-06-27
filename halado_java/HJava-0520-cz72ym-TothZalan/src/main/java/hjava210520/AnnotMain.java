package hjava210520;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;


public class AnnotMain {

    public static BiFunction<Class, Integer, List<Method>> methodsWithAnnotationCount = (Class clazz, Integer count) ->
            Arrays.stream(clazz.getDeclaredMethods()).filter(m -> m.getAnnotations().length == count).collect(Collectors.toList());


}

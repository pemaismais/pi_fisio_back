package app.pi_fisio.helper;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Component
// https://stackoverflow.com/questions/17417345/beanutils-copyproperties-api-to-ignore-null-and-specific-propertie
public class CopyPropertiesUtil {
    public static void copyNonNullProperties(Object src, Object target) throws IllegalAccessException {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }

    public static String[] getNullPropertyNames(Object source) throws IllegalAccessException{
        List<String> nullValuePropertyNames = new ArrayList<>();
        Field[] fields = source.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true); // Torna o campo acessível, mesmo que seja privado
                if (field.get(source) == null) {
                    nullValuePropertyNames.add(field.getName());
                }
        }
        return nullValuePropertyNames.toArray(new String[0]);
    }
}
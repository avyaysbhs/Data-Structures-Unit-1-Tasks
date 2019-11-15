package assignments;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.lang.reflect.Field;

public class Builder<T>
{
    private T object;
    private Class<? extends T> objectClass;

    public Builder(Class<? extends T> objectClass)
    {
        this.objectClass = objectClass;
    }

    public Builder<T> create()
    {
        try {
            object = objectClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return this;
    }

    public Builder<T> set(String key, Object value)
    {
        try {
            Field field = objectClass.getDeclaredField(key);
            boolean wasAccessible = field.isAccessible();

            field.setAccessible(true);
            field.set(object, value);
            field.setAccessible(wasAccessible);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        return this;
    }

    public <F> F get(String fieldName, Class<? extends F> fieldType, T target)
    {
        try {
            Field field = objectClass.getDeclaredField(fieldName);
            boolean wasAccessible = field.isAccessible();
            try {
                field.setAccessible(true);
                return (F) field.get(target);
            } finally {
                field.setAccessible(wasAccessible);
            }
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }

    public T construct()
    {
        return object;
    }

    public T constructAndDispose() {
        T product = construct(); dispose();
        return product;
    }

    public void dispose()
    {
        object = null;
    }
}

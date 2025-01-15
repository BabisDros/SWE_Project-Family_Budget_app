package com.android.familybudgetapp.utilities;

import java.util.Objects;

public class Tuples<T1,T2> {
    private final T1 val1;
    private final T2 val2;


    /**
     * initialize 2 generic parameters
     * @param val1 T1
     * @param val2 T2
     */
    public Tuples(T1 val1, T2 val2)
    {
        this.val1 = val1;
        this.val2 = val2;
    }

    /**
     * @return first generic value
     */
    public T1 getFirst()
    {
        return val1;
    }

    /**
     * @return second generic value
     */
    public T2 getSecond()
    {
        return val2;
    }

    /**
     * Compares this object with the specified object for equality. This method checks if the current
     * object and the specified object are of the same class and have equal first and second generic values.
     *
     * @param o the object to compare with this instance
     * @return {@code true} if the specified object is equal to this instance; {@code false} otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tuples<T1, T2> other = (Tuples<T1, T2>) o;
        return Objects.equals(getFirst(), other.getFirst()) &&
                Objects.equals(getSecond(), other.getSecond());
    }
}

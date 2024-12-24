package com.android.familybudgetapp.utilities;

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
}

package com.android.familybudgetapp.utilities;

/** Group 4 generic variables
 * @param <T1>
 * @param <T2>
 * @param <T3>
 * @param <T4>
 */
public class Quadruples<T1,T2,T3,T4> {
    private final T1 val1;
    private final T2 val2;
    private final T3 val3;
    private final T4 val4;


    /**
     * initialize 4 generic parameters
     * @param val1 T1
     * @param val2 T2
     * @param val3 T3
     * @param val4 T4
     */
    public Quadruples(T1 val1, T2 val2, T3 val3, T4 val4)
    {
        this.val1 = val1;
        this.val2 = val2;
        this.val3 = val3;
        this.val4 = val4;
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
     * @return third generic value
     */
    public T3 getThird()
    {
        return val3;
    }

    /**
     * @return fourth generic value
     */
    public T4 getFourth()
    {
        return val4;
    }
}

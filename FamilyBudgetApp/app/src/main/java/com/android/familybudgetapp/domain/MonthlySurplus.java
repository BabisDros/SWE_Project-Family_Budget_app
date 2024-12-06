package com.android.familybudgetapp.domain;
import java.time.LocalDateTime;

public class MonthlySurplus
{
    private LocalDateTime date;
    private int surplus;

    public MonthlySurplus(int surplus)
    {
        this.date = LocalDateTime.now();
    }

    public LocalDateTime getDate()
    {
        return date;
    }

    public int getSurplus()
    {
        return surplus;
    }

    public void setDate(LocalDateTime datetime)
    {
        if(datetime==null)
        {
            throw  new IllegalArgumentException("datetime shouldn't be null");
        }
        this.date = datetime;
    }

    public void addSurplus(int amount)
    {
        this.surplus+=amount;
    }
}

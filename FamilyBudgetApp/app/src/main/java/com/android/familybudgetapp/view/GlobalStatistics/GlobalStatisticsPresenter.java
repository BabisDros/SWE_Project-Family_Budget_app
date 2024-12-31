package com.android.familybudgetapp.view.GlobalStatistics;

import com.android.familybudgetapp.dao.FamilyDAO;
import com.android.familybudgetapp.domain.Family;
import com.android.familybudgetapp.domain.Income;
import com.android.familybudgetapp.domain.MonthlySurplus;
import com.android.familybudgetapp.utilities.Quadruples;
import com.android.familybudgetapp.utilities.Tuples;

import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class GlobalStatisticsPresenter {

    FamilyDAO familyDAO;

    GlobalStatisticsPresenter(FamilyDAO familyDAO)
    {
        this.familyDAO = familyDAO;
    }


    public List<Tuples<YearMonth, Double>> getMonthlyStat() {
        Set<Family> families = familyDAO.findAll();
        List<Tuples<YearMonth, Double>> myList = new ArrayList<>();

        Optional<YearMonth> minDate = Optional.of(YearMonth.now());
        Optional<YearMonth> newMinDate;
        // get earliest Date of a surplus of any family
        for (Family family: families)
        {
            newMinDate = family.getMonthlySurpluses().keySet().stream().min(YearMonth::compareTo);
            if (newMinDate.isEmpty())
                continue;
            if (newMinDate.get().isBefore(minDate.get()))
                minDate = newMinDate;
        }
        YearMonth currentDate = minDate.get();

        while (!currentDate.equals(YearMonth.now()))
        {
            double avg;
            int count = 0;
            int total = 0;
            for (Family family: families)
            {
                Map<YearMonth, MonthlySurplus> surpluses = family.getMonthlySurpluses();
                if (surpluses.containsKey(currentDate))
                {
                    if (surpluses.get(currentDate).getSurplus() > 0)
                        count++;
                    total++;
                }
            }
            avg = (double)count/total * 100;
            myList.add(new Tuples<>(currentDate, avg));
            currentDate = currentDate.plusMonths(1);
        }
        return myList;
    }

    public List<Quadruples<YearMonth, Double, Double, Double>> getYearlyStat() {
        Set<Family> families = familyDAO.findAll();
        List<Quadruples<YearMonth, Double, Double, Double>> myList = new ArrayList<>();

        Optional<YearMonth> minDate = Optional.of(YearMonth.now());
        Optional<YearMonth> newMinDate;
        // get earliest Date of a surplus of any family
        for (Family family: families)
        {
            newMinDate = family.getMonthlySurpluses().keySet().stream().min(YearMonth::compareTo);
            if (newMinDate.isEmpty())
                continue;
            if (newMinDate.get().isBefore(minDate.get()))
                minDate = newMinDate;
        }
        YearMonth currentDate = minDate.get();

        Map<Long, Integer> yearlySavings = new HashMap<>();
        while (!currentDate.equals(YearMonth.now()))
        {
            for (Family family: families)
            {
                Map<YearMonth, MonthlySurplus> surpluses = family.getMonthlySurpluses();
                if (surpluses.containsKey(currentDate)) // add monthly saving to total for this year
                    yearlySavings.put(family.getID(), yearlySavings.getOrDefault(family.getID(), 0) + surpluses.get(currentDate).getSurplus());
            }
            if (currentDate.getMonth().equals(Month.DECEMBER))
            {
                int countOver1k = 0;
                int countOver5k = 0;
                int countOver10k = 0;
                for(Integer saving: yearlySavings.values())
                {
                    if (saving >= 10000 * 100)
                        countOver10k++;
                    if (saving >= 5000 * 100)
                        countOver5k++;
                    if (saving >= 1000 * 100)
                        countOver1k++;
                }
                double size = yearlySavings.size();
                myList.add(new Quadruples<>(currentDate, countOver1k/ size * 100, countOver5k/ size * 100, countOver10k/ size * 100));
            }
            currentDate = currentDate.plusMonths(1);
        }

        return myList;
    }
}

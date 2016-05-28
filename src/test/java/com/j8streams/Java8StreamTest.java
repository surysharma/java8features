package com.j8streams;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static java.util.Arrays.asList;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.core.Is.is;

/**
 * Created by sureshsharma on 27/05/2016.
 */
public class Java8StreamTest {

    Trader raul = new Trader("Raul", "Cambridge");
    Trader mario = new Trader("Mario", "Milan");
    Trader alan = new Trader("Alan", "Cambridge");
    Trader brian = new Trader("Brian", "Cambridge");

    Transaction brianTransaction =  new Transaction(brian, 2011, 300);
    Transaction raulTransaction1 =  new Transaction(raul, 2012, 1000);
    Transaction raulTransaction2 =  new Transaction(raul, 2011, 400);
    Transaction marioTransaction1 =  new Transaction(mario, 2012, 710);
    Transaction marioTransaction2 =  new Transaction(mario, 2012, 700);
    Transaction alanTransaction1 =  new Transaction(alan, 2012, 950);

    List<Transaction> transactions;
    @Before
    public void setup(){
        // given
        transactions = asList(
                brianTransaction,
                raulTransaction1,
                raulTransaction2,
                marioTransaction1,
                marioTransaction2,
                alanTransaction1

        );
    }

    @Test
    public void should_find_all_transactions_in_year(){
        // given
        int year = 2011;

        // when
        List<Transaction> transactionsResult = transactions.stream()
                .filter(transaction -> transaction.getYear() == year)
                .collect(toList());

        // then
        assertThat(transactionsResult, is(asList(brianTransaction, raulTransaction2)));
    }

    @Test
    public void should_list_unique_cities_where_trader_works(){
        //when
        List<String> uniqueCities = transactions.stream()
                .map(transaction -> transaction.getTrader().getCity())
                .distinct()
                .collect(toList());

        //then
        assertThat(uniqueCities, is(asList("Cambridge", "Milan")));
    }

    @Test
    public void should_find_all_traders_from_city_and_sort_by_name(){
        // when
        String city_name = "Cambridge";

        List<Trader> collect = transactions.stream()
                .map(Transaction::getTrader)
                .filter(trader -> trader.getCity().equalsIgnoreCase(city_name))
                .sorted((t1, t2) -> t1.getName().compareTo(t2.getName()))
                .distinct()
                .collect(toList());

        // then
        assertThat(collect, is(Matchers.contains(alan, brian, raul)));
    }

    @Test
    public void should_list_trader_names_alpabatically(){
        // when
        List<String> tradersWithNames = transactions.stream()
                .map(Transaction::getTrader)
                .map(Trader::getName)
                .sorted(String::compareTo)
                .distinct()
                .collect(toList());

        // then
        assertThat(tradersWithNames, is(asList("Alan", "Brian", "Mario", "Raul")));
    }

    @Test
    public void should_check_trader_city(){
        // given
        String  city = "Milan";

        // when
        boolean isPresent = transactions.stream()
                .map(Transaction::getTrader)
                .anyMatch(trader -> trader.getCity().equalsIgnoreCase(city));

        // then
        assertThat(isPresent, is(true));
    }

    @Test
    public void should_list_all_transaction_values_for_city(){
        // given
        String city="Cambridge";

        // when
        List<Integer> transactionValues = transactions.stream()
                .filter(transaction -> transaction.getTrader().getCity().equalsIgnoreCase(city))
                .map(transaction1 -> transaction1.getValue())
                .collect(toList());

        // then
        assertThat(transactionValues, is(containsInAnyOrder(
                raulTransaction1.getValue(),
                raulTransaction2.getValue(),
                brianTransaction.getValue(),
                alanTransaction1.getValue())));
    }

    @Test
    public void should_get_highest_value_of_all_transactions(){
        // when
        Optional<Transaction> max = transactions.stream()
                .max(comparing(Transaction::getValue));



        // then
        assertThat(max.isPresent(), is(true));
        assertThat(max.get().getValue(), is(1000));
    }

    @Test
    public void should_get_smallest_value_of_all_transactions(){
        // when
        Optional<Transaction> max = transactions.stream()
                .min(comparing(Transaction::getValue));



        // then
        assertThat(max.isPresent(), is(true));
        assertThat(max.get().getValue(), is(300));
    }

}

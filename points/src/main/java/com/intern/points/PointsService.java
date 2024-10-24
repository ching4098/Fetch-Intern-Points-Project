package com.intern.points;
import java.util.*;

import org.springframework.stereotype.Service;

/**
 * Service class that handles the adding and spending of points of user
 */
@Service
public class PointsService {
    private List<Payment> payments = new ArrayList<>(); // list of payments
    private LinkedHashMap<String, Integer> store = new LinkedHashMap<>(); // linked hashmap that allows us to store entries according to the way they were added

    /**
     * Add payment method that adds one payment at a time into the store, updating fields if necessary
     * 
     * @param payment to be added
     */
    public void addPayment(Payment payment) {
        payments.add(payment);
        store.put(payment.getPayer(), store.getOrDefault(payment.getPayer(), 0) + payment.getPoints());
    }

    /**
     * Pay method that allows user to spend the points they currently have, payment priority is chosen
     * based on how early they were added into the store, a user's balance would never be able to go
     * negative
     * 
     * @param points to be paid for a transaction
     * @return a LinkedHashMap of the list of payers and the number of points spent from each
     * @throws IllegalArgumentException if user does not have enough points for payment
     */
    public LinkedHashMap<String, Integer> pay(int points) {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        List<Payment> temp = payments;
        Collections.sort(temp);
        int total = points;

        if(total > getAllPoints()) 
            throw new IllegalArgumentException("User does not have enough points to make the payment!");

        for(Payment payment : temp) {
            if (total <= 0) break;

            int userPoints = payment.getPoints();
            String payer = payment.getPayer();
            int pointsToBeDeducted = Math.min(userPoints, total);
            payment.setPoints(userPoints - pointsToBeDeducted);
            total -= pointsToBeDeducted;
            map.put(payer, map.getOrDefault(payer, 0) - pointsToBeDeducted);
            store.put(payer, store.get(payer) -  pointsToBeDeducted);
        }
        return map;
    }

    /**
     * Getter method for total points that a user has in their account
     * 
     * @return int total of points
     */
    public int getAllPoints() {
        int ret = 0;
        for(Integer amount : store.values()) {
            ret += amount;
        }
        return ret;
    }

    /**
     * Getter method for the store that contains the current balance of each payer
     * stored
     * 
     * @return a map of payers and their current balance
     */
    public LinkedHashMap<String, Integer> getStore() {
        return store;
    }

    /**
     * Getter method for all payments previously made
     * 
     * @return a list of payments made
     */
    public List<Payment> getPayments() {
        return payments;
    }
}

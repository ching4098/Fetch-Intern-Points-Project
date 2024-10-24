package com.intern.points;
import java.time.LocalDateTime;

/**
 * Payment class that contains the payer, points, and timestamp of the payment made, comparable (by timestamp)
 * 
 */
public class Payment implements Comparable<Payment>{
    private String payer; // payer name
    private int points; // points used in payment
    private LocalDateTime timestamp; // timestamp of payment made

    /**
     * Constructor for Payment class
     * @param payer - String input for payer's name
     * @param points - int input for points needed for payment
     * @param timestamp - LocalDateTime input for timestamp of the payment
     */
    public Payment(String payer, int points, LocalDateTime timestamp) {
        this.payer = payer;
        this.points = points;
        this.timestamp = timestamp;
    }

    /**
     * Getter method for payer
     * @return name of the payer
     */
    public String getPayer() {
        return this.payer;
    }

    /**
     * Getter method for points
     * @return points that the user has
     */
    public int getPoints() {
        return this.points;
    }

    /**
     * Getter method for timestamp
     * @return timestamp of the payment made
     */
    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }

    /**
     * Setter method for payer
     * @param payer - String input of payer name
     */
    public void setPayer(String payer) {
        this.payer = payer;
    }

    /**
     * Setter method for points
     * @param points - int input of points used in transaction
     */
    public void setPoints(int points) {
        this.points = points;
    }

    /**
     * Setter method for timestamp
     * @param timestamp - LocalDateTime input of timestamp of transaction
     */
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Overriden compare method that compares each payment by timestamp
     */
    @Override
    public int compareTo(Payment other) {
        return this.timestamp.compareTo(other.getTimestamp());
    }
}
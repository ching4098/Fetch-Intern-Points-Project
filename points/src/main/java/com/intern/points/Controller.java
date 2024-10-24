package com.intern.points;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * REST controller that handles all incoming transactions of points
 */
@RestController
@RequestMapping("/api/points")
public class Controller {
    @Autowired
    private PointsService service = new PointsService();
    
    /**
     * Adds points to the user's account through a request
     * 
     * @param request a HashMap containing payer name and point for each transaction
     * @return a response entity with status code:200(OK) indicating that the process was successful
     */
    @PostMapping("/add")
    public ResponseEntity<Void> addPoints(@RequestBody HashMap<String, String> request) {
        String payer = request.get("payer");
        int points = Integer.parseInt(request.get("points"));
        LocalDateTime time = LocalDateTime.parse(request.get("timestamp"), DateTimeFormatter.ISO_DATE_TIME);

        service.addPayment(new Payment(payer, points, time));
        return ResponseEntity.ok().build();
    }

    /**
     * Spends points from the user's account
     * 
     * @param request a HashMap containing point that want to be spent
     * @return a response entity depending on the result of the operation, 
     *         a list of users charged, or status code:400(BAD REQUEST)
     *         if the user does not have enough points in their account
     */
    @PostMapping("/spend")
    public ResponseEntity<?> spendPoints(@RequestBody HashMap<String, Integer> request) {
        int points = request.get("points");
        try{
            LinkedHashMap<String, Integer> map = service.pay(points);
            return ResponseEntity.ok(map);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Getter method for retrieving the list of balances for each payer
     * 
     * @return a response entity containing the list of current balances 
     */
    @GetMapping("/balance")
    public ResponseEntity<LinkedHashMap<String, Integer>> getBalance() {
        return ResponseEntity.ok(service.getStore());
    }
}

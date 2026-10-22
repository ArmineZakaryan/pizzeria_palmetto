package am.trainings.palmetto.customer;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class Customer {

    private long customerId;
    private final String fullName;
    private final String username;
}

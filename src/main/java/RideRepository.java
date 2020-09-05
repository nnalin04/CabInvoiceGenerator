import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class RideRepository {
    Map<String, ArrayList<Ride>> userRide = null;

    public RideRepository() {
        this.userRide = new HashMap<>();
    }

    public void addRides(String userId, Ride[] rides) {
        this.userRide.put(userId, new ArrayList<>((Arrays.asList(rides))));
    }

    public Ride[] getRides(String userId) {
        return this.userRide.get(userId).toArray(new Ride[0]);
    }
}

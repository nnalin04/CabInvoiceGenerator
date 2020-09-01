public class Ride {
    public int time;
    public double distance;
    public final CabRide cabRide;

    public Ride(CabRide ride, double distance, int time) {
        this.cabRide = ride;
        this.distance = distance;
        this.time = time;
    }
}

public class InvoiceGenerator<rideRepository> {

    private static final int COST_PER_TIME = 1;
    private static final double MIN_COST_PER_KM = 10;
    private static final double MIN_FARE = 5;
    private RideRepository rideRepository;

    public void setRideRepository(RideRepository rideRepository) {
        this.rideRepository = rideRepository;
    }

    public double calculateFare(double distance, int time) {
        double totalFare = distance * MIN_COST_PER_KM + time * COST_PER_TIME;
        return Math.max(totalFare, MIN_FARE);
    }

    public InvoiceSummary calculateFare(Ride[] rides) {
        double totalFare = 0;
        for (Ride ride : rides ){
            totalFare += ride.cabRide.calcCostOfCabeRide(ride);
        }
        return new InvoiceSummary(rides.length, totalFare);
    }

    public void addRides(String userId, Ride[] rides) {
        rideRepository.addRides(userId, rides);
    }

    public InvoiceSummary getInvoiceSummary(String userId) {
        return this.calculateFare(rideRepository.getRides(userId));
    }
}

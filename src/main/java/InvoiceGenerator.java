public class InvoiceGenerator {

    private RideRepository rideRepository;

    public void setRideRepository(RideRepository rideRepository) {
        this.rideRepository = rideRepository;
    }

    public InvoiceSummary calculateFare(Ride[] rides) {
        double totalFare = 0;
        for (Ride ride : rides ){
            totalFare += ride.cabRide.calcCostOfCabeRide(ride);
        }
        return new InvoiceSummary(rides.length, totalFare);
    }

    public void addRides(String userId, Ride[] rides) throws InvoiceGeneratorException {
        if (userId == "" || userId == null) {
            throw new InvoiceGeneratorException(InvoiceGeneratorException.ExceptionType.EMPTY, "Empty userId");
        }
        rideRepository.addRides(userId, rides);
    }

    public InvoiceSummary getInvoiceSummary(String userId) throws InvoiceGeneratorException {
        try{
            return this.calculateFare(rideRepository.getRides(userId));
        }catch (NullPointerException e) {
            throw new InvoiceGeneratorException(InvoiceGeneratorException.ExceptionType.INVALID_USERID, "invalid userId");
        }
    }
}

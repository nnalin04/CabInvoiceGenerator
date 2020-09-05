import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CabInvoiceTest {

    InvoiceGenerator invoiceGenerator = null;
    RideRepository rideRepository = null;
    Ride[] rides = null;

    @Before
    public void setUp() {
        invoiceGenerator = new InvoiceGenerator();
        rideRepository = new RideRepository();
        invoiceGenerator.setRideRepository(rideRepository);
        rides = new Ride[]{new Ride(CabRide.NORMAL, 2.0, 5),
                new Ride(CabRide.PREMIUM, 0.1, 1)
        };
    }

    @Test
    public void givenDistanceAndTime_WhenNormal_ShouldReturnTotalFare() {
        rides = new Ride[]{new Ride(CabRide.NORMAL, 2.0, 5)};
        InvoiceSummary summary = invoiceGenerator.calculateFare(rides);
        InvoiceSummary expectedInvoiceSummary = new InvoiceSummary(1, 25.0);
        Assert.assertEquals(expectedInvoiceSummary, summary);
    }

    @Test
    public void givenLessDistanceAndTime_WhenNormal_ShouldReturnMinFare() {
        rides = new Ride[]{new Ride(CabRide.NORMAL, 0.1, 1)};
        InvoiceSummary summary = invoiceGenerator.calculateFare(rides);
        InvoiceSummary expectedInvoiceSummary = new InvoiceSummary(1, 5.0);
        Assert.assertEquals(expectedInvoiceSummary, summary);
    }

    @Test
    public void givenDistanceAndTime_WhenPremium_ShouldReturnTotalFare() {
        rides = new Ride[]{new Ride(CabRide.PREMIUM, 2.0, 5)};
        InvoiceSummary summary = invoiceGenerator.calculateFare(rides);
        InvoiceSummary expectedInvoiceSummary = new InvoiceSummary(1, 40.0);
        Assert.assertEquals(expectedInvoiceSummary, summary);
    }

    @Test
    public void givenLessDistanceAndTime_WhenPremium_ShouldReturnMinFare() {
        rides = new Ride[]{new Ride(CabRide.PREMIUM, 0.1, 1)};
        InvoiceSummary summary = invoiceGenerator.calculateFare(rides);
        InvoiceSummary expectedInvoiceSummary = new InvoiceSummary(1, 20.0);
        Assert.assertEquals(expectedInvoiceSummary, summary);
    }

    @Test
    public void givenMultipleRide_ShouldReturnInvoiceSummary() {
        InvoiceSummary summary = invoiceGenerator.calculateFare(rides);
        InvoiceSummary expectedInvoiceSummary = new InvoiceSummary(2, 45.0);
        Assert.assertEquals(expectedInvoiceSummary, summary);
    }

    @Test
    public void givenUserIdAndRides_ShouldReturnInvoiceSummary() throws InvoiceGeneratorException {
        String userId = "abc@abc.com";
        invoiceGenerator.addRides(userId, rides);
        InvoiceSummary summary = invoiceGenerator.getInvoiceSummary(userId);
        InvoiceSummary expectedInvoiceSummary = new InvoiceSummary(2, 45.0);
        Assert.assertEquals(expectedInvoiceSummary, summary);
    }

    @Test
    public void givenUserIdAndRideOfDifferentType_ShouldReturnInvoiceSummary() throws InvoiceGeneratorException {
        String userId = "abc@abc.com";
        invoiceGenerator.addRides(userId, rides);
        InvoiceSummary summary = invoiceGenerator.getInvoiceSummary(userId);
        InvoiceSummary expectedInvoiceSummary = new InvoiceSummary(2, 30.0);
        Assert.assertNotEquals(expectedInvoiceSummary, summary);
    }

    @Test
    public void givenEmptyUserId_ShouldReturnInvoiceSummary() throws InvoiceGeneratorException {
        try {
            String userId = "";
            invoiceGenerator.addRides(userId, rides);
            InvoiceSummary summary = invoiceGenerator.getInvoiceSummary(userId);
            InvoiceSummary expectedInvoiceSummary = new InvoiceSummary(2, 45.0);
            Assert.assertNotEquals(expectedInvoiceSummary, summary);
        }catch (InvoiceGeneratorException e){
            Assert.assertEquals(InvoiceGeneratorException.ExceptionType.EMPTY, e.type);
        }
    }

    @Test
    public void givenNullUserId_ShouldReturnInvoiceSummary() {
        try {
            String userId = null;
            invoiceGenerator.addRides(userId, rides);
            InvoiceSummary summary = invoiceGenerator.getInvoiceSummary(userId);
            InvoiceSummary expectedInvoiceSummary = new InvoiceSummary(2, 45.0);
            Assert.assertNotEquals(expectedInvoiceSummary, summary);
        }catch (InvoiceGeneratorException e){
            Assert.assertEquals(InvoiceGeneratorException.ExceptionType.EMPTY, e.type);
        }
    }

    @Test
    public void givenUserId_ShouldReturnInvoiceSummary() {
        try {
            String userId = "abc";
            String userId1 = "@abc";
            invoiceGenerator.addRides(userId, rides);
            InvoiceSummary summary = invoiceGenerator.getInvoiceSummary(userId1);
            InvoiceSummary expectedInvoiceSummary = new InvoiceSummary(2, 45.0);
            Assert.assertNotEquals(expectedInvoiceSummary, summary);
        }catch (InvoiceGeneratorException e){
            Assert.assertEquals(InvoiceGeneratorException.ExceptionType.INVALID_USERID, e.type);
        }
    }
}

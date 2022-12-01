package streamingsystems.Helpers;


public enum PaymentType {

    CREDIT_CARD("Credit Card"),
    CASH("Cash");

    public final String name;
    
    PaymentType(String name) {
        this.name = name;
    }
}

package streamingsystems.DataRepresentation;


public enum PaymentType {

    CREDIT_CARD("CRD"),
    CASH("CSH");

    public final String name;

    PaymentType(String name) {
        this.name = name;
    }
}

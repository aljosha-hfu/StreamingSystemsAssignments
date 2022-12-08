package streamingsystems.DataRepresentation;


public enum PaymentType {
    CRD("CREDIT_CARD"),
    CSH("CASH"),
    UNK("UNKNOWN");

    public final String name;

    PaymentType(String name) {
        this.name = name;
    }
}

package streamingsystems.DataRepresentation;


/**
 * Represents the payment types of a taxi trip.
 */
@SuppressWarnings("unused")
public enum PaymentType {
    /**
     * Credit card payment type.
     */
    CRD("CREDIT_CARD"),
    /**
     * Cash payment type.
     */
    CSH("CASH"),
    /**
     * Unknown payment type.
     */
    UNK("UNKNOWN");

    /**
     * The name of the payment type.
     */
    public final String name;

    PaymentType(String name) {
        this.name = name;
    }
}

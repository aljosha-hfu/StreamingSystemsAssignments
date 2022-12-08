# Task 05

## `GeoCell`s

To represent the grid of cells in the city, we used a class called `GeoCell`.
It takes Lat and Long parameters and then returns.

### PaymentTypes

We created an Enum to store the different payment types.
In the documentation, they say, that the payment type is either Cash (CSH) or CreditCard (CRD).

However, there are also entries that list UNK as PaymentType, so we created a third option, Unknown (UNK).
Examples for the usage of UNK are line 74 and 516 of sample_data.csv.

line 74 of sample_data.csv:

```csv
F899990AB4A6582D6A5F21265725216E,474E00C0A8C9619AF6BE05D88A15F78A,2013-01-01 00:05:00,2013-01-01 00:08:00,180,0.40,-73.955826,40.779598,-73.949593,40.777012,UNK,4.00,0.50,0.50,1.00,0.00,6.00
```

To represent the wording of the challenge, the enum values also have a name in form of a String.

````java
public enum PaymentType {
    CRD("CREDIT_CARD"),
    CSH("CASH"),
    UNK("UNKNOWN");

    public final String name;

    PaymentType(String name) {
        this.name = name;
    }
}

````

## TODO

- How to get the last 30 mins of Taxi Trips from Kafka?

package streamingsystems;

import streamingsystems.implemented.MovingItemImpl;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting...");

        MovingItem movingItem1 = new MovingItemImpl("Moving Item 1");
        MovingItem movingItem2 = new MovingItemImpl("Moving Item 2");
        MovingItem movingItem3 = new MovingItemImpl("Moving Item 3");

        System.out.println("Terminating...");
    }
}
package streamingsystems;

import streamingsystems.implemented.MovingItemDTO;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting...");

        MovingItem movingItem1 = new MovingItemDTO("Moving Item 1");
        MovingItem movingItem2 = new MovingItemDTO("Moving Item 2");
        MovingItem movingItem3 = new MovingItemDTO("Moving Item 3");

        System.out.println("Terminating...");
    }
}
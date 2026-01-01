# NPC Interaction System - State Pattern Implementation

## Introduction

This Interaction System is a creative program that demonstrates how object-oriented programming (OOP) works, especially using the State Design Pattern. It creates an interactive experience between the user and a Fairy NPC (non-playable character). The Fairy's mood, or "state," changes based on how the user interacts with it, and this affects the responses the Fairy gives. This report will explain the purpose, design, development, and how the program works.

## Purpose of the System

The main goal of this system is to create an interactive and engaging program where the Fairy NPC reacts dynamically to the user's actions. This system simulates human-like emotional responses through state transitions, making the interaction feel more natural and engaging.

## System Design

The program is designed around the State Design Pattern, which allows an object to change its behavior based on its current state. This pattern is ideal for implementing emotional states in NPCs.

### 1. State Interface

**Location:** `/assignment-1/com/npc/State.java`

The `State` interface defines the common structure for the different emotional states of the Fairy NPC. Each mood state (Happy, Sad, or Angry) implements this interface and defines its own unique responses.

```java
package com.npc;

public interface State {
    void talk(Fairy fairy);
    void flattery(Fairy fairy);
    void curse(Fairy fairy);
    void exchange(Fairy fairy);
    void giveGift(Fairy fairy);
}
```

### 2. Concrete States

There are three concrete states: `HappyState`, `SadState`, and `AngryState`. Each state implements the interface methods, offering different responses depending on how the user interacts with the Fairy.

#### HappyState
**Location:** `/assignment-1/com/npc/HappyState.java`

```java
public class HappyState implements State {
    @Override
    public void talk(Fairy fairy) {
        System.out.println("The fairy smiles and chats happily with you.");
    }

    @Override
    public void curse(Fairy fairy) {
        System.out.println("The fairy is shocked by your rudeness! She becomes angry.");
        fairy.setState(new AngryState());
    }
    
    // ... other methods
}
```

#### AngryState
**Location:** `/assignment-1/com/npc/AngryState.java`

```java
public class AngryState implements State {
    @Override
    public void talk(Fairy fairy) {
        System.out.println("The fairy glares at you and refuses to talk.");
    }

    @Override
    public void exchange(Fairy fairy) {
        System.out.println("The fairy is in a bad mood. She confiscated your equipment.");
    }
    
    // ... other methods
}
```

#### SadState
**Location:** `/assignment-1/com/npc/SadState.java`

```java
public class SadState implements State {
    @Override
    public void talk(Fairy fairy) {
        System.out.println("The fairy sighs and doesn't want to say much.");
    }

    @Override
    public void giveGift(Fairy fairy) {
        System.out.println("The fairy wipes her tears and smiles. She appreciates the gift.");
        fairy.setState(new HappyState());
    }
    
    // ... other methods
}
```

### 3. State Transitions

The Fairy's mood changes based on user interactions, creating realistic emotional shifts:

- **Happy → Angry**: When cursed
- **Angry → Sad**: When flattered
- **Sad → Happy**: When given a gift or flattered
- **Angry → Happy**: When given a gift

This keeps the interaction dynamic and engaging.

### 4. Fairy Context Class

**Location:** `/assignment-1/com/npc/Fairy.java`

The `Fairy` class acts as the "context" in the State Pattern. It holds the current mood of the Fairy and directs the behavior to the correct state object.

```java
public class Fairy {
    private State state;

    public Fairy() {
        this.state = new HappyState(); // Initial state
    }

    public void setState(State state) {
        this.state = state;
    }

    public void talk() {
        state.talk(this);
    }
    
    // ... other interaction methods
}
```

## Implementation

The system is implemented using Java 8+ with the following key components:

### 1. State Interface
Defines the contract for all interaction methods that each state must implement.

### 2. Concrete State Classes
Each emotional state implements the interface with mood-specific responses and state transition logic.

### 3. State Management
The `Fairy` class manages the current state and delegates all interactions to the active state object.

### 4. Main Interaction Loop
**Location:** `/assignment-1/com/npc/Main.java`

The program continuously prompts the user for interactions, processes input, and updates the Fairy's mood.

```java
public class Main {
    public static void main(String[] args) {
        Fairy fairy = new Fairy();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Display menu
            System.out.println("\nPlease select the interaction with the fairy:");
            System.out.println("1. Talk to her");
            System.out.println("2. Flattery her");
            System.out.println("3. Curse her");
            System.out.println("4. Exchange equipment with her");
            System.out.println("5. Give her a gift");
            System.out.print("Your input is: ");

            // Process input and trigger appropriate interaction
            String input = scanner.nextLine();
            int choice = Integer.parseInt(input.trim());
            
            switch (choice) {
                case 1: fairy.talk(); break;
                case 2: fairy.flattery(); break;
                case 3: fairy.curse(); break;
                case 4: fairy.exchange(); break;
                case 5: fairy.giveGift(); break;
            }
        }
    }
}
```

## Project Outcome

### Input Interface
When the program runs, it displays an interactive menu where users can select their interaction:

```
Please select the interaction with the fairy:
1. Talk to her
2. Flattery her
3. Curse her
4. Exchange equipment with her
5. Give her a gift
Your input is: 
```

### Sample Output
Based on user input, the program shows mood-appropriate responses:

**Example 1: Happy State**
```
Your input is: 1
The fairy smiles and chats happily with you.
```

**Example 2: Angry State**
```
Your input is: 4
The fairy is in a bad mood. She confiscated your equipment.
```

**Example 3: State Transition**
```
Your input is: 3
The fairy is shocked by your rudeness! She becomes angry.

Your input is: 5
The fairy accepts the gift and her mood improves slightly.
```

The program continues running until the user manually terminates it, providing an ongoing interactive experience with the Fairy NPC.

## Conclusion

This implementation successfully demonstrates the State Design Pattern in creating a dynamic NPC interaction system. The Fairy's emotional responses change realistically based on user interactions, providing an engaging and immersive experience that showcases the power of object-oriented design patterns in game development.

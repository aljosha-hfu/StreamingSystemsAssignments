# Task 01

- Considerations: Should we implement the commands directly inside the `CommandsImpl` class or as subclasses of an
  abstract `Command`class with an abstract `handle()` method which executes the command?
    - We decided on using the latter since it allows for more code segregation and separation of concerns.

## Questions

- Are `id` and `name` the same thing?
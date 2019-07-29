# SRP Dojo

This repo violates the [Single Responsibility Principle](https://en.wikipedia.org/wiki/Single_responsibility_principle) badly - can you fix it?

1. Count how many responsibilities does the Employee class have. Name them and write them down on a piece of paper.
2. Run the application. Check the output. Save it somewhere to compare later.
3. Start refactoring. Check the output often. It should stay the same as it was in the beginning.
4. When you've finished refactoring, count the responsibilities again. Does every class have only one? 
5. Compare your code with a friend.

Additionally fix some other issues:
* should we store both `age` and `birthDate` fields?
* what's wrong with `setHolidays` method?
* can you see anything else that could be improved?

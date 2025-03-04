## TASK 1

Write a program that prints to the console every second the time from the moment this program launched.
The second thread prints every 5 seconds the message `Past 5 seconds`.

## TASK 2

Write a program that prints to the console string contains numbers from 1 to n with replacement some values:
 - If the number is divided by `3`, print `fizz`
 - If the number is divided by `5`, print `buzz`
 - If the number is divided by `3` and `5` at the same time, print `fizzbuzz`

For example, for `n = 15`, it expects the next result: `1, 2, fizz, 4, buzz, fizz, 7, 8, fizz, buzz, 11, fizz, 13, 14, fizzbuzz`.

The program must be multithreaded and work with 4 threads:

Thread `A` call `fizz()` to check that number is divided by `3` and if true - add to queue for output to thread `D` `fizz`.
Thread `B` call `buzz()` to check that number is divided by `5` and if true - add to queue for output to thread `D` `buzz`.
Thread `C` call `fizzbuzz()` to check that number is divided by `3` and `5` at the same time and if true - add to queue for output to thread `D` `fizzbuzz`.
Thread `D` call `number()` to print the next number from the queue, if there is such a number to print.

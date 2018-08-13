# o-Writing Book game

Co-Writing Book is a game in which each player writes one sentence of a ‘book’ in a turn-by-turn manner, without being able to see more than the previous sentence.

## Example

e.g: 
- Player A starts, writing “If I had a world of my own”.

- Player B sees “If I had a world of my own”, and writes “everything would be nonsense”.

- Player C sees “everything would be nonsense”, and writes “Nothing would be what it is”

- Player D sees “Nothing would be what it is”, and writes “because everything would be what it isn’t”

- Player E sees “because everything would be what it isn’t”, and writes “Alice.”

When the book is ‘completed’, all of the contributing players can access and read it. Everyone gets 10 points for contributing to the book.


## Aim
The aim of this exercise is to write an application server (in any language and framework running on the JVM) that lets you get a book, add a sentence to it, add 10 points to all participants when a book is completed, and return a leaderboard of the five ‘greatest’ writers.

Do not focus on a storing system; it will all be stored in memory. Do not focus on com.nunomagg.network/socket but on the model/application layer.

You can assume that the players will always write a sentence once they get a book.

## Solution

## Assumption 1
For this solution an assumption was made that the phrase **"You can assume that the players will always write a sentence once they get a book."**
in the projects statement meant the user was added automatically as a contribuiting player of the book upon requesting it and that it would be he's
turn until he writes.
So as for the mentioned assumption the book should never be locked on completion

## Other assumptions
- the user can add as many lines as he want to a book.

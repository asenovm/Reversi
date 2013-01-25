# Reversi #
=======

## <span style="text-align: center;">About</span> ##

An implementation of the *Reversi/Othello* game, written in *Java/Swing* and featuring AI opponent.


<div><img style="text-align:center;" src="https://photos-2.dropbox.com/t/0/AABI0C4W5cvcYEy8MTopFeHDoQ6rs7Wnu1GhObP0L4lAgA/10/126705867/png/2048x1536/2/1359129600/0/2/screenshot.png/6zFo-QHCR_P_FSxCy_0jdqrxvNt1lKl0JAQ6ni2_NTU" alt="gameplaye" /></div>

## <span style="text-align: center;">AI opponent</span> ##

The AI opponent is implemented using the Minimax algorithm with alpha-beta pruning. Since the state space of the game is pretty big (almost as big as in the chess game) it cannot be traversed completely to a terminal state, but instead only the first 4 levels of the decision tree are generated. The board at the 4th("terminal") level is evaluated using the following heuristics:
 * Number of discs - the bigger the number of discs on the board a player owns, the better.
 * Mobility - the fewer moves the opponent of the current player has, the better
 * Location -  some position are better than others, because they offer a better chance for attacks
 * Turn skip - sometimes the player cannot make a valid move, which is very profitable for the other player.
 * Number of stable discs - some discs on the board cannot be flipped anymore, either because they are surrounded from all sides, or because they are located in the corners of the board. The more such discs a player owns, the better for him.

## <span style="text-align:center;">Further improvement</span> ##
There are a few points that I'd like to have improved/developed in the near feature. These include, but are not limited to:
 * Adjust the weights, given to the different heuristics, perhaps by using a neuron net 
 * Port the game to Android
 * Allow for multiplayer games

## <span style="text-align:center;">About the author</span> ##

Author: Martin Asenov Asenov <br />
email: asenov.m@gmail.com

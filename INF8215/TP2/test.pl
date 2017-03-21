ispair(no, one).
ispair(no, superOne).
ispair(yes, two).
ispair(no, three).
ispair(yes, four).

isgreaterthantwo(no, one).
isgreaterthantwo(no, superOne).
isgreaterthantwo(no, two).
isgreaterthantwo(yes, three).
isgreaterthantwo(yes, four).


questions([
[ispair, 'pair'],
[isgreaterthantwo, 'two']]).

askQuestion([Pred,Question],Value,X):-
	test(Pred,Value,X).

test(Pred,Value,X):-
	call(Pred,Value,X).

getFirstQuestion([H|T],H,T).


bot(X,Y) :- questions(ListQuestions),
	iteration(X,Y,ListQuestions).

iteration([],Y,[]).

iteration([H|T],Y,ListQuestions):-
	getFirstQuestion(ListQuestions,Question,Rest),
	askQuestion(Question,H,Y),
	iteration(T,Y,Rest).


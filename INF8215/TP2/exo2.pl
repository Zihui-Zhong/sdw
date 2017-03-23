enough_space([], _).
enough_space([0], _).
enough_space(Contraintes, Sequence) :-
	sum_list(Contraintes, S),
	length(Contraintes, Y),
	length(Sequence, Z),
	S + Y - 1 =< Z.
	

nonogram(ColumnSpecs, LineSpecs, X) :-
    length(ColumnSpecs, Width),
    valid_lines(Width, LineSpecs, X),
    valid_columns(ColumnSpecs, X, 1),
    print_nonogram(X).

valid_lines(_, [], []).
valid_lines(Width, [LineSpec | LineSpecs], [Line | Lines]) :-
    length(Line, Width),
	list_between(Line, 0, 1),
    valid_seq(LineSpec, Line),
    valid_lines(Width, LineSpecs, Lines).

valid_columns([], _, _).
valid_columns([ColumnSpec | ColumnSpecs], Lines, FirstColumn) :-
    valid_column(FirstColumn, ColumnSpec, Lines),
    FirstColumnPlusOne is FirstColumn + 1,
    valid_columns(ColumnSpecs, Lines, FirstColumnPlusOne).
    
valid_column(Index, ColumnSpec, Lines) :-
    extract(Index, Lines, Column),
    valid_seq(ColumnSpec, Column).


valid_seq(Contraintes, [0 | Seq]) :-
	enough_space(Contraintes, [0|Seq]),
    valid_seq(Contraintes, Seq).
valid_seq([Contrainte | Contraintes], [1 | SeqRest]) :-
    Contrainte > 0,
    ContrainteMoinUn is Contrainte - 1,
    valid_seq([ContrainteMoinUn| Contraintes], SeqRest).
valid_seq([], Seq) :-
    valid_seq([0], Seq).
valid_seq([0], []).
valid_seq([0 | Contraintes], [0 | SeqRest]) :-
    valid_seq(Contraintes, SeqRest).
 
extract(_, [], []).
extract(Index, [List], [Result]) :-
    nth1(Index, List, Result),!.
extract(Index, [List | Lists], [Result | Results]) :-
    extract(Index, [List], [Result]),
    extract(Index, Lists, Results).

print_nonogram(N) :-
    nl,write('Found nonogram:'),nl,
    print_nonogram1(N).
    
print_nonogram1([]).
print_nonogram1([Line | Lines]) :-
    print_line(Line),nl,
    print_nonogram1(Lines).
    
print_line([]).
print_line([Head | Tail]) :-
    Head = 1,
    write('# '),
    print_line(Tail).
	
print_line([Head | Tail]) :-
    Head = 0,
    write('. '),
    print_line(Tail).
	
list_between([], Low, High).
list_between([H|List], Low, High) :-
	between(Low, High, H),
	list_between(List, Low, High).


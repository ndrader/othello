JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	Board.java \
	Computer.java \
	Game.java \
	Menu.java \
	Node.java \
	Othello.java \

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class

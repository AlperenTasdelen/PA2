ORDER = 2
GUI = false
SHUFFLE = false
INPUT = ./sample_input.txt
SHUFFLE_INPUT = ./shuf_input.txt
RUN = ./run.sh

all:
	@javac *.java
	java CengBookRunner $(ORDER) $(INPUT) $(GUI)
	@rm -f *.class

myrun:
	@javac *.java
	java CengBookRunner $(ORDER) $(INPUT) $(GUI) < $(RUN)
	@rm -f *.class

shuf:
	@javac *.java
	java CengBookRunner $(ORDER) $(SHUFFLE_INPUT) $(GUI)
	@rm -f *.class

clean:
	rm -f *.class

run:
	java CengBookRunner $(ORDER) ./sample_input.txt $(GUI)
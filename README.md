# Cases

java CengBookRunner 4 2.input False < 1.input > 1.student.out
java CengBookRunner 3 1.input False < 2.input > 2.student.out
java CengBookRunner 2 1.input False < 3.input > 3.student.out
java CengBookRunner 5 1.input False < 3.input > 4.student.out
java CengBookRunner 2 1.input False < 4.input > 5.student.out
java CengBookRunner 3 1.input False < 4.input > 6.student.out
java CengBookRunner 2 1.input False < 5.input > 7.student.out
java CengBookRunner 4 1.input False < 5.input > 8.student.out
java CengBookRunner 2 1.input False < 6.input > 9.student.out
java CengBookRunner 5 1.input False < 6.input > 10.student.out
java CengBookRunner 2 1.input False < 7.input > 11.student.out
java CengBookRunner 6 1.input False < 7.input > 12.student.out

then

diff -ZB x.out x.student.out

Cases 1 & 2 are 5 points, the rest are 9 points

SELECT student.name, student.age, f.name FROM student
LEFT JOIN faculty f ON f.id = student.faculty_id;

SELECT student.name FROM student
INNER JOIN avatar a ON student.id = a.student_id
SELECT course.id, course.title, course.description
FROM course
         INNER JOIN student_course
                    on course.id = student_course.course_id
where student_course.user_id = ?
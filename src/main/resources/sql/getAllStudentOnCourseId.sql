SELECT *
FROM user
         INNER JOIN student_course
                    on user.id = student_course.user_id
where student_course.course_id = ?
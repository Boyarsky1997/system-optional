select comment.*, u.name , u.surname
from comment
inner join user u on comment.teacher_id = u.id
where student_id = ?

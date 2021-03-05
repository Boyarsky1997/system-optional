select comment.*, u.name
from comment
inner join user u on comment.teacher_id = u.id
where student_id = ?


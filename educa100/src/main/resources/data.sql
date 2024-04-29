INSERT INTO papel (id, nome)
VALUES (1, 'adm'),
       (2, 'pedagogico'),
       (3, 'recruiter'),
       (4, 'professor'),
       (5, 'aluno')
ON CONFLICT (id) DO NOTHING;
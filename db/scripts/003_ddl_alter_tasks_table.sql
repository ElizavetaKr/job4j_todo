ALTER TABLE tasks
ADD COLUMN user_id int not null references users(id);
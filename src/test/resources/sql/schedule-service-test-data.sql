INSERT INTO schedules (
    schedule_id,
    end_at,
    start_at,
    theater_id,
    create_date,
    last_modified_date,
    movie_id,
    schedule_date,
    screen_id,
    code,
    created_by,
    last_modified_by
) VALUES
      (1, '09:19:00.000000', '07:30:00.000000', 2, now(), now(), 21, now() , 3, NULL, NULL, NULL),
      (2, '11:19:00.000000', '09:30:00.000000', 2, now(), now(), 21, now(), 5, NULL, NULL, NULL);
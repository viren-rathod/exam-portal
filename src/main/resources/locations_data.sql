-- States
INSERT INTO state_master (id, name, created_at, created_by) VALUES (1, 'Maharashtra', CURRENT_TIMESTAMP, 'admin');
INSERT INTO state_master (id, name, created_at, created_by) VALUES (2, 'Karnataka', CURRENT_TIMESTAMP, 'admin');
INSERT INTO state_master (id, name, created_at, created_by) VALUES (3, 'Delhi', CURRENT_TIMESTAMP, 'admin');
INSERT INTO state_master (id, name, created_at, created_by) VALUES (4, 'Tamil Nadu', CURRENT_TIMESTAMP, 'admin');
INSERT INTO state_master (id, name, created_at, created_by) VALUES (5, 'Gujarat', CURRENT_TIMESTAMP, 'admin');

-- Cities
INSERT INTO city_master (id, name, state_id, created_at, created_by) VALUES (1, 'Mumbai', 1, CURRENT_TIMESTAMP, 'admin');
INSERT INTO city_master (id, name, state_id, created_at, created_by) VALUES (2, 'Pune', 1, CURRENT_TIMESTAMP, 'admin');
INSERT INTO city_master (id, name, state_id, created_at, created_by) VALUES (3, 'Nagpur', 1, CURRENT_TIMESTAMP, 'admin');
INSERT INTO city_master (id, name, state_id, created_at, created_by) VALUES (4, 'Bengaluru', 2, CURRENT_TIMESTAMP, 'admin');
INSERT INTO city_master (id, name, state_id, created_at, created_by) VALUES (5, 'Mysuru', 2, CURRENT_TIMESTAMP, 'admin');
INSERT INTO city_master (id, name, state_id, created_at, created_by) VALUES (6, 'Mangaluru', 2, CURRENT_TIMESTAMP, 'admin');
INSERT INTO city_master (id, name, state_id, created_at, created_by) VALUES (7, 'New Delhi', 3, CURRENT_TIMESTAMP, 'admin');
INSERT INTO city_master (id, name, state_id, created_at, created_by) VALUES (8, 'Chennai', 4, CURRENT_TIMESTAMP, 'admin');
INSERT INTO city_master (id, name, state_id, created_at, created_by) VALUES (9, 'Coimbatore', 4, CURRENT_TIMESTAMP, 'admin');
INSERT INTO city_master (id, name, state_id, created_at, created_by) VALUES (10, 'Madurai', 4, CURRENT_TIMESTAMP, 'admin');
INSERT INTO city_master (id, name, state_id, created_at, created_by) VALUES (11, 'Ahmedabad', 5, CURRENT_TIMESTAMP, 'admin');
INSERT INTO city_master (id, name, state_id, created_at, created_by) VALUES (12, 'Surat', 5, CURRENT_TIMESTAMP, 'admin');
INSERT INTO city_master (id, name, state_id, created_at, created_by) VALUES (13, 'Vadodara', 5, CURRENT_TIMESTAMP, 'admin');

-- Colleges
INSERT INTO college_master (id, name, created_at, created_by) VALUES (1, 'Indian Institute of Technology (IIT), Bombay', CURRENT_TIMESTAMP, 'admin');
INSERT INTO college_master (id, name, created_at, created_by) VALUES (2, 'College of Engineering, Pune (COEP)', CURRENT_TIMESTAMP, 'admin');
INSERT INTO college_master (id, name, created_at, created_by) VALUES (3, 'Indian Institute of Technology (IIT), Delhi', CURRENT_TIMESTAMP, 'admin');
INSERT INTO college_master (id, name, created_at, created_by) VALUES (4, 'National Institute of Technology (NIT), Trichy', CURRENT_TIMESTAMP, 'admin');
INSERT INTO college_master (id, name, created_at, created_by) VALUES (5, 'Anna University, Chennai', CURRENT_TIMESTAMP, 'admin');
INSERT INTO college_master (id, name, created_at, created_by) VALUES (6, 'R.V. College of Engineering (RVCE), Bengaluru', CURRENT_TIMESTAMP, 'admin');
INSERT INTO college_master (id, name, created_at, created_by) VALUES (7, 'Indian Institute of Technology (IIT), Madras', CURRENT_TIMESTAMP, 'admin');
INSERT INTO college_master (id, name, created_at, created_by) VALUES (8, 'Birla Institute of Technology and Science (BITS), Pilani', CURRENT_TIMESTAMP, 'admin');
INSERT INTO college_master (id, name, created_at, created_by) VALUES (9, 'L.D. College of Engineering, Ahmedabad', CURRENT_TIMESTAMP, 'admin');
INSERT INTO college_master (id, name, created_at, created_by) VALUES (10, 'Visvesvaraya National Institute of Technology (VNIT), Nagpur', CURRENT_TIMESTAMP, 'admin');
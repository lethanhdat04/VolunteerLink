INSERT INTO users (full_name, email, password, phone_number, latitude, longitude, role_id, status, created_at, update_at) VALUES
-- Admin users
('Nguyen Van Admin', 'admin@volunteer.com', '$2a$10$hashedpassword1', '0901234567', 21.0285, 105.8542, 1, 'ACTIVE', NOW(), NOW()),

-- Organization users
('Hoi Chu Thap Do Viet Nam', 'redcross@vietnam.org', '$2a$10$hashedpassword2', '0902345678', 21.0245, 105.8412, 2, 'ACTIVE', NOW(), NOW()),
('Quy Tam Long Viet', 'tamlong@charity.vn', '$2a$10$hashedpassword3', '0903456789', 21.0195, 105.8502, 2, 'ACTIVE', NOW(), NOW()),
('Hoi Bao Ve Moi Truong', 'environment@protect.vn', '$2a$10$hashedpassword4', '0904567890', 21.0315, 105.8445, 2, 'ACTIVE', NOW(), NOW()),

-- Volunteer users
('Tran Thi Lan', 'lan.tran@email.com', '$2a$10$hashedpassword5', '0905678901', 21.0275, 105.8535, 3, 'ACTIVE', NOW(), NOW()),
('Le Van Duc', 'duc.le@email.com', '$2a$10$hashedpassword6', '0906789012', 21.0225, 105.8475, 3, 'ACTIVE', NOW(), NOW()),
('Pham Thi Mai', 'mai.pham@email.com', '$2a$10$hashedpassword7', '0907890123', 21.0305, 105.8395, 3, 'ACTIVE', NOW(), NOW()),
('Hoang Van Minh', 'minh.hoang@email.com', '$2a$10$hashedpassword8', '0908901234', 21.0185, 105.8525, 3, 'ACTIVE', NOW(), NOW()),
('Vu Thi Hoa', 'hoa.vu@email.com', '$2a$10$hashedpassword9', '0909012345', 21.0255, 105.8465, 3, 'ACTIVE', NOW(), NOW()),

-- Recipient users
('Nguyen Van Cuong', 'cuong.nguyen@email.com', '$2a$10$hashedpassword10', '0910123456', 21.0295, 105.8415, 4, 'ACTIVE', NOW(), NOW()),
('Tran Thi Nga', 'nga.tran@email.com', '$2a$10$hashedpassword11', '0911234567', 21.0215, 105.8555, 4, 'ACTIVE', NOW(), NOW()),
('Le Van Hung', 'hung.le@email.com', '$2a$10$hashedpassword12', '0912345678', 21.0325, 105.8385, 4, 'ACTIVE', NOW(), NOW());

-- Insert data into organization table
INSERT INTO organization (id, description, type, website) VALUES
                                                              (2, 'Vietnamese Red Cross - Providing humanitarian aid and disaster relief', 'Healthcare', 'https://redcross.org.vn'),
                                                              (3, 'Tam Long Viet Foundation - Supporting education and community development', 'Education', 'https://tamlongviet.org'),
                                                              (4, 'Environmental Protection Association - Promoting environmental conservation', 'Environmental', 'https://moitruong.org.vn');

-- Insert data into volunteer table
INSERT INTO volunteer (id, available_hours, rating) VALUES
                                                        (5, 20, 4.5),
                                                        (6, 15, 4.2),
                                                        (7, 25, 4.8),
                                                        (8, 18, 4.3),
                                                        (9, 22, 4.6);

-- Insert data into recipient table
INSERT INTO recipient (id) VALUES
                               (10),
                               (11),
                               (12);

-- Insert data into volunteer_skills table
INSERT INTO volunteer_skills (user_id, skill) VALUES
                                                  (5, 'HEALTHCARE'),
                                                  (5, 'FIRST_AID'),
                                                  (5, 'COMMUNICATION'),
                                                  (6, 'TEACHING'),
                                                  (6, 'EDUCATION'),
                                                  (6, 'COMMUNICATION'),
                                                  (7, 'ENVIRONMENTAL_CLEANUP'),
                                                  (7, 'COMMUNITY_OUTREACH'),
                                                  (7, 'EVENT_ORGANIZATION'),
                                                  (8, 'IT_SUPPORT'),
                                                  (8, 'LOGISTICS'),
                                                  (8, 'COMMUNICATION'),
                                                  (9, 'CHILDCARE'),
                                                  (9, 'COOKING'),
                                                  (9, 'ELDERLY_CARE');



-- Insert data into event table
INSERT INTO event (title, description, location, start_time, end_time, max_participants, participants, organization_id, status) VALUES
                                                                                                                                    ('Blood Donation Campaign', 'Annual blood donation drive to help patients in need', 'Hanoi Medical University', '2025-06-15 08:00:00', '2025-06-15 17:00:00', 100, 45, 2, 'PLANNED'),
                                                                                                                                    ('Environmental Cleanup Day', 'Community cleanup event at Hoan Kiem Lake', 'Hoan Kiem Lake, Hanoi', '2025-06-20 07:00:00', '2025-06-20 12:00:00', 50, 32, 4, 'PLANNED'),
                                                                                                                                    ('Teaching Workshop for Underprivileged Children', 'Educational workshop for children in rural areas', 'Community Center, Ba Vi District', '2025-06-25 09:00:00', '2025-06-25 16:00:00', 30, 18, 3, 'PLANNED'),
                                                                                                                                    ('Elderly Care Visit', 'Visit and care for elderly residents at nursing homes', 'Golden Age Nursing Home', '2025-06-12 14:00:00', '2025-06-12 17:00:00', 20, 15, 2, 'COMPLETED'),
                                                                                                                                    ('IT Skills Training', 'Basic computer skills training for seniors', 'Community IT Center', '2025-06-18 10:00:00', '2025-06-18 15:00:00', 25, 12, 3, 'ONGOING');

-- Insert data into event_registration table
INSERT INTO event_registration (event_id, volunteer_id, registration_time, status, rating, review, create_at, update_at) VALUES
                                                                                                                             (1, 5, '2025-06-08 10:30:00', 'REGISTERED', NULL, NULL, NOW(), NOW()),
                                                                                                                             (1, 6, '2025-06-08 11:15:00', 'REGISTERED', NULL, NULL, NOW(), NOW()),
                                                                                                                             (1, 7, '2025-06-08 14:20:00', 'APPROVED', NULL, NULL, NOW(), NOW()),
                                                                                                                             (2, 7, '2025-06-09 09:45:00', 'REGISTERED', NULL, NULL, NOW(), NOW()),
                                                                                                                             (2, 8, '2025-06-09 16:30:00', 'APPROVED', NULL, NULL, NOW(), NOW()),
                                                                                                                             (3, 6, '2025-06-10 08:15:00', 'REGISTERED', NULL, NULL, NOW(), NOW()),
                                                                                                                             (3, 9, '2025-06-10 13:45:00', 'APPROVED', NULL, NULL, NOW(), NOW()),
                                                                                                                             (4, 5, '2025-06-05 12:00:00', 'REGISTERED', 5, 'Great experience helping elderly residents', NOW(), NOW()),
                                                                                                                             (4, 9, '2025-06-05 14:30:00', 'REGISTERED', 4, 'Meaningful volunteer work', NOW(), NOW()),
                                                                                                                             (5, 8, '2025-06-07 11:20:00', 'APPROVED', NULL, NULL, NOW(), NOW());

-- Insert data into help_request table
INSERT INTO help_request (title, description, location, latitude, longitude, start_time, end_time, estimated_time, priority, status, organization_id, recipient_id, volunteer_id, create_at, update_at) VALUES
                                                                                                                                                                                                            ('Medical Assistance Needed', 'Elderly person needs help with medication management and basic health monitoring', '123 Nguyen Trai Street, Hanoi', 21.0125, 105.8520, '2025-06-13 08:00:00', '2025-06-13 12:00:00', 4, 'HIGH', 'PENDING', 2, 10, NULL, NOW(), NOW()),
                                                                                                                                                                                                            ('Tutoring for High School Student', 'Student needs help with mathematics and physics preparation for university entrance exam', '456 Le Loi Street, Hanoi', 21.0201, 105.8498, '2025-06-14 18:00:00', '2025-06-14 21:00:00', 3, 'MEDIUM', 'PENDING', 3, 11, NULL, NOW(), NOW()),
                                                                                                                                                                                                            ('Home Cleaning Assistance', 'Single mother needs help with house cleaning and organization', '789 Tran Hung Dao Street, Hanoi', 21.0189, 105.8467, '2025-06-16 09:00:00', '2025-06-16 13:00:00', 4, 'LOW', 'ACCEPTED', 3, 12, 7, NOW(), NOW()),
                                                                                                                                                                                                            ('Computer Setup Help', 'Senior citizen needs assistance setting up new computer and learning basic operations', '321 Ba Trieu Street, Hanoi', 21.0234, 105.8543, '2025-06-17 14:00:00', '2025-06-17 17:00:00', 3, 'MEDIUM', 'PENDING', 2, 10, NULL, NOW(), NOW()),
                                                                                                                                                                                                            ('Grocery Shopping Assistance', 'Disabled person needs help with weekly grocery shopping', '654 Hang Bong Street, Hanoi', 21.0356, 105.8521, '2025-06-19 10:00:00', '2025-06-19 12:00:00', 2, 'HIGH', 'COMPLETED', 2, 11, 5, NOW(), NOW());

-- Insert data into help_request_skills table
INSERT INTO help_request_skills (help_request_id, skill) VALUES
                                                             (1, 'HEALTHCARE'),
                                                             (1, 'ELDERLY_CARE'),
                                                             (2, 'TEACHING'),
                                                             (2, 'EDUCATION'),
                                                             (3, 'CLEANING'),
                                                             (3, 'COMMUNITY_OUTREACH'),
                                                             (4, 'IT_SUPPORT'),
                                                             (4, 'COMMUNICATION'),
                                                             (5, 'TRANSPORTATION'),
                                                             (5, 'COMMUNITY_OUTREACH');
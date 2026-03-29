-- Categories (Tech Domains)
INSERT INTO category (id, title, description, created_at, created_by) VALUES (1, 'Java Programming', 'Core Java, JVM, and Multithreading concepts', CURRENT_TIMESTAMP, 'admin');
INSERT INTO category (id, title, description, created_at, created_by) VALUES (2, 'Frontend Development', 'HTML5, CSS3, and modern UI frameworks', CURRENT_TIMESTAMP, 'admin');
INSERT INTO category (id, title, description, created_at, created_by) VALUES (3, 'SQL & Databases', 'Relational database design and ACID properties', CURRENT_TIMESTAMP, 'admin');
INSERT INTO category (id, title, description, created_at, created_by) VALUES (4, 'Cloud Computing', 'AWS, Azure, and Scalability', CURRENT_TIMESTAMP, 'admin');
INSERT INTO category (id, title, description, created_at, created_by) VALUES (5, 'Data Structures', 'Arrays, Linked Lists, and Trees', CURRENT_TIMESTAMP, 'admin');
INSERT INTO category (id, title, description, created_at, created_by) VALUES (6, 'Algorithms', 'Sorting, Searching, and Dynamic Programming', CURRENT_TIMESTAMP, 'admin');
INSERT INTO category (id, title, description, created_at, created_by) VALUES (7, 'DevOps & CI/CD', 'Docker, Kubernetes, and Jenkins pipelines', CURRENT_TIMESTAMP, 'admin');
INSERT INTO category (id, title, description, created_at, created_by) VALUES (8, 'JavaScript', 'ES6 features, Closures, and Event Loops', CURRENT_TIMESTAMP, 'admin');
INSERT INTO category (id, title, description, created_at, created_by) VALUES (9, 'Python', 'Scripting, Data Analysis, and Django', CURRENT_TIMESTAMP, 'admin');
INSERT INTO category (id, title, description, created_at, created_by) VALUES (10, 'Cybersecurity', 'Web security, JWT, and Encryption', CURRENT_TIMESTAMP, 'admin');

-- Exams
INSERT INTO exam (id, title, description, exam_time, max_marks, total_questions, exam_code, status, created_at, created_by) VALUES (1, 'Full Stack Developer Prep', 'Testing Frontend and JavaScript fundamentals', 60, '100', '10', 'FS-2026', 'ACTIVE', CURRENT_TIMESTAMP, 'admin');
INSERT INTO exam (id, title, description, exam_time, max_marks, total_questions, exam_code, status, created_at, created_by) VALUES (2, 'Backend Systems & Logic', 'Advanced SQL, DS, and Algorithms', 60, '100', '10', 'BS-2026', 'ACTIVE', CURRENT_TIMESTAMP, 'admin');
INSERT INTO exam (id, title, description, exam_time, max_marks, total_questions, exam_code, status, created_at, created_by) VALUES (3, 'Cloud Infrastructure Pro', 'DevOps, Cloud, and Security essentials', 60, '100', '10', 'CP-2026', 'ACTIVE', CURRENT_TIMESTAMP, 'admin');

-- Exam Categories Mapping
-- Exam 1: Java, Frontend, JS
INSERT INTO exam_category (exam_id, category_id) VALUES (1, 1), (1, 2), (1, 8);
-- Exam 2: SQL, Data Structures, Algorithms
INSERT INTO exam_category (exam_id, category_id) VALUES (2, 3), (2, 5), (2, 6);
-- Exam 3: Cloud, DevOps, Python, Security
INSERT INTO exam_category (exam_id, category_id) VALUES (3, 4), (3, 7), (3, 9), (3, 10);

-- Questions (3 per category)
INSERT INTO questions (id, title, description, created_at, created_by) VALUES
                                                                           (1, 'Java Heap vs Stack', 'Where are objects stored in Java memory?', CURRENT_TIMESTAMP, 'admin'),
                                                                           (2, 'Java Inheritance', 'Which keyword is used to inherit a class?', CURRENT_TIMESTAMP, 'admin'),
                                                                           (3, 'Java Final Keyword', 'Can a final class be inherited?', CURRENT_TIMESTAMP, 'admin'),
                                                                           (4, 'HTML Semantics', 'Which tag is used for the most important heading?', CURRENT_TIMESTAMP, 'admin'),
                                                                           (5, 'CSS Box Model', 'Which property controls the space inside an element?', CURRENT_TIMESTAMP, 'admin'),
                                                                           (6, 'CSS Selectors', 'How do you select an element with a specific ID?', CURRENT_TIMESTAMP, 'admin'),
                                                                           (7, 'SQL JOINS', 'Which join returns all records from both tables?', CURRENT_TIMESTAMP, 'admin'),
                                                                           (8, 'SQL Constraints', 'Which constraint ensures unique values in a column?', CURRENT_TIMESTAMP, 'admin'),
                                                                           (9, 'ACID Properties', 'What does the "A" in ACID stand for?', CURRENT_TIMESTAMP, 'admin'),
                                                                           (10, 'Cloud Models', 'What does SaaS stand for?', CURRENT_TIMESTAMP, 'admin'),
                                                                           (11, 'AWS Services', 'Which service is used for scalable virtual servers?', CURRENT_TIMESTAMP, 'admin'),
                                                                           (12, 'Serverless', 'Which AWS service allows you to run code without provisioning servers?', CURRENT_TIMESTAMP, 'admin'),
                                                                           (13, 'Array Complexity', 'What is the time complexity to access an element by index?', CURRENT_TIMESTAMP, 'admin'),
                                                                           (14, 'Linked Lists', 'What is the main advantage of a Linked List over an Array?', CURRENT_TIMESTAMP, 'admin'),
                                                                           (15, 'Stack Data Structure', 'Which principle does a Stack follow?', CURRENT_TIMESTAMP, 'admin'),
                                                                           (16, 'Binary Search', 'What is the time complexity of Binary Search?', CURRENT_TIMESTAMP, 'admin'),
                                                                           (17, 'Sorting Algorithms', 'Which algorithm is generally considered the fastest for large datasets?', CURRENT_TIMESTAMP, 'admin'),
                                                                           (18, 'Recursion', 'What happens if a recursive function lacks a base case?', CURRENT_TIMESTAMP, 'admin'),
                                                                           (19, 'Docker Containerization', 'What is the command to list running containers?', CURRENT_TIMESTAMP, 'admin'),
                                                                           (20, 'Kubernetes Nodes', 'What is the smallest deployable unit in Kubernetes?', CURRENT_TIMESTAMP, 'admin'),
                                                                           (21, 'CI/CD Pipelines', 'What is the purpose of a "Build" stage?', CURRENT_TIMESTAMP, 'admin'),
                                                                           (22, 'JS Closures', 'What is a closure in JavaScript?', CURRENT_TIMESTAMP, 'admin'),
                                                                           (23, 'JS Async/Await', 'Which keyword is used to wait for a Promise?', CURRENT_TIMESTAMP, 'admin'),
                                                                           (24, 'JS Equality', 'What is the difference between == and ===?', CURRENT_TIMESTAMP, 'admin'),
                                                                           (25, 'Python Lists', 'How do you add an element to the end of a list?', CURRENT_TIMESTAMP, 'admin'),
                                                                           (26, 'Python Decorators', 'What symbol is used to denote a decorator?', CURRENT_TIMESTAMP, 'admin'),
                                                                           (27, 'Python GIL', 'What does GIL stand for in Python?', CURRENT_TIMESTAMP, 'admin'),
                                                                           (28, 'SQL Injection', 'How do you prevent SQL injection?', CURRENT_TIMESTAMP, 'admin'),
                                                                           (29, 'JWT Authentication', 'Where is a JWT usually stored on the client?', CURRENT_TIMESTAMP, 'admin'),
                                                                           (30, 'CORS', 'What does CORS stand for?', CURRENT_TIMESTAMP, 'admin');

-- Question Categories Mapping
INSERT INTO question_category (question_id, category_id) VALUES
                                                             (1,1), (2,1), (3,1), (4,2), (5,2), (6,2), (7,3), (8,3), (9,3), (10,4),
                                                             (11,4), (12,4), (13,5), (14,5), (15,5), (16,6), (17,6), (18,6), (19,7), (20,7),
                                                             (21,7), (22,8), (23,8), (24,8), (25,9), (26,9), (27,9), (28,10), (29,10), (30,10);

-- Options
INSERT INTO question_options (id, title, question_id, created_at, created_by) VALUES

-- Q1
(1,'Stack Memory',1,CURRENT_TIMESTAMP,'admin'),
(2,'Heap Memory',1,CURRENT_TIMESTAMP,'admin'),
(3,'Cache',1,CURRENT_TIMESTAMP,'admin'),
(4,'Disk Storage',1,CURRENT_TIMESTAMP,'admin'),

-- Q2
(5,'extends',2,CURRENT_TIMESTAMP,'admin'),
(6,'implements',2,CURRENT_TIMESTAMP,'admin'),
(7,'inherits',2,CURRENT_TIMESTAMP,'admin'),
(8,'super',2,CURRENT_TIMESTAMP,'admin'),

-- Q3
(9,'Yes',3,CURRENT_TIMESTAMP,'admin'),
(10,'No',3,CURRENT_TIMESTAMP,'admin'),
(11,'Only abstract classes',3,CURRENT_TIMESTAMP,'admin'),
(12,'Depends on JVM',3,CURRENT_TIMESTAMP,'admin'),

-- Q4
(13,'h1',4,CURRENT_TIMESTAMP,'admin'),
(14,'head',4,CURRENT_TIMESTAMP,'admin'),
(15,'title',4,CURRENT_TIMESTAMP,'admin'),
(16,'header',4,CURRENT_TIMESTAMP,'admin'),

-- Q5
(17,'Margin',5,CURRENT_TIMESTAMP,'admin'),
(18,'Padding',5,CURRENT_TIMESTAMP,'admin'),
(19,'Border',5,CURRENT_TIMESTAMP,'admin'),
(20,'Outline',5,CURRENT_TIMESTAMP,'admin'),

-- Q6
(21,'.class',6,CURRENT_TIMESTAMP,'admin'),
(22,'*',6,CURRENT_TIMESTAMP,'admin'),
(23,'#id',6,CURRENT_TIMESTAMP,'admin'),
(24,'element',6,CURRENT_TIMESTAMP,'admin'),

-- Q7
(25,'INNER JOIN',7,CURRENT_TIMESTAMP,'admin'),
(26,'LEFT JOIN',7,CURRENT_TIMESTAMP,'admin'),
(27,'FULL OUTER JOIN',7,CURRENT_TIMESTAMP,'admin'),
(28,'CROSS JOIN',7,CURRENT_TIMESTAMP,'admin'),

-- Q8
(29,'PRIMARY KEY',8,CURRENT_TIMESTAMP,'admin'),
(30,'UNIQUE',8,CURRENT_TIMESTAMP,'admin'),
(31,'FOREIGN KEY',8,CURRENT_TIMESTAMP,'admin'),
(32,'CHECK',8,CURRENT_TIMESTAMP,'admin'),

-- Q9
(33,'Atomicity',9,CURRENT_TIMESTAMP,'admin'),
(34,'Consistency',9,CURRENT_TIMESTAMP,'admin'),
(35,'Isolation',9,CURRENT_TIMESTAMP,'admin'),
(36,'Durability',9,CURRENT_TIMESTAMP,'admin'),

-- Q10
(37,'Software as a Service',10,CURRENT_TIMESTAMP,'admin'),
(38,'System as a Service',10,CURRENT_TIMESTAMP,'admin'),
(39,'Storage as a Service',10,CURRENT_TIMESTAMP,'admin'),
(40,'Security as a Service',10,CURRENT_TIMESTAMP,'admin'),

-- Q11
(41,'EC2',11,CURRENT_TIMESTAMP,'admin'),
(42,'S3',11,CURRENT_TIMESTAMP,'admin'),
(43,'RDS',11,CURRENT_TIMESTAMP,'admin'),
(44,'Lambda',11,CURRENT_TIMESTAMP,'admin'),

-- Q12
(45,'EC2',12,CURRENT_TIMESTAMP,'admin'),
(46,'Lambda',12,CURRENT_TIMESTAMP,'admin'),
(47,'S3',12,CURRENT_TIMESTAMP,'admin'),
(48,'DynamoDB',12,CURRENT_TIMESTAMP,'admin'),

-- Q13
(49,'O(n)',13,CURRENT_TIMESTAMP,'admin'),
(50,'O(log n)',13,CURRENT_TIMESTAMP,'admin'),
(51,'O(1)',13,CURRENT_TIMESTAMP,'admin'),
(52,'O(n²)',13,CURRENT_TIMESTAMP,'admin'),

-- Q14
(53,'Fixed size',14,CURRENT_TIMESTAMP,'admin'),
(54,'Faster access',14,CURRENT_TIMESTAMP,'admin'),
(55,'Dynamic Size',14,CURRENT_TIMESTAMP,'admin'),
(56,'Less memory',14,CURRENT_TIMESTAMP,'admin'),

-- Q15
(57,'FIFO',15,CURRENT_TIMESTAMP,'admin'),
(58,'LILO',15,CURRENT_TIMESTAMP,'admin'),
(59,'Random',15,CURRENT_TIMESTAMP,'admin'),
(60,'LIFO',15,CURRENT_TIMESTAMP,'admin'),

-- Q16
(61,'O(n)',16,CURRENT_TIMESTAMP,'admin'),
(62,'O(log n)',16,CURRENT_TIMESTAMP,'admin'),
(63,'O(n log n)',16,CURRENT_TIMESTAMP,'admin'),
(64,'O(1)',16,CURRENT_TIMESTAMP,'admin'),

-- Q17
(65,'Quick Sort',17,CURRENT_TIMESTAMP,'admin'),
(66,'Bubble Sort',17,CURRENT_TIMESTAMP,'admin'),
(67,'Insertion Sort',17,CURRENT_TIMESTAMP,'admin'),
(68,'Selection Sort',17,CURRENT_TIMESTAMP,'admin'),

-- Q18
(69,'Infinite loop',18,CURRENT_TIMESTAMP,'admin'),
(70,'Compilation error',18,CURRENT_TIMESTAMP,'admin'),
(71,'Stack Overflow',18,CURRENT_TIMESTAMP,'admin'),
(72,'Memory leak',18,CURRENT_TIMESTAMP,'admin'),

-- Q19
(73,'docker run',19,CURRENT_TIMESTAMP,'admin'),
(74,'docker ps',19,CURRENT_TIMESTAMP,'admin'),
(75,'docker start',19,CURRENT_TIMESTAMP,'admin'),
(76,'docker list',19,CURRENT_TIMESTAMP,'admin'),

-- Q20
(77,'Pod',20,CURRENT_TIMESTAMP,'admin'),
(78,'Container',20,CURRENT_TIMESTAMP,'admin'),
(79,'Cluster',20,CURRENT_TIMESTAMP,'admin'),
(80,'Node',20,CURRENT_TIMESTAMP,'admin'),

-- Q21
(81,'Testing',21,CURRENT_TIMESTAMP,'admin'),
(82,'Compile source code',21,CURRENT_TIMESTAMP,'admin'),
(83,'Deploy app',21,CURRENT_TIMESTAMP,'admin'),
(84,'Monitor logs',21,CURRENT_TIMESTAMP,'admin'),

-- Q22
(85,'Simple function',22,CURRENT_TIMESTAMP,'admin'),
(86,'Callback',22,CURRENT_TIMESTAMP,'admin'),
(87,'Promise',22,CURRENT_TIMESTAMP,'admin'),
(88,'Function with its lexical environment',22,CURRENT_TIMESTAMP,'admin'),

-- Q23
(89,'await',23,CURRENT_TIMESTAMP,'admin'),
(90,'async',23,CURRENT_TIMESTAMP,'admin'),
(91,'wait',23,CURRENT_TIMESTAMP,'admin'),
(92,'pause',23,CURRENT_TIMESTAMP,'admin'),

-- Q24
(93,'No difference',24,CURRENT_TIMESTAMP,'admin'),
(94,'== faster',24,CURRENT_TIMESTAMP,'admin'),
(95,'Type checking',24,CURRENT_TIMESTAMP,'admin'),
(96,'=== slower',24,CURRENT_TIMESTAMP,'admin'),

-- Q25
(97,'.append()',25,CURRENT_TIMESTAMP,'admin'),
(98,'.add()',25,CURRENT_TIMESTAMP,'admin'),
(99,'.push()',25,CURRENT_TIMESTAMP,'admin'),
(100,'.insert()',25,CURRENT_TIMESTAMP,'admin'),

-- Q26
(101,'#',26,CURRENT_TIMESTAMP,'admin'),
(102,'@',26,CURRENT_TIMESTAMP,'admin'),
(103,'$',26,CURRENT_TIMESTAMP,'admin'),
(104,'%',26,CURRENT_TIMESTAMP,'admin'),

-- Q27
(105,'Global Index Lock',27,CURRENT_TIMESTAMP,'admin'),
(106,'General Interpreter Lock',27,CURRENT_TIMESTAMP,'admin'),
(107,'Global Instruction Lock',27,CURRENT_TIMESTAMP,'admin'),
(108,'Global Interpreter Lock',27,CURRENT_TIMESTAMP,'admin'),

-- Q28
(109,'Parameterized Queries',28,CURRENT_TIMESTAMP,'admin'),
(110,'String concatenation',28,CURRENT_TIMESTAMP,'admin'),
(111,'Hardcoding',28,CURRENT_TIMESTAMP,'admin'),
(112,'Ignoring input',28,CURRENT_TIMESTAMP,'admin'),

-- Q29
(113,'Session only',29,CURRENT_TIMESTAMP,'admin'),
(114,'Local Storage or Cookies',29,CURRENT_TIMESTAMP,'admin'),
(115,'Database',29,CURRENT_TIMESTAMP,'admin'),
(116,'Server logs',29,CURRENT_TIMESTAMP,'admin'),

-- Q30
(117,'Cross-Origin Resource Sharing',30,CURRENT_TIMESTAMP,'admin'),
(118,'Central Object Resource Sh̵̵aring',30,CURRENT_TIMESTAMP,'admin'),
(119,'Cross Object Request System',30,CURRENT_TIMESTAMP,'admin'),
(120,'Client-Origin Resource Sharing',30,CURRENT_TIMESTAMP,'admin');

-- Question Answers (Mapping Correct Answers Randomly)
INSERT INTO question_answer (question_id, option_id) VALUES
                                                         (1, 2),   -- Heap Memory
                                                         (2, 5),   -- extends
                                                         (3, 10),  -- No (assuming option 10 is 'No')
                                                         (4, 13),  -- h1
                                                         (5, 18),  -- Padding
                                                         (6, 23),  -- #symbol
                                                         (7, 27),  -- FULL OUTER JOIN
                                                         (8, 30),  -- UNIQUE
                                                         (9, 33),  -- Atomicity
                                                         (10, 39), -- Software as a Service
                                                         (11, 41), -- EC2
                                                         (12, 46), -- Lambda
                                                         (13, 51), -- O(1)
                                                         (14, 55), -- Dynamic Size
                                                         (15, 60), -- LIFO
                                                         (16, 62), -- O(log n)
                                                         (17, 65), -- Quick Sort
                                                         (18, 71), -- Stack Overflow
                                                         (19, 74), -- docker ps
                                                         (20, 77), -- Pod
                                                         (21, 82), -- Compile source code
                                                         (22, 88), -- Function with its lexical environment
                                                         (23, 89), -- await
                                                         (24, 95), -- Type checking
                                                         (25, 97), -- .append()
                                                         (26, 102),-- @
                                                         (27, 108),-- Global Interpreter Lock
                                                         (28, 109),-- Parameterized Queries
                                                         (29, 114),-- Local Storage or Cookies
                                                         (30, 117);-- Cross-Origin Resource Sharing


---------------------------------------------------------------------------------------------------------------------------------------------
INSERT INTO questions (id, title, description, created_at, created_by) VALUES
-- Java
(31, 'Java Interfaces', 'Can an interface have default methods?', CURRENT_TIMESTAMP, 'admin'),
(32, 'Java Exceptions', 'Which keyword is used to handle exceptions?', CURRENT_TIMESTAMP, 'admin'),

-- Frontend
(33, 'HTML Forms', 'Which tag is used to create a form?', CURRENT_TIMESTAMP, 'admin'),
(34, 'CSS Flexbox', 'Which property defines flex direction?', CURRENT_TIMESTAMP, 'admin'),

-- SQL
(35, 'SQL Index', 'What is the purpose of an index?', CURRENT_TIMESTAMP, 'admin'),
(36, 'Normalization', 'Which normal form removes transitive dependency?', CURRENT_TIMESTAMP, 'admin'),

-- Cloud
(37, 'IaaS', 'What does IaaS stand for?', CURRENT_TIMESTAMP, 'admin'),
(38, 'Cloud Scaling', 'What is horizontal scaling?', CURRENT_TIMESTAMP, 'admin'),

-- Data Structures
(39, 'Queue DS', 'Which principle does a Queue follow?', CURRENT_TIMESTAMP, 'admin'),
(40, 'Tree Height', 'What is height of a tree?', CURRENT_TIMESTAMP, 'admin'),

-- Algorithms
(41, 'Merge Sort', 'What is time complexity of Merge Sort?', CURRENT_TIMESTAMP, 'admin'),
(42, 'Greedy Algorithm', 'Does greedy always give optimal solution?', CURRENT_TIMESTAMP, 'admin'),

-- DevOps
(43, 'Docker Image', 'What is a Docker image?', CURRENT_TIMESTAMP, 'admin'),
(44, 'CI/CD Deploy', 'What is deployment stage?', CURRENT_TIMESTAMP, 'admin'),

-- JavaScript
(45, 'JS Hoisting', 'What is hoisted in JavaScript?', CURRENT_TIMESTAMP, 'admin'),
(46, 'Event Loop', 'What handles async callbacks?', CURRENT_TIMESTAMP, 'admin'),

-- Python
(47, 'Python Tuples', 'Are tuples mutable?', CURRENT_TIMESTAMP, 'admin'),
(48, 'Python Lambda', 'What is a lambda function?', CURRENT_TIMESTAMP, 'admin'),

-- Security
(49, 'Encryption', 'What is symmetric encryption?', CURRENT_TIMESTAMP, 'admin'),
(50, 'HTTPS', 'What does HTTPS ensure?', CURRENT_TIMESTAMP, 'admin');

INSERT INTO question_category (question_id, category_id) VALUES
                                                             (31,1),(32,1),
                                                             (33,2),(34,2),
                                                             (35,3),(36,3),
                                                             (37,4),(38,4),
                                                             (39,5),(40,5),
                                                             (41,6),(42,6),
                                                             (43,7),(44,7),
                                                             (45,8),(46,8),
                                                             (47,9),(48,9),
                                                             (49,10),(50,10);

INSERT INTO question_options (id, title, question_id, created_at, created_by) VALUES

-- Q31
(121,'Yes',31,CURRENT_TIMESTAMP,'admin'),
(122,'No',31,CURRENT_TIMESTAMP,'admin'),
(123,'Only static',31,CURRENT_TIMESTAMP,'admin'),
(124,'Only abstract',31,CURRENT_TIMESTAMP,'admin'),

-- Q32
(125,'try-catch',32,CURRENT_TIMESTAMP,'admin'),
(126,'throw',32,CURRENT_TIMESTAMP,'admin'),
(127,'throws',32,CURRENT_TIMESTAMP,'admin'),
(128,'final',32,CURRENT_TIMESTAMP,'admin'),

-- Q33
(129,'form',33,CURRENT_TIMESTAMP,'admin'),
(130,'input',33,CURRENT_TIMESTAMP,'admin'),
(131,'label',33,CURRENT_TIMESTAMP,'admin'),
(132,'button',33,CURRENT_TIMESTAMP,'admin'),

-- Q34
(133,'flex-direction',34,CURRENT_TIMESTAMP,'admin'),
(134,'align-items',34,CURRENT_TIMESTAMP,'admin'),
(135,'justify-content',34,CURRENT_TIMESTAMP,'admin'),
(136,'display',34,CURRENT_TIMESTAMP,'admin'),

-- Q35
(137,'Speed up queries',35,CURRENT_TIMESTAMP,'admin'),
(138,'Delete data',35,CURRENT_TIMESTAMP,'admin'),
(139,'Store backup',35,CURRENT_TIMESTAMP,'admin'),
(140,'Normalize DB',35,CURRENT_TIMESTAMP,'admin'),

-- Q36
(141,'1NF',36,CURRENT_TIMESTAMP,'admin'),
(142,'2NF',36,CURRENT_TIMESTAMP,'admin'),
(143,'3NF',36,CURRENT_TIMESTAMP,'admin'),
(144,'BCNF',36,CURRENT_TIMESTAMP,'admin'),

-- Q37
(145,'Infrastructure as a Service',37,CURRENT_TIMESTAMP,'admin'),
(146,'Internet as a Service',37,CURRENT_TIMESTAMP,'admin'),
(147,'Integration as a Service',37,CURRENT_TIMESTAMP,'admin'),
(148,'Instance as a Service',37,CURRENT_TIMESTAMP,'admin'),

-- Q38
(149,'Add more machines',38,CURRENT_TIMESTAMP,'admin'),
(150,'Increase CPU',38,CURRENT_TIMESTAMP,'admin'),
(151,'Increase RAM',38,CURRENT_TIMESTAMP,'admin'),
(152,'Optimize code',38,CURRENT_TIMESTAMP,'admin'),

-- Q39
(153,'FIFO',39,CURRENT_TIMESTAMP,'admin'),
(154,'LIFO',39,CURRENT_TIMESTAMP,'admin'),
(155,'Random',39,CURRENT_TIMESTAMP,'admin'),
(156,'Priority',39,CURRENT_TIMESTAMP,'admin'),

-- Q40
(157,'Max depth',40,CURRENT_TIMESTAMP,'admin'),
(158,'Node count',40,CURRENT_TIMESTAMP,'admin'),
(159,'Leaf nodes',40,CURRENT_TIMESTAMP,'admin'),
(160,'Edges count',40,CURRENT_TIMESTAMP,'admin'),

-- Q41
(161,'O(n log n)',41,CURRENT_TIMESTAMP,'admin'),
(162,'O(n²)',41,CURRENT_TIMESTAMP,'admin'),
(163,'O(log n)',41,CURRENT_TIMESTAMP,'admin'),
(164,'O(n)',41,CURRENT_TIMESTAMP,'admin'),

-- Q42
(165,'Yes',42,CURRENT_TIMESTAMP,'admin'),
(166,'No',42,CURRENT_TIMESTAMP,'admin'),
(167,'Sometimes',42,CURRENT_TIMESTAMP,'admin'),
(168,'Never',42,CURRENT_TIMESTAMP,'admin'),

-- Q43
(169,'Blueprint of container',43,CURRENT_TIMESTAMP,'admin'),
(170,'Running container',43,CURRENT_TIMESTAMP,'admin'),
(171,'Virtual machine',43,CURRENT_TIMESTAMP,'admin'),
(172,'OS kernel',43,CURRENT_TIMESTAMP,'admin'),

-- Q44
(173,'Release to production',44,CURRENT_TIMESTAMP,'admin'),
(174,'Compile code',44,CURRENT_TIMESTAMP,'admin'),
(175,'Write code',44,CURRENT_TIMESTAMP,'admin'),
(176,'Test code',44,CURRENT_TIMESTAMP,'admin'),

-- Q45
(177,'Variables & functions',45,CURRENT_TIMESTAMP,'admin'),
(178,'Only variables',45,CURRENT_TIMESTAMP,'admin'),
(179,'Only functions',45,CURRENT_TIMESTAMP,'admin'),
(180,'Nothing',45,CURRENT_TIMESTAMP,'admin'),

-- Q46
(181,'Event Loop',46,CURRENT_TIMESTAMP,'admin'),
(182,'Call Stack',46,CURRENT_TIMESTAMP,'admin'),
(183,'Heap',46,CURRENT_TIMESTAMP,'admin'),
(184,'Thread',46,CURRENT_TIMESTAMP,'admin'),

-- Q47
(185,'Yes',47,CURRENT_TIMESTAMP,'admin'),
(186,'No',47,CURRENT_TIMESTAMP,'admin'),
(187,'Sometimes',47,CURRENT_TIMESTAMP,'admin'),
(188,'Only lists mutable',47,CURRENT_TIMESTAMP,'admin'),

-- Q48
(189,'Anonymous function',48,CURRENT_TIMESTAMP,'admin'),
(190,'Loop',48,CURRENT_TIMESTAMP,'admin'),
(191,'Class',48,CURRENT_TIMESTAMP,'admin'),
(192,'Module',48,CURRENT_TIMESTAMP,'admin'),

-- Q49
(193,'Same key encryption',49,CURRENT_TIMESTAMP,'admin'),
(194,'Two key encryption',49,CURRENT_TIMESTAMP,'admin'),
(195,'Hashing',49,CURRENT_TIMESTAMP,'admin'),
(196,'Tokenization',49,CURRENT_TIMESTAMP,'admin'),

-- Q50
(197,'Secure communication',50,CURRENT_TIMESTAMP,'admin'),
(198,'Faster speed',50,CURRENT_TIMESTAMP,'admin'),
(199,'Caching',50,CURRENT_TIMESTAMP,'admin'),
(200,'Compression',50,CURRENT_TIMESTAMP,'admin');
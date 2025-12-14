-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 14, 2025 at 02:12 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.1.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `university_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `books`
--

CREATE TABLE `books` (
  `id` int(11) NOT NULL,
  `title` varchar(200) NOT NULL,
  `author` varchar(100) NOT NULL,
  `isbn` varchar(20) NOT NULL,
  `category` varchar(50) DEFAULT NULL,
  `description` text DEFAULT NULL,
  `cover_image` varchar(255) DEFAULT 'default_cover.jpg',
  `quantity` int(11) DEFAULT 1,
  `available` int(11) DEFAULT 1,
  `added_by` int(11) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `owner_id` int(11) DEFAULT NULL,
  `status` enum('available','unavailable','borrowed','reserved') DEFAULT 'available',
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `available_copies` int(11) DEFAULT 1,
  `total_copies` int(11) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `books`
--

INSERT INTO `books` (`id`, `title`, `author`, `isbn`, `category`, `description`, `cover_image`, `quantity`, `available`, `added_by`, `created_at`, `owner_id`, `status`, `updated_at`, `available_copies`, `total_copies`) VALUES
(1, 'The Great Gatsby', 'F. Scott Fitzgerald', '9780743273565', 'Fiction', 'A classic novel of the Jazz Age', 'default_cover.jpg', 5, 5, NULL, '2025-12-13 20:04:54', NULL, 'available', '2025-12-13 23:20:15', 1, 1),
(2, 'To Kill a Mockingbird', 'Harper Lee', '9780061120084', 'Fiction', 'A gripping tale of racial injustice', 'default_cover.jpg', 3, 3, NULL, '2025-12-13 20:04:54', NULL, 'available', '2025-12-13 23:20:15', 1, 1),
(3, '1984', 'George Orwell', '9780451524935', 'Science Fiction', 'Dystopian social science fiction', 'default_cover.jpg', 4, 4, NULL, '2025-12-13 20:04:54', NULL, 'available', '2025-12-13 23:20:15', 1, 1),
(4, 'Pride and Prejudice', 'Jane Austen', '9780141439518', 'Romance', 'A romantic novel of manners', 'default_cover.jpg', 2, 2, NULL, '2025-12-13 20:04:54', NULL, 'available', '2025-12-13 23:20:15', 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `books_backup`
--

CREATE TABLE `books_backup` (
  `id` int(11) NOT NULL DEFAULT 0,
  `title` varchar(200) NOT NULL,
  `author` varchar(100) NOT NULL,
  `isbn` varchar(20) NOT NULL,
  `category` varchar(50) DEFAULT NULL,
  `description` text DEFAULT NULL,
  `cover_image` varchar(255) DEFAULT 'default_cover.jpg',
  `quantity` int(11) DEFAULT 1,
  `available` int(11) DEFAULT 1,
  `added_by` int(11) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `owner_id` int(11) DEFAULT NULL,
  `status` enum('available','unavailable','borrowed','reserved') DEFAULT 'available',
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `available_copies` int(11) DEFAULT 1,
  `total_copies` int(11) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `books_backup`
--

INSERT INTO `books_backup` (`id`, `title`, `author`, `isbn`, `category`, `description`, `cover_image`, `quantity`, `available`, `added_by`, `created_at`, `owner_id`, `status`, `updated_at`, `available_copies`, `total_copies`) VALUES
(1, 'The Great Gatsby', 'F. Scott Fitzgerald', '9780743273565', 'Fiction', 'A classic novel of the Jazz Age', 'default_cover.jpg', 5, 5, NULL, '2025-12-13 20:04:54', NULL, 'available', '2025-12-13 23:20:15', 1, 1),
(2, 'To Kill a Mockingbird', 'Harper Lee', '9780061120084', 'Fiction', 'A gripping tale of racial injustice', 'default_cover.jpg', 3, 3, NULL, '2025-12-13 20:04:54', NULL, 'available', '2025-12-13 23:20:15', 1, 1),
(3, '1984', 'George Orwell', '9780451524935', 'Science Fiction', 'Dystopian social science fiction', 'default_cover.jpg', 4, 4, NULL, '2025-12-13 20:04:54', NULL, 'available', '2025-12-13 23:20:15', 1, 1),
(4, 'Pride and Prejudice', 'Jane Austen', '9780141439518', 'Romance', 'A romantic novel of manners', 'default_cover.jpg', 2, 2, NULL, '2025-12-13 20:04:54', NULL, 'available', '2025-12-13 23:20:15', 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `book_owners`
--

CREATE TABLE `book_owners` (
  `id` int(11) NOT NULL,
  `book_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `is_primary` tinyint(1) DEFAULT 1,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `borrowings`
--

CREATE TABLE `borrowings` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `book_id` int(11) NOT NULL,
  `borrow_date` date NOT NULL,
  `return_date` date DEFAULT NULL,
  `due_date` date NOT NULL,
  `status` enum('pending','approved','borrowed','returned','overdue','rejected') DEFAULT 'pending',
  `request_date` timestamp NOT NULL DEFAULT current_timestamp(),
  `approved_by` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `borrow_requests`
--

CREATE TABLE `borrow_requests` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `book_id` int(11) NOT NULL,
  `borrow_date` date NOT NULL,
  `return_date` date NOT NULL,
  `request_time` datetime NOT NULL,
  `approved_time` datetime DEFAULT NULL,
  `status` enum('pending','approved','rejected','returned') DEFAULT 'pending',
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `contact_messages`
--

CREATE TABLE `contact_messages` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `subject` varchar(200) NOT NULL,
  `message` text NOT NULL,
  `ip_address` varchar(45) DEFAULT NULL,
  `user_agent` text DEFAULT NULL,
  `status` enum('unread','read','replied') DEFAULT 'unread',
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `reading_history`
--

CREATE TABLE `reading_history` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `book_id` int(11) NOT NULL,
  `read_date` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `settings`
--

CREATE TABLE `settings` (
  `id` int(11) NOT NULL,
  `setting_key` varchar(100) NOT NULL,
  `value` text DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `students`
--

CREATE TABLE `students` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `age` int(11) DEFAULT NULL,
  `email` varchar(100) NOT NULL,
  `course` varchar(50) DEFAULT NULL,
  `grade` char(2) DEFAULT NULL,
  `registration_date` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `students`
--

INSERT INTO `students` (`id`, `name`, `age`, `email`, `course`, `grade`, `registration_date`) VALUES
(6, 'MURAGIJIMANA Janvier', 23, 'mj6041551@gmail.com', 'Programming', 'A', '2025-12-12 18:08:13'),
(7, 'IBYIMANA Ikora ubalide', 34, 'ubalide@gmail.com', 'Networking', 'A', '2025-12-12 18:09:19'),
(8, 'NDAYISHIMIYE Igiraneza regis', 23, 'ndayishimiye@gmail.com', 'java', 'B+', '2025-12-12 18:11:03'),
(9, 'SEKAMANa Theogen', 21, 'sekaman@gmail.com', 'DATABASE', 'C', '2025-12-12 18:11:50'),
(10, 'nkusi', 25, 'nkusi@gmail.com', 'ipbasecamera', 'D', '2025-12-12 18:13:13'),
(11, 'Yesu', 23, '43yesu@gmail.com', 'maintenance', 'F', '2025-12-12 19:02:07');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `full_name` varchar(100) NOT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `role` enum('reader','borrower','lender') DEFAULT 'reader',
  `status` enum('active','suspended') DEFAULT 'active',
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `last_login` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `email`, `password`, `full_name`, `phone`, `role`, `status`, `created_at`, `last_login`) VALUES
(1, 'lender', 'lender@library.com', '$2y$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'Library Manager', NULL, 'lender', 'active', '2025-12-13 20:04:50', NULL),
(2, 'muragije', 'muragije@gmail.com', '123456', 'janvier', NULL, 'reader', 'active', '2025-12-13 20:17:43', NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `books`
--
ALTER TABLE `books`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `isbn` (`isbn`),
  ADD KEY `added_by` (`added_by`),
  ADD KEY `fk_books_owner` (`owner_id`);

--
-- Indexes for table `book_owners`
--
ALTER TABLE `book_owners`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `unique_book_user` (`book_id`,`user_id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `borrowings`
--
ALTER TABLE `borrowings`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `book_id` (`book_id`),
  ADD KEY `approved_by` (`approved_by`);

--
-- Indexes for table `borrow_requests`
--
ALTER TABLE `borrow_requests`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `book_id` (`book_id`);

--
-- Indexes for table `contact_messages`
--
ALTER TABLE `contact_messages`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `reading_history`
--
ALTER TABLE `reading_history`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `book_id` (`book_id`);

--
-- Indexes for table `settings`
--
ALTER TABLE `settings`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `setting_key` (`setting_key`),
  ADD KEY `idx_setting_key` (`setting_key`);

--
-- Indexes for table `students`
--
ALTER TABLE `students`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `books`
--
ALTER TABLE `books`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `book_owners`
--
ALTER TABLE `book_owners`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `borrowings`
--
ALTER TABLE `borrowings`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `borrow_requests`
--
ALTER TABLE `borrow_requests`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `contact_messages`
--
ALTER TABLE `contact_messages`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `reading_history`
--
ALTER TABLE `reading_history`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `settings`
--
ALTER TABLE `settings`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `students`
--
ALTER TABLE `students`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `books`
--
ALTER TABLE `books`
  ADD CONSTRAINT `books_ibfk_1` FOREIGN KEY (`added_by`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `fk_books_owner` FOREIGN KEY (`owner_id`) REFERENCES `users` (`id`) ON DELETE SET NULL;

--
-- Constraints for table `book_owners`
--
ALTER TABLE `book_owners`
  ADD CONSTRAINT `book_owners_ibfk_1` FOREIGN KEY (`book_id`) REFERENCES `books` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `book_owners_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `borrowings`
--
ALTER TABLE `borrowings`
  ADD CONSTRAINT `borrowings_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `borrowings_ibfk_2` FOREIGN KEY (`book_id`) REFERENCES `books` (`id`),
  ADD CONSTRAINT `borrowings_ibfk_3` FOREIGN KEY (`approved_by`) REFERENCES `users` (`id`);

--
-- Constraints for table `borrow_requests`
--
ALTER TABLE `borrow_requests`
  ADD CONSTRAINT `borrow_requests_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `borrow_requests_ibfk_2` FOREIGN KEY (`book_id`) REFERENCES `books` (`id`);

--
-- Constraints for table `reading_history`
--
ALTER TABLE `reading_history`
  ADD CONSTRAINT `reading_history_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `reading_history_ibfk_2` FOREIGN KEY (`book_id`) REFERENCES `books` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

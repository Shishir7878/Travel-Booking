USE travel_booking_system;
e
CREATE TABLE users (
    email VARCHAR(100) PRIMARY KEY,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE travel_packages (
    package_id INT PRIMARY KEY,
    destination VARCHAR(100),
    price FLOAT,
    description TEXT
);


CREATE TABLE bookings (
    booking_id VARCHAR(50) PRIMARY KEY,
    user_id VARCHAR(100), 
    package_id INT,
    travel_date DATE,
    number_of_travelers INT,
    FOREIGN KEY (user_id) REFERENCES users(email),
    FOREIGN KEY (package_id) REFERENCES travel_packages(package_id)
);

INSERT INTO users (email, password) VALUES ('shishir@gmail.com', 'password123');
INSERT INTO users (email, password) VALUES ('trav2@gmail.com', 'password456');


INSERT INTO bookings (booking_id, user_id, package_id, travel_date, number_of_travelers)
VALUES 
('B001', 'shishir@gmail.com', 1, '2024-12-01', 2),
('B002', 'trav2@gmail.com', 2, '2024-11-20', 3);

INSERT INTO travel_packages (package_id, destination, price, description) VALUES
(25, 'Bali', 1400.0, '7-day tropical beach escape including Nusa Dua and Ubud temples'),
(26, 'Machu Picchu', 2500.0, '5-day trek to Machu Picchu with Inca Trail hike'),
(27, 'Kyoto', 1700.0, '4-day cultural tour exploring ancient temples and traditional tea houses'),
(28, 'Cairo', 1800.0, '6-day Egypt tour, including Pyramids of Giza and the Sphinx'),
(29, 'Antarctica', 5000.0, '10-day adventure including icebergs, penguins, and scientific research stations'),
(30, 'Zanzibar', 2200.0, '5-day island getaway with Spice Tours and beach relaxation'),
(31, 'Iceland', 3000.0, '8-day road trip around Iceland exploring waterfalls, volcanoes, and glaciers'),
(32, 'Petra', 2500.0, '6-day historical tour of ancient Petra and Wadi Rum desert in Jordan'),
(33, 'New Zealand', 3200.0, '9-day adventure tour including Hobbiton, Milford Sound, and Queenstown'),
(34, 'South Africa', 2800.0, '8-day safari tour in Kruger National Park with cultural experiences'),
(35, 'Reykjavik', 1200.0, '4-day city break with geothermal spas and nearby hot springs'),
(36, 'Lisbon', 1500.0, '5-day European city tour including Belem Tower, Lisbon Cathedral'),
(37, 'Marrakech', 1700.0, '6-day Moroccan tour including the Atlas Mountains and Medina'),
(38, 'Great Barrier Reef', 2500.0, '5-day diving and snorkeling tour exploring the coral reefs'),
(39, 'Dubai Desert Safari', 1800.0, '4-day desert adventure including dune bashing and camel rides'),
(40, 'Tokyo Disney Resort', 2400.0, '7-day trip to Tokyo Disneyland and DisneySea'),
(41, 'Hawaii', 2900.0, '6-day island-hopping tour with beaches, volcanoes, and rainforests'),
(42, 'Galapagos Islands', 4500.0, '8-day wildlife tour to see unique species and volcanic landscapes'),
(43, 'Northern Lights', 3500.0, '7-day winter tour in Norway to witness the Aurora Borealis'),
(44, 'Budapest', 1600.0, '4-day thermal bath and river cruise tour of Hungary’s capital');


SELECT * FROM travel_packages WHERE destination = 'Paris';



SELECT * FROM users;
SELECT * FROM travel_packages;
SELECT * FROM bookings;



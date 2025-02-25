# Travel Booking System

The **Travel Booking System** is a web services-based application that allows users to manage travel bookings. The system enables users to create, update, delete, and view bookings, as well as explore available travel packages. It also includes user authentication (login and registration) to ensure secure access.

This project is developed using Java GUI (JFrame) for the front end, HTTP requests for communication with the backend, and a relational database to store user and booking data.

## Features

### User Authentication
- **Register**: New users can create an account by providing their email and password.
- **Login**: Existing users can log in to access the system.

### Travel Packages
- **View Travel Packages**: Users can browse a list of available travel packages.
- **Package Details**: Users can view the details of each package (e.g., destination, price).

### Booking Management
- **Create Booking**: Users can book a travel package.
- **Update Booking**: Users can modify their existing bookings.
- **Delete Booking**: Users can cancel their bookings.
- **View Bookings**: Users can view a list of all their bookings.

## Technologies Used

- **Frontend**: Java GUI (JFrame)
- **Backend**: Web services (HTTP requests)
- **Database**: MySQL (or any relational database)
- **Programming Language**: Java
- **Tools**: 
  - Maven (for dependency management)
  - Git (for version control)

## How It Works

### User Authentication
- Users register with their email and password.
- Registered users log in to access the system.

### Travel Packages
- The system fetches available travel packages from the database and displays them in the GUI.
- Users can view details of each package and make a decision based on the information provided.

### Booking Management
- Users can create a booking by selecting a travel package.
- Bookings can be updated or deleted as needed.
- Users can view all their bookings in a list format.

### Database Interaction
- All user data, travel packages, and bookings are stored in a relational database.
- The Java application communicates with the database via HTTP requests to the backend.

## Installation

1. **Clone the repository:**
   ```bash
   git clone https://github.com/your-username/travel-booking-system.git

# Distributed File Storage System
## Overview
This project implements a distributed file storage system using Java Remote Method Invocation (RMI). The application is designed to be highly available and fault-tolerant, using a suite of distributed systems algorithms to manage data across multiple clients and servers. It is built to demonstrate core concepts of distributed computing in a practical, hands-on manner.

## Key Algorithms Implemented
Two-Phase Commit (2PC): Ensures all participating servers either commit or abort a transaction, guaranteeing atomicity across the distributed system.

Timeout: Manages server-side and client-side timeouts to handle network failures or unresponsive processes.

Data Management Replication: Ensures data consistency and availability by replicating files across multiple servers.

## Getting Started
This project can be run locally or via Docker. The recommended development environment is IntelliJ IDEA.

## Prerequisites
Java Development Kit (JDK) 8 or higher

Apache Maven or a similar build tool

Docker (for containerized deployment)

## Running the Application
1. Using Docker (Recommended)
The easiest way to run the application is by using the provided start.sh script, which automates the Docker container setup.


<img width="768" height="610" alt="Screenshot 2025-09-24 at 11 17 52 AM" src="https://github.com/user-attachments/assets/207de600-ee11-451e-82c1-36c86d876dbb" />

<img width="807" height="143" alt="Screenshot 2025-09-24 at 11 18 04 AM" src="https://github.com/user-attachments/assets/a4a4590a-e618-44f7-904f-baa0f88652ae" />

## File Operations
The application supports standard file operations:

Upload: The file name provided must exactly match the file name in the specified local path.

Download: When downloading, the destination path's filename can be different from the original.

## Known Issues and Future Improvements
This project is a work in progress, and the following are known issues and areas for future development:

Consistency: The system is not fully fault-tolerant; server restarts are required to maintain consistency with the database.

MongoDB Connection: The MongoDB connection string is currently hardcoded.

Error Handling: Error and failure messages could be more descriptive.

Design: The codebase violates some SOLID principles and could be better organized.

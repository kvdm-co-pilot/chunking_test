# Deployment Guide for Bitcoin Transaction and Wallet Management Application

## Introduction
This guide provides comprehensive instructions for deploying the Bitcoin Transaction and Wallet Management application. The application is designed to manage Bitcoin transactions, wallets, and cryptographic keys securely and efficiently. This document covers the deployment process, including prerequisites, setup, configuration, and verification steps to ensure a successful deployment in a production environment.

## Prerequisites

### System Requirements
- **Operating System**: Linux (Ubuntu 20.04 or later recommended)
- **Java Development Kit**: JDK 11 or later
- **Kotlin**: Kotlin 1.5 or later
- **Database**: PostgreSQL 12 or later
- **Memory**: Minimum 4GB RAM
- **Disk Space**: Minimum 10GB free space
- **Network**: Stable internet connection for accessing Bitcoin network and dependencies

### Software Dependencies
- **Gradle**: Version 6.8 or later for build automation
- **Docker**: Version 20.10 or later for containerization (optional)
- **Bitcoin Core**: Version 0.21 or later for full node operations

### Security Considerations
- Ensure secure SSH access to the server.
- Configure firewall rules to allow necessary ports (e.g., 8333 for Bitcoin, 5432 for PostgreSQL).
- Use SSL/TLS for secure communication between components.

## Deployment Steps

### Step 1: Environment Setup
1. **Install JDK and Kotlin**:
   ```bash
   sudo apt update
   sudo apt install openjdk-11-jdk
   curl -s https://get.sdkman.io | bash
   sdk install kotlin
   ```

2. **Install Gradle**:
   ```bash
   sdk install gradle 6.8
   ```

3. **Install Docker (optional)**:
   ```bash
   sudo apt install docker.io
   sudo systemctl start docker
   sudo systemctl enable docker
   ```

4. **Install PostgreSQL**:
   ```bash
   sudo apt install postgresql postgresql-contrib
   sudo systemctl start postgresql
   sudo systemctl enable postgresql
   ```

5. **Install Bitcoin Core**:
   Follow the official Bitcoin Core installation guide for your operating system.

### Step 2: Codebase Configuration
1. **Clone the Repository**:
   ```bash
   git clone https://github.com/example/bitcoin-wallet-management.git
   cd bitcoin-wallet-management
   ```

2. **Configure Database**:
   - Update `src/main/resources/application.properties` with your PostgreSQL credentials.
   - Example configuration:
     ```
     spring.datasource.url=jdbc:postgresql://localhost:5432/bitcoin_db
     spring.datasource.username=your_username
     spring.datasource.password=your_password
     ```

3. **Configure Bitcoin Core**:
   - Ensure Bitcoin Core is running and synchronized with the network.
   - Update `src/main/resources/application.properties` with Bitcoin Core RPC credentials.
   - Example configuration:
     ```
     bitcoin.rpc.url=http://localhost:8332
     bitcoin.rpc.username=rpcuser
     bitcoin.rpc.password=rpcpassword
     ```

### Step 3: Build and Deploy
1. **Build the Application**:
   ```bash
   ./gradlew build
   ```

2. **Run the Application**:
   - **Without Docker**:
     ```bash
     java -jar build/libs/bitcoin-wallet-management.jar
     ```
   - **With Docker**:
     - Create a Dockerfile in the root directory:
       ```dockerfile
       FROM openjdk:11-jre-slim
       COPY build/libs/bitcoin-wallet-management.jar /app.jar
       ENTRYPOINT ["java", "-jar", "/app.jar"]
       ```
     - Build and run the Docker container:
       ```bash
       docker build -t bitcoin-wallet-management .
       docker run -d -p 8080:8080 bitcoin-wallet-management
       ```

### Step 4: Verification
1. **Verify Application Startup**:
   - Check application logs for successful startup messages.
   - Ensure no errors related to database connectivity or Bitcoin Core RPC.

2. **Test API Endpoints**:
   - Use tools like Postman to test API endpoints for wallet creation, fund reception, and transaction sending.
   - Example endpoint test:
     ```
     POST /api/wallets/create
     ```

3. **Monitor System Performance**:
   - Use monitoring tools like Prometheus and Grafana to track system metrics and ensure optimal performance.

### Step 5: Maintenance and Monitoring
- Regularly update system packages and dependencies.
- Monitor Bitcoin network status and ensure node synchronization.
- Implement logging and alerting for critical application events.

## Conclusion
Following this guide will ensure a successful deployment of the Bitcoin Transaction and Wallet Management application in a production environment. Adhering to security best practices and regular maintenance will help maintain the integrity and performance of the system.
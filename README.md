# Prerequisites
Before you begin, ensure you have the following installed on your machine:

- Docker
- Git

# Running the Application with Docker

You can run the application easily inside a Docker container by following these steps:

## Step 1: Clone the Repository
Clone the repository to your local machine using git clone:

    git clone https://github.com/your-username/receipt-processor.git
    cd receipt-processor

## Step 2: Build the Docker Image
Once you have cloned the repository, build the Docker image using the following command:

    docker build -t receipt-processor .

The build may take a few minutes depending on your network speed and system configuration.

## Step 3: Run the Docker Container
Once the image is built, you can run the application using the following command:

    docker run -p 8080:8080 receipt-processor

This command will Start a Docker container with your application and expose port 8080 on your local machine, mapping it to port 8080 in the Docker container.

## Step 4: Access the Application
Once the container is up and running, you can access the application by opening your browser and navigating to:

    http://localhost:8080



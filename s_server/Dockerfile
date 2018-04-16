# Usee an official Python runtime as a parent image
FROM debian:stretch-slim

# Set the working directory to /app
WORKDIR /app

# Copy the current directory contents into the container at /app
ADD . /app

# Install any needed packages specified in requirements.txt
RUN apt-get update
RUN apt-get install -y openssl

# Make port 80 available to the world outside this container
EXPOSE 443

# Define environment variable
# ENV NAME World

# Run app.py when the container launches
CMD openssl s_server -status -www -accept 443 -key ssl/private.key -cert ssl/cert.pem -CAfile ssl/cas.pem


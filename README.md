# Customer Management - Create, Read, Update, Delete, findById and findAll 
### By David King


## Quick Start

```bash
# Checkout from git
# Setup Gradle Wrapper 
# Build
# Run 
# View 

# Commands

git clone https://github.com/enterprisesaas/customer-management
cd customer-management
gradle wrapper
./gradle clean build
./gradle bootRun

# Create Customer, HTTP Post
# http://localhost:8080/customers

# JSON
{
    "uuid": "21b1c993-e48f-4962-92b4-78a65535ad36",
    "firstName": "David",
    "lastName": "King",
    "email": "davidking@code.net"
}

# Find All, HTTP GET
# http://localhost:8080/customers

# Update lastname, HTTP PUT
# http://localhost:8080/customers/{customerId}/lastname

# Update firstname, HTTP PUT
# http://localhost:8080/customers/{customerId}/firstname

# Update email, HTTP PUT
# http://localhost:8080/customers/{customerId}/email

# Delete Customer, HTTP PUT
# http://localhost:8080/customers/{customerId}
```


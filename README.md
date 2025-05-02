MoA Farm Registry System
This project is a full-stack application designed to manage farm registry and related services for the Ministry of Agriculture (MoA). It consists of multiple components: a backend (moabackend), a frontend (moa-frontend), a farmland registry service, and integrations with MyGovService Portal (built on Joget) and Keycloak for authentication.
Project Components
•	MyGovService Portal (Joget): A workflow and service portal integrated with Keycloak for authentication.
•	MoA Frontend: A web-based user interface for interacting with the farm registry system.
•	MoA Backend: A Java-based backend (Spring Boot) handling core business logic and API services.
•	Farmland Registry: A Node.js-based service for managing farmland data.
•	Keycloak: An identity and access management solution for securing the system.
Prerequisites
Before running the project, ensure you have the following installed:
•	Java 17+ (for MoA Backend)
•	Node.js 18+ (for Farmland Registry and MoA Frontend)
•	Maven (for building MoA Backend)
•	Docker (optional, for Keycloak or Joget if containerized)
•	Git (to clone this repository)
Setup Instructions
1. Clone the Repository
git clone https://github.com/mainswitch7/moa-farm-registry.git
cd moa-farm-registry
2. Project Structure
moa-farm-registry/
├── moabackend/             # MoA Backend (Spring Boot)
├── moa-frontend/           # MoA Frontend (Static web app)
├── farmland-registry/      # Farmland Registry (Node.js)
├── joget/                  # MyGovService Portal (Joget configuration, if included)
└── README.md

3. Configuration and Start Order
Follow this order to ensure dependencies are available:
a. Keycloak (Port 8500)
•	Run Keycloak: 
o	If using a local instance:
docker run -p 8500:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:latest start-dev
•	
Access: http://localhost:8500
Config: Create a realm (e.g., moa-realm), client (e.g., moa-client), and users.
b. MoA Backend (Port 8030)
•	Navigate to Backend Directory: 
cd moabackend
•	Build the Project:
mvn clean install
•	Access: http://localhost:8030
•	Config: Update application.properties with Keycloak and Farmland Registry URLs.
•	Note: Ensure Keycloak is running, as the backend may rely on it for authentication.
c. Farmland Registry (Port 8040)
•	Navigate to Farmland Registry Directory:
cd farmland-registry
Install Dependencies:
npm install
Run the Server
node server.js
Access: http://localhost:8040

d. MyGovService Portal - Joget (Port 8080)
•	Start Joget Server: 
o	Assuming Joget is pre-installed or bundled
cd joget
./start.sh  # Or follow Joget-specific instructions

•	
o	Access: http://localhost:8080
o	Ensure integration with Keycloak (port 8500) is configured in Joget settings.
e. MoA Frontend (Port 5000)
•	Navigate to Frontend Directory:
cd moa-frontend
Install Serve (if not installed globally):
npm install -g serve
Run the Frontend:
npx serve -s -l 5000

•	Access: http://localhost:5000
•	Open index.html in your browser if needed.
Ports Overview
Component	Port	Command
Keycloak	8500	docker run ...
MoA Backend	8030	java -jar moabackend-0.0.1-SNAPSHOT.jar
Farmland Registry	8040	node server.js
MyGovService (Joget)	8080	./start.sh (or Joget-specific)
MoA Frontend	5000	npx serve -s -l 5000
Testing the System
1.	Verify Keycloak: Log into http://localhost:8080 via Keycloak SSO.
2.	Submit Application: 
•	Go to http://localhost:5000, fill farmer/household/farmland data, and submit.
•	Data is sent as JSON to http://localhost:8030/services/farmland/applications.
3.	Review and Approve: 
•	In MoA Backend (e.g., a custom UI or API call), review and approve the application.
•	Approved data is sent to http://localhost:8040/createEntries
4.	Explore Joget: Navigate to http://localhost:8080 and test workflows.
5.	Use MoA Frontend: Open http://localhost:5000 and interact with the UI (e.g., register a farm).
GovStack Compliance
•	Registration BB API: Implemented in MoA Backend (/services/{serviceId}/applications).
•	Digital Registry BB API: Implemented in Farmland Registry (createEntries).

Troubleshooting
•	MoA Backend Fails to Start: 
o	Ensure target/moabackend-0.0.1-SNAPSHOT.jar exists (run mvn clean install).
o	Check for port conflicts on 8030.
•	File Locking Issues: 
o	Delete the target folder manually if Maven clean fails:
rmdir /s /q moabackend/target



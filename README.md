# Smart Clinic

A full-stack clinic operations dashboard for appointment booking, daily schedules, doctor availability, and patient queues. Built as a portfolio project with a React + TypeScript frontend and a Spring Boot REST API backed by PostgreSQL.

## Features

- Clinic dashboard with today's schedule, queue, doctor roster, and status overview
- Appointment booking with request validation and conflict protection for a doctor's date/time slot
- Appointment status workflow: booked, checked in, completed, or cancelled
- REST API with centralized validation and error responses
- PostgreSQL persistence with Docker Compose setup
- GitHub Actions workflow for backend and frontend builds

## Run with Docker

```bash
docker compose up --build
```

Open the dashboard at [http://localhost:5173](http://localhost:5173). The API is at [http://localhost:8080/api](http://localhost:8080/api). The first run seeds four sample doctors.

## Run locally

1. Start PostgreSQL with the credentials in `backend/src/main/resources/application.properties`, or run only the `db` service with `docker compose up db`.
2. Start the API: `cd backend && mvn spring-boot:run`.
3. Start the UI: `cd frontend && npm install && npm run dev`.

## API overview

| Method | Endpoint | Purpose |
| --- | --- | --- |
| GET | `/api/dashboard` | Today's clinic summary |
| GET | `/api/doctors` | List doctors |
| GET | `/api/appointments?date=YYYY-MM-DD` | List appointments by date |
| GET | `/api/appointments/queue?date=YYYY-MM-DD&doctorId=1` | View the queue |
| POST | `/api/appointments` | Book an appointment |
| PATCH | `/api/appointments/{id}/status` | Update appointment status |
| GET | `/api/patients/{email}/appointments` | Patient appointment history |

## Important scope note

This version is a local portfolio demo. Authentication and role enforcement are not implemented, so endpoints are open. Do not use real patient information. Add authentication, access controls, database migrations, and production secrets before any real deployment.

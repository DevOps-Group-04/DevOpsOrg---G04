# Edinburgh Napier University - DevOps - Group 04

MASTER BUILD ![Build](https://img.shields.io/github/actions/workflow/status/DevOps-Group-04/DevOpsOrg---G04/main.yml?branch=master&style=flat-square)

DEVELOP STATUS ![Build](https://img.shields.io/github/actions/workflow/status/DevOps-Group-04/DevOpsOrg---G04/main.yml?branch=develop&style=flat-square)

LICENCE [![LICENSE](https://img.shields.io/github/license/DevOps-Group-04/DevOpsOrg---G04.svg?style=flat-square)](LICENSE)

RELEASE [![Releases](https://img.shields.io/github/release/DevOps-Group-04/DevOpsOrg---G04/all.svg?style=flat-square)](https://github.com/DevOps-Group-04/DevOpsOrg---G04/releases)

# WHO World Population Query System

A Java-based application for querying and reporting on countries, cities, languages, and populations using MySQL’s classic world dataset. Built by DevOps Group 04 with CI/CD, containerisation, and automated testing.

---

## Overview

- Query by country, region, or city
- Generate country, city, capital city, language, and population reports
- Output results suitable for WHO data-driven analysis

---

##  Tech Stack

- Runtime: Java 17
- Build: Maven
- Database: MySQL 8
- CI/CD: GitHub Actions
- Containers: Docker, Docker Compose
---

## Quick Start

### 1) Clone
```
git clone https://github.com/DevOps-Group-04/DevOpsOrg---G04.git
```
```
cd DevOpsOrg---G04
```
### 2. Set Up the Database

Ensure MySQL 8+ is installed, then import the database:
```
mysql -u root -p < world.sql
```

This will create and populate the world database with all required data.

### 3. Configure the Application

Update the database credentials in the config file:
```
DB_HOST=localhost
DB_NAME=world
DB_USER=root
DB_PASS=your_password
```

### 4. Run the Application
```
mvn compile exec:java
```

---

### Example Queries

Here are some useful SQL queries that the system may run:


- Get top 10 most populated countries

```
SELECT Name, Population FROM Country ORDER BY Population DESC LIMIT 10;
```
- List cities in a given country

```
SELECT Name, District, Population FROM City WHERE CountryCode = 'GBR';
```
- Find all countries where English is an official language

```
SELECT CountryCode, Language FROM CountryLanguage WHERE Language = 'English' AND IsOfficial = 'T';
```
---

## Project Team

### DevOps Group 04

A collaborative software engineering team focused on DevOps best practices and database-driven application design.

| Name      | Role               |
|-----------|--------------------|
| Pablo Y   | Developer / Tester |
| Matthew W | Developer / Tester |
| Bryce P   | Developer / Tester |
| Daniel W  | Developer / Tester |
| Munaib LB | Developer / Tester |

---

## License
This project is released under the Apache 2.0 License.

You are free to use, modify, and distribute it under the same terms.

---

## Acknowledgments
MySQL World Sample Database

World Health Organisation (WHO)

All team members of DevOps Group 04 for their contributions.

# ENU - DevOps - Group 04

MASTER BUILD ![Build](https://img.shields.io/github/actions/workflow/status/DevOps-Group-04/DevOpsOrg---G04/main.yml?master?style=flat-square)

DEVELOP STATUS ![Build](https://img.shields.io/github/actions/workflow/status/DevOps-Group-04/DevOpsOrg---G04/main.yml?develop?style=flat-square)

LICENCE [![LICENSE](https://img.shields.io/github/license/DevOps-Group-04/DevOpsOrg---G04.svg?style=flat-square)](https://github.com/DevOps-Group-04/DevOpsOrg---G04/blob/master/LICENSE)

RELEASE [![Releases](https://img.shields.io/github/release/DevOps-Group-04/DevOpsOrg---G04/all.svg?style=flat-square)](https://github.com/DevOps-Group-04/DevOpsOrg---G04/releases)

# 🌍 WHO World Population Query System

This project is a database-driven application designed for **World Health Organisation (WHO)** staff to efficiently **query and analyse global demographic and geographical data**.  
It provides information on **countries, cities, populations, and related attributes** using structured queries on the classic MySQL **`world`** dataset.

---

## 🧭 Overview

The **WHO World Population Query System** enables authorised staff to:
- Retrieve country and city data from the global dataset.
- View demographic and population statistics.
- Search by country, region, language, or population range.
- Support WHO data-driven initiatives and reports.

This application is built as part of **DevOps Group 04’s software engineering project**, emphasising teamwork, database integration, and DevOps practices.

---

## 🗂️ Database Structure

The database used is based on MySQL’s classic `world` dataset, which includes three main tables:

| Table               | Description                                                                                                   |
|---------------------|---------------------------------------------------------------------------------------------------------------|
| **Country**         | Contains information about countries including population, life expectancy, GNP, region, and government form. |
| **City**            | Stores details about major cities, linked to their country via a foreign key (`CountryCode`).                 |
| **CountryLanguage** | Contains languages spoken in each country and the percentage of speakers.                                     |

### Example Entity Relationships

- One **Country** has many **Cities**.
- One **Country** has many **Languages** (via `CountryLanguage`).
- Each **City** belongs to one **Country**.

---

## 🧩 Features

- 🔍 **Query by Country or City** — find population and regional information quickly.
- 📊 **Population Reports** — generate insights based on population or region.
- 🌐 **Language Data** — view official and spoken languages by country.
- 🧠 **User-friendly Interface** (CLI or GUI, depending on team implementation).
- ⚙️ **Built with DevOps Practices** — uses CI/CD pipelines and version control on GitHub.

---

## 🛠️ Technologies Used

| Component                  | Technology                    |
|----------------------------|-------------------------------|
| **Backend / Query Engine** | Java                          |
| **Database**               | MySQL 8.0                     |
| **DevOps Tools**           | GitHub Actions, Docker, Maven |
| **Version Control**        | Git / GitHub                  |
| **Documentation**          | Markdown, SQL comments        |

---

## 💾 Setup Instructions

### 1. Clone the Repository

git clone https://github.com/DevOps-Group-04/DevOpsOrg---G04.git

cd DevOpsOrg---G04

### 2. Set Up the Database

Ensure MySQL 8+ is installed, then import the database:

mysql -u root -p < world.sql

This will create and populate the world database with all required data.

### 3. Configure the Application

Update the database credentials in the config file:

DB_HOST=localhost
DB_NAME=world
DB_USER=root
DB_PASS=your_password

### 4. Run the Application

mvn compile exec:java

---

### 🧪 Example Queries

Here are some useful SQL queries that the system may run:


- Get top 10 most populated countries

SELECT Name, Population FROM Country ORDER BY Population DESC LIMIT 10;

- List cities in a given country

SELECT Name, District, Population FROM City WHERE CountryCode = 'GBR';

- Find all countries where English is an official language

SELECT CountryCode, Language FROM CountryLanguage WHERE Language = 'English' AND IsOfficial = 'T';

---

## 👥 Project Team

DevOps Group 04

A collaborative software engineering team focused on DevOps best practices and database-driven application design.

| Name      | Role               |
|-----------|--------------------|
| Pablo Y   | Developer / Tester |
| Matthew W | Developer / Tester |
| Bryce P   | Developer / Tester |
| Daniel W  | Developer / Tester |
| Munaib LB | Developer / Tester |

---

## 🧾 License
This project is released under the Apache 2.0 License.

You are free to use, modify, and distribute it under the same terms.

---

## 📚 Acknowledgments
MySQL World Sample Database

World Health Organisation (WHO)

All team members of DevOps Group 04 for their contributions.

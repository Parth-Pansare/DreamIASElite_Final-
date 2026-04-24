# Dream IAS Elite - Backend & Content Strategy Session

## Date: Friday, April 24, 2026

### 1. The "Tank" Technology Stack
- **Frontend (Mobile):** Android (Jetpack Compose + Kotlin)
- **Backend:** **Spring Boot (Java)**
    - Chosen for maximum scalability, enterprise-grade security (Spring Security + JWT), and long-term stability.
    - Architecture: RESTful API serving both the Mobile App and the Admin Panel.
- **Database:** **PostgreSQL**
    - Managed via **Spring Data JPA (Hibernate)** for robust object-relational mapping.
- **Web (Admin Panel):** **Next.js**
    - The existing `webnext js` project will be repurposed into an internal tool for administrators to manage content, users, and analytics.

### 2. The Content Engine Strategy (Automated Ingestion)
To solve the problem of manually entering thousands of UPSC PYQs and Current Affairs, we will implement the following "Content Pipeline":

#### **A. The Fetcher (Web Scraper)**
- **Technology:** Python (BeautifulSoup/Selenium) or Java (Jsoup).
- **Function:** Automatically crawls official UPSC archives and educational portals to download papers and articles.

#### **B. The Brain (AI-Powered Parser)**
- **Technology:** Python (PyMuPDF/OCR) + LLM API (Gemini/GPT).
- **Process:** 
    1. Extract raw text from complex UPSC PDFs.
    2. Use AI to structure that text into standard JSON (identifying Question, Options, Correct Answer, and Explanation).
    3. **Auto-Tagging:** The AI will automatically assign each question to a specific **Unit** and **Topic** based on the UPSC syllabus.

#### **C. The Ingestor (API Gateway)**
- **Process:** The structured JSON is sent to a private Spring Boot endpoint.
- **Logic:** Spring Boot validates the data, checks for duplicates, and saves it into the PostgreSQL database.

#### **D. The Distribution**
- The Android app fetches this structured, tagged data from the Spring Boot API, allowing students to filter questions by Year, Subject, or Topic.

### 3. Key Decisions & Rationales
- **Java vs Kotlin for Backend:** Chosen **Java** to ensure a traditional, high-performance "Tank" architecture.
- **Next.js Role:** Shifted from "Backend" to "Admin Tooling" to leverage the existing UI work while keeping the core API in Spring Boot.
- **Automation First:** Prioritizing the build-out of the Scraper/Parser to ensure the app is content-rich from Day 1 without manual data entry.

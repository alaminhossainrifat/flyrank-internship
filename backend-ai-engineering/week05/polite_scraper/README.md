# 📚 Polite Scraper — Week 5

A Python web scraping project that follows ethical scraping practices while collecting book data from **Books to Scrape**.

> **Target Website:** https://books.toscrape.com/

---

## 📖 Project Overview

This project scraped **5 pages (100 books)** from the website and extracted the following information for each book:

- 📌 Title
- 💷 Price
- ✅ Availability

The collected data is cleaned, structured, and exported to a JSON file for future use as a **RAG (Retrieval-Augmented Generation) corpus**.

---

## 🤝 Ethical Scraping Practices

This scraper was designed to be respectful of the target website.

### ✔ Robots.txt Verification
The website's `robots.txt` file was checked before scraping to confirm that the targeted pages are allowed.

Verification file:
- `robots_check.txt`

---

### ✔ Rate Limiting

To avoid overwhelming the server, the scraper waits **1 second** between every HTTP request.

```python
time.sleep(1)
```

---

### ✔ Custom User-Agent

The scraper identifies itself with a custom User-Agent instead of pretending to be a browser.

```text
RifatScraperBot/1.0 (contact: alaminhossainrif@gmail.com)
```

---

## ⚙️ Scraping Pipeline

The project follows a simple and clean data extraction pipeline:

```text
Fetch
   ↓
Parse
   ↓
Extract
   ↓
Clean
   ↓
Structure
```

### Fetch
- Retrieves HTML pages using the `requests` library.

### Parse
- Parses HTML content using **BeautifulSoup**.

### Extract
Extracts:

- Book title
- Price
- Availability status

### Clean
- Removes unwanted currency symbols
- Fixes encoding issues
- Cleans text using regular expressions

### Structure
Stores the cleaned data as structured JSON.

---

## 📂 Project Structure

```text
.
├── scraper.py            # Main scraping script
├── check_robots.py       # Robots.txt verification
├── robots_check.txt      # Robots.txt verification output
├── books.json            # Final scraped dataset (100 records)
└── README.md
```

---

## 📦 Output

The scraper generates:

**`books.json`**

Dataset contains:

- 100 structured book records
- Clean and normalized data
- Ready for downstream processing
- Intended as the foundation for next week's **RAG corpus**

---

## 🛠 Technologies Used

- Python 3
- Requests
- BeautifulSoup4
- Regular Expressions (re)
- JSON

---

## 🚀 Learning Outcomes

This project demonstrates:

- Ethical web scraping practices
- Robots.txt compliance
- Rate limiting
- HTML parsing with BeautifulSoup
- Data cleaning and normalization
- JSON data structuring
- Building reusable datasets for AI/RAG workflows

---

## 📄 License

This project is created for educational purposes as part of the **Week 5** assignment.
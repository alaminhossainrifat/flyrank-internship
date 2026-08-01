import requests, time, json, re
from bs4 import BeautifulSoup

HEADERS = {"User-Agent": "RifatScraperBot/1.0 (contact: your_email@example.com)"}
BASE = "https://books.toscrape.com/catalogue/page-{}.html"

all_data = []
for page in range(1, 6):
    url = BASE.format(page)
    res = requests.get(url, headers=HEADERS, timeout=10)
    res.encoding = "utf-8"
    soup = BeautifulSoup(res.text, "html.parser")
    books = soup.select("article.product_pod")
    for book in books:
        title = book.h3.a["title"]
        price_text = book.select_one(".price_color").text
        price = float(re.sub(r"[^\d.]", "", price_text))
        availability = book.select_one(".availability").text.strip()
        all_data.append({
            "title": title,
            "price": price,
            "availability": availability
        })
    time.sleep(1)

with open("books.json", "w", encoding="utf-8") as f:
    json.dump(all_data, f, ensure_ascii=False, indent=2)

print(f"Collected {len(all_data)} records")
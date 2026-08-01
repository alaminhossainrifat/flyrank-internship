import urllib.robotparser

rp = urllib.robotparser.RobotFileParser()
rp.set_url("https://books.toscrape.com/robots.txt")
rp.read()

user_agent = "RifatScraperBot"
test_urls = [
    "https://books.toscrape.com/catalogue/page-1.html",
    "https://books.toscrape.com/catalogue/page-2.html",
]

for url in test_urls:
    allowed = rp.can_fetch(user_agent, url)
    print(f"{url} -> Allowed: {allowed}")
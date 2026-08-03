(function() {
    // 1. Extracting widget ID from script tag
    const scriptTag = document.currentScript || document.querySelector('script[src*="widget.js"]');
    const widgetId = new URL(scriptTag.src).searchParams.get("id");
    const backendUrl = "http://localhost:8080";

    if (!widgetId) {
        console.error("FlyRank Widget: ID is missing in the script tag.");
        return;
    }

// 2. Fetch widget configuration (config) from backend
    fetch(`${backendUrl}/api/widgets/${widgetId}/config`)
        .then(res => res.json())
        .then(config => renderWidget(config))
        .catch(err => console.error("FlyRank Widget: Failed to load config", err));

    // 3. Render the form to the website
    function renderWidget(config) {
        const container = document.createElement('div');
        container.style.cssText = "border: 1px solid #ddd; padding: 20px; max-width: 300px; font-family: sans-serif; border-radius: 8px; background: #fff;";

        container.innerHTML = `
            <h3 style="margin-top: 0;">${config.name}</h3>
            <form id="flyrank-widget-form">
                <input type="text" name="name" placeholder="Name" required style="display:block; width:100%; box-sizing:border-box; margin-bottom:10px; padding:8px;" />
                <input type="email" name="email" placeholder="Email" required style="display:block; width:100%; box-sizing:border-box; margin-bottom:10px; padding:8px;" />
                
                <!-- Honeypot Field (Hidden) -->
                <input type="text" name="_bot_check" style="display:none;" />
                
                <button type="submit" style="width:100%; padding:10px; background-color:#007bff; color:#fff; border:none; border-radius:4px; cursor:pointer;">Submit</button>
            </form>
            <div id="flyrank-msg" style="margin-top:10px; color:green; display:none; text-align:center;">
                Thank you! Submission received.
            </div>
        `;

        // Show the form right after the script tag is placed
        scriptTag.parentNode.insertBefore(container, scriptTag.nextSibling);

        // 4. Handling form submission
        document.getElementById("flyrank-widget-form").addEventListener("submit", function(e) {
            e.preventDefault();

            const formData = new FormData(e.target);
            const payload = Object.fromEntries(formData.entries());

            fetch(`${backendUrl}/api/submissions?widgetId=${widgetId}`, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(payload)
            }).then(res => {
                if(res.ok) {
                    document.getElementById("flyrank-widget-form").style.display = "none";
                    document.getElementById("flyrank-msg").style.display = "block";
                } else {
                    alert("Something went wrong!");
                }
            });
        });
    }
})();
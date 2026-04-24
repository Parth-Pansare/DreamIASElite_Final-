import json
import requests
import ollama

# --- CONFIGURATION ---
BASE_URL = "http://localhost:8080/api/v1"
AUTH_URL = f"{BASE_URL}/auth/login"
INGEST_URL = f"{BASE_URL}/content/admin/batch-ingest"
OLLAMA_MODEL = "llama3.2"

# Admin Credentials (Matches the DataSeeder)
ADMIN_EMAIL = "admin@dreamias.com"
ADMIN_PASSWORD = "admin123"

SYSTEM_PROMPT = """
You are a UPSC Question Parser. Extract questions into a JSON array.
Format: [{"prompt": "...", "options": ["A. x", "B. y", ...], "correctIndex": 0, "explanation": "...", "subjectName": "...", "unitName": "...", "topicName": "..."}]
"""

def get_admin_token():
    print(f"--- Logging in as {ADMIN_EMAIL}... ---")
    payload = {"email": ADMIN_EMAIL, "password": ADMIN_PASSWORD}
    try:
        response = requests.post(AUTH_URL, json=payload)
        if response.status_code == 200:
            token = response.json().get("token")
            print("Login successful!")
            return token
        else:
            print(f"Login failed! Status: {response.status_code}")
            return None
    except Exception as e:
        print(f"Auth error: {e}")
        return None

def parse_with_ai(raw_text):
    print(f"--- Sending text to Ollama ({OLLAMA_MODEL})... ---")
    response = ollama.chat(model=OLLAMA_MODEL, messages=[
        {'role': 'system', 'content': SYSTEM_PROMPT},
        {'role': 'user', 'content': f"Extract questions: {raw_text}"},
    ])
    content = response['message']['content']
    try:
        start = content.find('[')
        end = content.rfind(']') + 1
        return json.loads(content[start:end])
    except Exception as e:
        print(f"AI Parse error: {e}")
        return None

def push_to_spring_boot(questions, token):
    if not questions or not token: return
    
    headers = {"Authorization": f"Bearer {token}"}
    print(f"--- Pushing {len(questions)} questions with JWT... ---")
    try:
        response = requests.post(INGEST_URL, json=questions, headers=headers)
        if response.status_code == 200:
            print("Ingestion Success!")
        else:
            print(f"Failed. Status: {response.status_code}, Msg: {response.text}")
    except Exception as e:
        print(f"Push error: {e}")

if __name__ == "__main__":
    text = "Q. Which article relates to Abolition of Untouchability? A. 14, B. 15, C. 16, D. 17. Ans: D. Art 17. Subject: Indian Polity, Unit: Constitutional Framework, Topic: Fundamental Rights."
    
    token = get_admin_token()
    if token:
        questions = parse_with_ai(text)
        if questions:
            push_to_spring_boot(questions, token)

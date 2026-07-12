from dotenv import load_dotenv
import os

load_dotenv()

class Settings:

    GROQ_API_KEY = os.getenv("GROQ_API_KEY")

    MODEL = os.getenv(
        "MODEL",
        "llama-3.3-70b-versatile"
    )

settings = Settings()

print("API KEY:", settings.GROQ_API_KEY)
print("MODEL:", settings.MODEL)
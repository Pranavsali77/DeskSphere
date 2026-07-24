import os
import joblib

# -----------------------------
# Load Trained Model
# -----------------------------

BASE_DIR = os.path.dirname(__file__)

MODEL_PATH = os.path.join(
    BASE_DIR,
    "ticket_model.pkl"
)

model = joblib.load(MODEL_PATH)


# -----------------------------
# Predict Ticket Category
# -----------------------------

def predict_category(title: str, description: str):

    text = f"{title} {description}"

    prediction = model.predict([text])[0]

    probabilities = model.predict_proba([text])[0]

    confidence = max(probabilities)

    return {
        "predictedCategory": prediction,
        "confidence": round(float(confidence), 4)
    }
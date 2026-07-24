import os
import joblib
import pandas as pd

from sklearn.pipeline import Pipeline
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.linear_model import LogisticRegression

# -----------------------------
# Load Dataset
# -----------------------------

BASE_DIR = os.path.dirname(os.path.dirname(__file__))

dataset_path = os.path.join(
    BASE_DIR,
    "datasets",
    "tickets_dataset.csv"
)

df = pd.read_csv(dataset_path)

# -----------------------------
# Prepare Training Data
# -----------------------------

X = df["title"] + " " + df["description"]
y = df["category"]

# -----------------------------
# Build ML Pipeline
# -----------------------------

model = Pipeline([
    ("tfidf", TfidfVectorizer()),
    ("classifier", LogisticRegression(max_iter=1000))
])

# -----------------------------
# Train Model
# -----------------------------

model.fit(X, y)

# -----------------------------
# Save Model
# -----------------------------

model_path = os.path.join(
    BASE_DIR,
    "nlu",
    "ticket_model.pkl"
)

joblib.dump(model, model_path)

print("===================================")
print("AI Ticket Classification Model Ready")
print("Saved to:")
print(model_path)
print("===================================")
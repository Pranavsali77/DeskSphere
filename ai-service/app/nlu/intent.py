import json

from app.nlu.classifier import classify_issue


def detect_intent(message: str):

    response = classify_issue(message)

    print("========== AI RESPONSE ==========")
    print(response)
    print("=================================")

    return json.loads(response)
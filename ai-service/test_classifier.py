from app.nlu.ticket_classifier import predict_category

result = predict_category(
    "Laptop cannot connect to WiFi",
    "Internet is not working in office"
)

print(result)
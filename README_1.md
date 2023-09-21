
# Nasa Day of the Image

The NASA Image of the Day App is a simple Android application that allows users to view NASA's "Image of the Day" along with its description. It fetches data from NASA's API and provides a user-friendly interface for exploring daily space images.




## API Reference

#### Get all items

```http
  GET /planetary/apod
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `api_key` | `string` | **Required**. Your API key
| `start_date` | `string` | Optional (YYYY-MM-DD)
| `end_date` | `string` | Optional (YYYY-MM-DD) 



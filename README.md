
# Nasa Day Image

The NASA Image of the Day App is a simple Android application that allows users to view NASA's "Image of the Day" along with its description. It fetches data from NASA's API and provides a user-friendly interface for exploring daily space images.




## API Reference

#### Get all items

```http
  GET /planetary/apod
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `api_key` | `string` | **Required** Your API key
| `start_date` | `string` | **Optional** (YYYY-MM-DD)
| `end_date` | `string` | **Optional** (YYYY-MM-DD) 



## Features

- The app fetches the daily image data from NASA's API.
- The app loads the daily image upon app launch and allows users to manually refresh it.
- It can handle video content by displaying a placeholder image and a play button for video content.
- Local caching is implemented to store fetched data for offline access.



## Setup and API Key Intructions

- To use this app and fetch NASA's "Image of the Day" data, you need to obtain an API key from NASA's Open API portal. Follow these steps to get your API key:
- 1. Visit NASA's Open API Portal:
   - Go to (https://api.nasa.gov/) in your web browser.
- It can handle video content by displaying a placeholder image and a play button for video content.
- Local caching is implemented to store fetched data for offline access.


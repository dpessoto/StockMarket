# StockMarket

This project uses the **brapi** API to retrieve information about stocks and market indices. To utilize the API, you need to obtain a personal API key.

## How to Get an API Key

1. Visit the API website: [https://brapi.dev/docs](https://brapi.dev/docs).

2. Create an account or log in if you already have one.

3. After logging in, navigate to the **Generate API Key** section.

4. Create a new key and copy the generated value.

## Setting Up the API Key in the Project

1. Open the project in your preferred code editor.

2. In the project directory, locate the `apikey.properties` file.

3. Open the `apikey.properties` file and add the following line, replacing `"YOUR_API_KEY"` with the key you copied:

   ```properties
   API_KEY="YOUR_API_KEY"

## Project Features

- Listing of stocks in the market.
- Detailed view of a selected stock, including a chart.

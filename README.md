# Modern Java Course, by [Alura](https://www.alura.com.br)

My code for the 5-day Java course of Alura using most modern Java resources (Java11 +)

About the course: https://www.alura.com.br/imersao-java _(in Portuguese)_

## Day 1: Consuming a IMDB API with Java

### Branch "first-class-consuming-imdb-api"

### Challenges

- [X] Consume the most popular movies' endpoint from IMDB API. Also look in the IMDB API documentation for
  the endpoint that returns the best series and the one that returns the most popular series.

- [X] Use your creativity to make the output cuter: use emojis with UTF-8 code, show the movie note as little stars,
  decorate the terminal with colors, bold and italics using ANSI codes, and more!
     - Without print because Powershell does not showing emojis and I'll go back to this later :-(

- [X] Place the IMDB API key somewhere outside the code like a configuration file (eg a .properties file)
  or an environment variable.

- [X] Change JsonParser to use a JSON parsing library like Jackson or GSON.

- [X] Supreme challenge: create some way for you to give a rating to the movie, pulling it from some configuration file
  or asking the user to type the rating in the terminal.

## Day 2: Generating stickers

### Branch "creating-stickers"

### Challenges

- [X] Read the documentation of `InputStream`: https://docs.oracle.com/javase/7/docs/api/java/io/InputStream.html.

- [ ] Center the text on the sticker.
- [ ] Make a WhatsApp and/or Telegram package with your own stickers!

- [X] Create output directory for images if it does not already exist.
    - Output directory included in the .gitignore to avoid image versions in GitHub that can create confusing git trees
- [ ] Use another font like Comic Sans or Impact, the font used in memes.
- [ ] Put a picture of yourself smiling, giving a thumbs up!
- [ ] Add an outline to the text of the image.
- [ ] Edit the image URL returned by the IMDB API to get a larger image instead of the thumbnails.
- [ ] Customize sticker text according to IMDB ratings.
- [ ] Ultimate challenge: use some image manipulation library like OpenCV to extract the main image and contour it.
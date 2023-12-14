# Discord Bot - Java Maven Project

This Discord bot, developed using Java and Maven, offers various commands, including fetching memes, jokes, and additional functionalities.

## Features

- **Meme Command**: Use `/meme` to retrieve programming-related memes.
- **Joke Command**: Employ `/joke` to fetch dad jokes.
- **Other Commands**: Includes features like todo lists, rule display, and more.

## Setup

1. **Clone the Repository:**

    ```bash
    git clone https://github.com/your-username/discord-bot.git
    cd discord-bot
    ```

2. **Environment Setup:**

    - Create a `.env` file in the root directory.
    - Define necessary environment variables in the `.env` file:
        ```plaintext
        DISCORD_BOT_TOKEN=YOUR_DISCORD_BOT_TOKEN_HERE
        RAPID_API_KEY=YOUR_RAPID_API_KEY_HERE
        ```

3. **Building and Running:**

    Use Maven to build the project and run the bot:

    ```bash
    mvn clean install
    java -jar target/discord-bot.jar
    ```

4. **Command Usage:**

- `/meme`: Fetches programming-related memes.
- `/joke`: Retrieves dad jokes.
- `/rules`: Displays server rules.
- `/hello`: Greets the user.
- `/todo`: Allows the user to create a to-do list.
- `/help`: Provides information and instructions about bot commands.


## Dependencies

- [JDA (Java Discord API)](https://github.com/DV8FromTheWorld/JDA)
- [OkHttp](https://square.github.io/okhttp/) for making HTTP requests.
- [dotenv-java](https://github.com/cdimascio/dotenv-java) for handling environment variables.
- Other dependencies mentioned in the `pom.xml` file.

## Screenshots

![image](https://github.com/deathmukh/discbot/assets/91791452/57cce116-430b-48ba-a2d0-f4d1734e24e7)
![image](https://github.com/deathmukh/discbot/assets/91791452/c50c1bc8-bcfd-4b41-b8e0-cf23ebc39a74)
![image](https://github.com/deathmukh/discbot/assets/91791452/ed5e3594-fe90-4f4b-aa68-7fc31a59561b)


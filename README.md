# EZPC

## Setup

- Fork and clone this repository to your machine
- Create a new PostgreSQL databases called `etl1db`
- Install Maven by following the [instructions](https://maven.apache.org/install.html) for your operating system
- Set up Auth0:
  - [Sign up to Auth0](https://auth0.com/signup?place=header&type=button&text=sign%20up)
  - Click on `Applications` in the side bar.
  - Click on `+ Create Application`
  - Enter any name for the application and select `Regular Web Applications`
  - In the application's `Settings` page, add `http://localhost:8080/login/oauth2/code/okta` to `Allowed Callback URLs` and `http://localhost:8080` to `Allowed Logout URLs`, then click `Save`
  - Keep the `Settings` page open, then edit the file `application.properties` (found in `src/main/resources`) and replace `${OAUTH_ISSUER_URI}` with `https://` followed by your `Domain`, `${OAUTH_CLIENT_ID}` with your `Client ID`, and `${OAUTH_CLIENT_SECRET}` with your `Client Secret`
- Open the codebase in an IDE and install the Lombok plugin:
  - If using IntelliJ, for example, accept the prompt to install the Lombok plugin, or if you don't get prompted, open the settings, go to `Plugins` and search for "Lombok", made by Jetbrains)
- Either run the command `mvn spring-boot:run` in a console application, or run `Etl1Application.main()` from your IDE
- Navigate to `http://localhost:8080/` in your web browser
>If you intend to push any changes to the project to your forked repository, you should make sure you don't push your Auth0 details. You can do this either by ensuring you exclude application.properties from any commits, or you can run this command from your project directory to always exclude it automatically:
> 
>`git update-index --skip-worktree src/main/resources/application.properties`
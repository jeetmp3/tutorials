## SpringSecurity using OAuth2 with AzureAD

Application demonstrates spring security using oauth2 and AzureAD as authorization server.

### How to run
Configure clientId & secrets

```bash
export CLIENT_ID=<client-id>
export CLIENT_SECRET=<client-secret>
```

Now run the application using

```bash
./gradlew :azure-oauth2-demo:bootRun
```

Open `https://<host-name>/home`
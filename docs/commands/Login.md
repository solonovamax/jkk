It allows to login in the current Jenkins server. If the login is successfull, a
new file will appera in the current directory where `jkk` has been executed, a
`.jkkcred` file. This TOML file includes your login information, such as
username, password and server URL. *It is very important* to note that your
credentials are saved as **plain** text, and no encryption is applied whatsoever,
so please do not commit this file in your VCS system (if you don't know why,
[this is an excellent article explaining why this is a bad
idea](https://dylankatz.com/Why-We-Fail-At-Keeping-Git-Secrets/)).

## Usage
Example:
```
jkk login -u <username> -p <password> https://jenkinsurl.example.com
```

Flags explanation:
* `--username -u` is the username `jkk` will use to login in the server
* `--passowrd -p` the password or token used by `jkk` to complete the authentication

Note that if not provided, the two flags will be asket interactively. Finally,
if no Jenkins server URL is specified, the login will be perform against
`http://localhost`.
